package ru.skillbox.notificationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.skillbox.commondto.account.AccountDto;
import ru.skillbox.notificationservice.dto.NotificationCreateDto;
import ru.skillbox.notificationservice.dto.NotificationSettingDto;
import ru.skillbox.notificationservice.entity.Notification;
import ru.skillbox.notificationservice.entity.NotificationCreate;
import ru.skillbox.notificationservice.entity.NotificationResponse;
import ru.skillbox.notificationservice.repository.NotificationCreateRepository;
import ru.skillbox.notificationservice.settings.NotificationSetting;
import ru.skillbox.notificationservice.settings.NotificationSettingResponse;
import ru.skillbox.notificationservice.entity.NotificationType;
import ru.skillbox.notificationservice.repository.NotificationRepository;
import ru.skillbox.notificationservice.settings.NotificationSettingData;

import java.time.LocalDateTime;
import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private NotificationRepository notificationRepository;
    private NotificationCreateRepository notificationCreateRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, NotificationCreateRepository notificationCreateRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationCreateRepository = notificationCreateRepository;
    }

    public NotificationSettingResponse getSettings(Long id) {

        NotificationSetting notificationSetting = notificationRepository.findByUserId(id).get();

        NotificationSettingResponse notificationSettingResponse = new NotificationSettingResponse();
        notificationSettingResponse.setTime(LocalDateTime.now());
        notificationSettingResponse.setUserId(notificationSetting.getUserId());

        ArrayList<NotificationSettingData> notificationSettingData = new ArrayList<>();

        if (notificationSetting.isPost()){
            notificationSettingData.add(new NotificationSettingData(true, NotificationType.POST ));
        }
        if (notificationSetting.isMessage()){
            notificationSettingData.add(new NotificationSettingData(true, NotificationType.MESSAGE ));
        }
        if (notificationSetting.isPostComment()){
            notificationSettingData.add(new NotificationSettingData(true, NotificationType.POST_COMMENT ));
        }
        if (notificationSetting.isFriendRequest()){
            notificationSettingData.add(new NotificationSettingData(true, NotificationType.FRIEND_REQUEST ));
        }
        if (notificationSetting.isFriendBirthday()){
            notificationSettingData.add(new NotificationSettingData(true, NotificationType.FRIEND_BIRTHDAY ));
        }
        if (notificationSetting.isSendEmailMessage()){
            notificationSettingData.add(new NotificationSettingData(true, NotificationType.SEND_EMAIL_MESSAGE ));
        }
        if (notificationSetting.isSendPhoneMessage()){
            notificationSettingData.add(new NotificationSettingData(true, NotificationType.SEND_PHONE_MESSAGE ));
        }
        if (notificationSetting.isCommentComment()){
            notificationSettingData.add(new NotificationSettingData(true, NotificationType.COMMENT_COMMENT ));
        }

        notificationSettingResponse.setData(notificationSettingData);
        return notificationSettingResponse;
    }

    public void createSettings(NotificationSettingDto dto) {
        NotificationSetting notificationSetting = new NotificationSetting();
        notificationSetting.setId(dto.getId());
        notificationSetting.setUserId(dto.getUserId());
        notificationSetting.setFriendRequest(dto.isFriendRequest());
        notificationSetting.setFriendBirthday(dto.isFriendBirthday());
        notificationSetting.setPostComment(dto.isPostComment());
        notificationSetting.setCommentComment(dto.isCommentComment());
        notificationSetting.setPost(dto.isPost());
        notificationSetting.setMessage(dto.isMessage());
        notificationSetting.setSendPhoneMessage(dto.isSendPhoneMessage());
        notificationSetting.setSendEmailMessage(dto.isSendEmailMessage());
        notificationRepository.save(notificationSetting);
    }

    public void updateSettings(NotificationSettingData notificationSettingData , Long id){

        NotificationSetting notificationSetting = notificationRepository.findByUserId(id).get();

        if (notificationSettingData.getNotificationType().equals(NotificationType.POST)){
            notificationSetting.setPost(notificationSettingData.isEnable());
        }
        if (notificationSettingData.getNotificationType().equals(NotificationType.MESSAGE)){
            notificationSetting.setMessage(notificationSettingData.isEnable());
        }
        if (notificationSettingData.getNotificationType().equals(NotificationType.FRIEND_BIRTHDAY)){
            notificationSetting.setFriendBirthday(notificationSettingData.isEnable());
        }
        if (notificationSettingData.getNotificationType().equals(NotificationType.FRIEND_REQUEST)){
            notificationSetting.setFriendRequest(notificationSettingData.isEnable());
        }
        if (notificationSettingData.getNotificationType().equals(NotificationType.POST_COMMENT)){
            notificationSetting.setPostComment(notificationSettingData.isEnable());
        }
        if (notificationSettingData.getNotificationType().equals(NotificationType.COMMENT_COMMENT)){
            notificationSetting.setCommentComment(notificationSettingData.isEnable());
        }
        if (notificationSettingData.getNotificationType().equals(NotificationType.SEND_EMAIL_MESSAGE)){
            notificationSetting.setSendEmailMessage(notificationSettingData.isEnable());
        }
        if (notificationSettingData.getNotificationType().equals(NotificationType.SEND_PHONE_MESSAGE)){
            notificationSetting.setSendPhoneMessage(notificationSettingData.isEnable());
        }

        notificationRepository.save(notificationSetting);
    }


    public void createNotification(NotificationCreateDto dto) {
        NotificationCreate notificationCreate = new NotificationCreate();
        notificationCreate.setId(dto.getId());
        notificationCreate.setAuthorId(dto.getAuthorId());
        notificationCreate.setUserId(dto.getUserId());
        notificationCreate.setContent(dto.getContent());
        notificationCreate.setNotificationType(dto.getNotificationType());
        notificationCreate.setStatusSent(dto.isStatusSent());

        notificationCreateRepository.save(notificationCreate);
    }

    public NotificationResponse getNotification(HttpServletRequest request) throws Exception {
        Long currentAuthUserId = Long.parseLong(request.getHeader("id"));
        AccountDto userDto = getAccountDtoByIdFromUserService(currentAuthUserId,request);

        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setTimeStamp(LocalDateTime.now());

        ArrayList<Notification> data = new ArrayList<>();
        data.add(new Notification(0L,userDto, "content" , NotificationType.FRIEND_REQUEST, LocalDateTime.now()));

        notificationResponse.setData(data);
        return notificationResponse;

    }
    private AccountDto getAccountDtoByIdFromUserService(Long accountId, HttpServletRequest request) throws Exception {
        String url = "http://localhost:9090/api/v1/account/{accountId}";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",request.getHeader("Authorization"));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class,
                accountId
        );
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.getBody(), AccountDto.class);
    }




}

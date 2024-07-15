package ru.skillbox.notificationservice.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.notificationservice.dto.NotificationCreateDto;
import ru.skillbox.notificationservice.dto.NotificationSettingDto;
import ru.skillbox.notificationservice.entity.NotificationResponse;
import ru.skillbox.notificationservice.settings.NotificationSettingResponse;
import ru.skillbox.notificationservice.service.NotificationService;
import ru.skillbox.notificationservice.settings.NotificationSettingData;
import ru.skillbox.notificationservice.status.NotificationStatus;

import java.time.LocalDateTime;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notifications")
public class NotificationController {

    private NotificationService notificationService;

    @GetMapping("/settings")
    public NotificationSettingResponse getNotificationSetting(HttpServletRequest httpServletRequest){

        return notificationService.getSettings(Long.parseLong(httpServletRequest.getHeader("id")));

    }

    @PutMapping("/settings")
    public ResponseEntity<?> updateNotificationSettings(@RequestBody NotificationSettingData notificationSettingData, HttpServletRequest httpServletRequest){
        notificationService.updateSettings(notificationSettingData , Long.parseLong(httpServletRequest.getHeader("id")));

        LocalDateTime dateTime = LocalDateTime.now();
        NotificationStatus notificationStatus = new NotificationStatus(true);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("time" , dateTime);
        hashMap.put("status" , notificationStatus);

        return ResponseEntity.ok(hashMap);
    }

    @PostMapping("/settings")
    public ResponseEntity<?> createNotificationSetting(@RequestBody NotificationSettingDto notificationSettingDto){
        notificationService.createSettings(notificationSettingDto);
        return ResponseEntity.ok(notificationSettingDto);
    }


    @GetMapping
    public NotificationResponse getNotification(HttpServletRequest httpServletRequest) throws Exception {

        return notificationService.getNotification(httpServletRequest);
    }


    @PostMapping
    public ResponseEntity<?> createNotification(@RequestBody NotificationCreateDto notificationCreateDto){
        notificationService.createNotification(notificationCreateDto);
        return ResponseEntity.ok(notificationCreateDto);
    }
}

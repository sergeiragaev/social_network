package ru.skillbox.dialogservice.controller;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skillbox.dialogservice.TestDependenciesContainer;
import ru.skillbox.dialogservice.model.dto.DialogDto;
import ru.skillbox.dialogservice.model.dto.JwtRequest;
import ru.skillbox.dialogservice.model.dto.MessageDto;
import ru.skillbox.dialogservice.model.entity.Dialog;
import ru.skillbox.dialogservice.service.feign.DialogFeignClient;

import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@WithUserDetails(value = "admin@socialnetwork.com")
class DialogControllerIT extends TestDependenciesContainer {

    @MockBean
    private DialogFeignClient feignClient;

    @Value("${app.apiPrefix}")
    private String apiPrefix;

    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    public void setUp() {

        HashMap<String, String> tokenPayload = new HashMap<>();
        tokenPayload.put("id", "1");
        tokenPayload.put("authorities", "ADMIN");
        Mockito.when(feignClient.validateToken(new JwtRequest("token")))
                .thenReturn(tokenPayload);

    }

    @Test
    @DisplayName("test update dialog by id, return dialog, ok")
    void testPutDialogById_returnDialog() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put(apiPrefix + "/dialogs/{id}", 1L)
                        .cookie(new Cookie("jwt", "token")))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("test update dialog by id, dialog not exists, bad request")
    void testPutDialogById_dialogNotExists_badRequest() throws Exception {

        DialogDto dialogDto = generateTestDialogDto();
        saveDialogInDbAndGet(dialogDto);

        mockMvc.perform(MockMvcRequestBuilders.put(apiPrefix + "/dialogs/{id}", 145145145L)
                        .cookie(new Cookie("jwt", "token")))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("test update dialog by id, dialog is updated")
    void testUpdateDialog() throws Exception {
        DialogDto dialogDto = generateTestDialogDto();
        saveDialogInDbAndGet(dialogDto);

        mockMvc.perform(
                        put("/api/v1/dialogs/1")
                                .accept(MediaType.APPLICATION_JSON)
                                .cookie(new Cookie("jwt", "token")))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(dialogDto.getUnreadCount()))));
    }

    @Test
    @DisplayName("test get dialogs, return dialogs")
    void testGetDialogs() throws Exception {
        DialogDto dialogDto = generateTestDialogDto();
        saveDialogInDbAndGet(dialogDto);

        mockMvc.perform(
                        get("/api/v1/dialogs").header("id", 2)
                                .accept(MediaType.APPLICATION_JSON)
                                .cookie(new Cookie("jwt", "token")))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(dialogDto.getUnreadCount()))));

    }

    @Test
    @DisplayName("test get unread dialogs, return unread count 0")
    void testGetUnread() throws Exception {

        mockMvc.perform(
                        get("/api/v1/dialogs/unread").header("id", 1)
                                .accept(MediaType.APPLICATION_JSON)
                                .cookie(new Cookie("jwt", "token")))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"count\":0")));

    }

    @Test
    @DisplayName("test get messages, return message")
    void testGetMessages() throws Exception {
        DialogDto dialogDto = generateTestDialogDto();
        Dialog dialog = saveDialogInDbAndGet(dialogDto);
        MessageDto messageDto = generateTestMessageDto(dialog.getId());
        saveMessageInDbAndGet(messageDto);

        mockMvc.perform(
                        get("/api/v1/dialogs/messages?recipientId=2").header("id", 1)
                                .accept(MediaType.APPLICATION_JSON)
                                .cookie(new Cookie("jwt", "token")))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(messageDto.getMessageText())));
    }

    @Test
    @DisplayName("test get dialog, return dialog")
    void testGetDialog() throws Exception {

        DialogDto dialogDto = generateTestDialogDto();
        saveDialogInDbAndGet(dialogDto);

        mockMvc.perform(
                        get("/api/v1/dialogs/recipientId/2").header("id", 1)
                                .accept(MediaType.APPLICATION_JSON)
                                .cookie(new Cookie("jwt", "token")))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(dialogDto.getUnreadCount()))));

    }
}
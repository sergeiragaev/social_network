package ru.skillbox.notificationservice.settings;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
public class NotificationSettingResponse {
    private LocalDateTime time;
    private ArrayList<NotificationSettingData> data;
    private Long userId;
}

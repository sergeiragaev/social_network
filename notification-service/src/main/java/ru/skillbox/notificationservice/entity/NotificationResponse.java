package ru.skillbox.notificationservice.entity;

import lombok.Getter;
import lombok.Setter;
import ru.skillbox.notificationservice.settings.NotificationSettingData;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
public class NotificationResponse {

    private LocalDateTime timeStamp;
    private ArrayList<Notification> data;

}

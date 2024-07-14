package ru.skillbox.notificationservice.settings;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationSettingData {
    private boolean enable;
    private String notificationType;

    public NotificationSettingData(boolean enable, String notificationType) {
        this.enable = enable;
        this.notificationType = notificationType;
    }
}

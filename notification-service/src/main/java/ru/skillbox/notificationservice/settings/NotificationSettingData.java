package ru.skillbox.notificationservice.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.notificationservice.entity.NotificationType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSettingData {

    private boolean enable;
    private NotificationType notificationType;

}

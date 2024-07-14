package ru.skillbox.notificationservice.settings;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notificationSettings")
public class NotificationSetting {

    private boolean emailEnabled;
    private boolean pushEnabled;
    private boolean smsEnabled;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long userId;
    private boolean friendRequest;
    private boolean friendBirthday;
    private boolean postComment;
    private boolean commentComment;
    private boolean post;
    private boolean message;
    private boolean sendPhoneMessage;
    private boolean sendEmailMessage;

    public boolean isEmailEnabled() {
        return emailEnabled;
    }

    public void setEmailEnabled(boolean emailEnabled) {
        this.emailEnabled = emailEnabled;
    }

    public boolean isPushEnabled() {
        return pushEnabled;
    }

    public void setPushEnabled(boolean pushEnabled) {
        this.pushEnabled = pushEnabled;
    }

    public boolean isSmsEnabled() {
        return smsEnabled;
    }

    public void setSmsEnabled(boolean smsEnabled) {
        this.smsEnabled = smsEnabled;
    }

    public void updateFrom(NotificationSetting otherSettings) {
        this.emailEnabled = otherSettings.isEmailEnabled();
        this.pushEnabled = otherSettings.isPushEnabled();
        this.smsEnabled = otherSettings.isSmsEnabled();
    }
}
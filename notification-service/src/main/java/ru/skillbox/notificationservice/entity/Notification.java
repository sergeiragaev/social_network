package ru.skillbox.notificationservice.entity;

public class Notification {

    private String title;
    private String message;
    private NotificationType type;

    public Notification(String title, String message, NotificationType type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public NotificationType getType() {
        return type;
    }
}


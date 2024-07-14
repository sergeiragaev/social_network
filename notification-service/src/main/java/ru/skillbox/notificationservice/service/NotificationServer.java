package ru.skillbox.notificationservice.service;

import ru.skillbox.notificationservice.entity.Notification;
import ru.skillbox.notificationservice.settings.NotificationSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NotificationServer {

    private final NotificationSetting settings;
    private final List<Notification> notifications = new ArrayList<>();
    private final AtomicInteger notificationCount = new AtomicInteger(0);

    public NotificationServer(NotificationSetting settings) {
        this.settings = settings;
    }

    // GET /api/v1/notifications/settings
    public NotificationSetting getSettings() {
        return settings;
    }

    // PUT /api/v1/notifications/settings
    public void updateSettings(NotificationSetting updatedSettings) {
        this.settings.updateFrom(updatedSettings);
    }

    // POST /api/v1/notifications/settings
    public void createSettings(NotificationSetting newSettings) {
        this.settings.updateFrom(newSettings);
    }

    // GET /api/v1/notifications
    public List<Notification> getNotifications() {
        return notifications;
    }

    // POST /api/v1/notifications
    public void addNotification(Notification notification) {
        notifications.add(notification);
        notificationCount.incrementAndGet();
    }

    // GET /api/v1/notifications/count
    public int getNotificationCount() {
        return notificationCount.get();
    }
}


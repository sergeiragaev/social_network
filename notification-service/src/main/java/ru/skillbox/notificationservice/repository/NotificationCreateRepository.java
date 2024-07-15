package ru.skillbox.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.notificationservice.entity.NotificationCreate;
import ru.skillbox.notificationservice.settings.NotificationSetting;

import java.util.Optional;

@Repository
public interface NotificationCreateRepository extends JpaRepository<NotificationCreate, Long> {
}

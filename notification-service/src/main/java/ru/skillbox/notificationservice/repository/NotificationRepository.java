package ru.skillbox.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.notificationservice.settings.NotificationSetting;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Long , NotificationSetting> {
    Optional<NotificationSetting> findByUserId(Long Id);
}

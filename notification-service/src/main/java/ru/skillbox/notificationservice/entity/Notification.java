package ru.skillbox.notificationservice.entity;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.commondto.account.AccountDto;


import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private AccountDto user;
    private String content;
    private NotificationType notificationType;
    private LocalDateTime sentTime;

}

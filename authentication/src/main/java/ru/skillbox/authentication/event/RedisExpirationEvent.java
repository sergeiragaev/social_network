package ru.skillbox.authentication.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;
import ru.skillbox.authentication.model.entity.nosql.RefreshToken;

@Component
@Slf4j
public class RedisExpirationEvent {

    @EventListener
    public void handleRedisKeyExpiredEvent(RedisKeyExpiredEvent<RefreshToken> event) {
        RefreshToken expiredRefreshToken = (RefreshToken) event.getValue();
        log.info("Refresh token with key {} has expired. Refresh token is: {}", expiredRefreshToken.getId(), expiredRefreshToken.getToken());
    }
}

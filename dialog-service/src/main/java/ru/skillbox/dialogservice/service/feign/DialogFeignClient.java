package ru.skillbox.dialogservice.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.skillbox.commonlib.dto.auth.IsOnlineRequest;
import ru.skillbox.dialogservice.model.dto.JwtRequest;

import java.util.Map;

@FeignClient(name = "dialog", url = "${app.gatewayUrl}" + "${app.apiPrefix}")
public interface DialogFeignClient {

    @PostMapping("/auth/getclaims")
    Map<String, String> validateToken(@RequestBody JwtRequest request);

    @PostMapping("/auth/setIsOnline")
    void setIsOnline(@RequestBody IsOnlineRequest request);
}

package ru.skillbox.dialogservice.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.skillbox.dialogservice.model.dto.JwtRq;

import java.util.Map;

@FeignClient(name = "dialog", url = "${app.feignClient.url}")
public interface DialogFeignClient {

    @PostMapping("/auth/getclaims")
    Map<String, String> validateToken(@RequestBody JwtRq request);
}

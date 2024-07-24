package ru.skillbox.authentication.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.authentication.model.dto.JwtRq;
import ru.skillbox.authentication.service.security.Jwt.JwtService;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FeignClientController {

    private final JwtService jwtService;

    @PostMapping("/getclaims")
    public Map<String, Object> getJwtTokenClimesHandler(@RequestBody JwtRq request) {
        return jwtService.getAllClaimsFromToken(request.getToken());
    }

}

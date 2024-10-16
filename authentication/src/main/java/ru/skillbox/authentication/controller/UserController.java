package ru.skillbox.authentication.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.authentication.exception.AlreadyExistsException;
import ru.skillbox.authentication.exception.CaptchaValidatedExcepction;
import ru.skillbox.authentication.model.dto.RegUserDto;
import ru.skillbox.authentication.model.web.*;
import ru.skillbox.authentication.repository.sql.UserRepository;
import ru.skillbox.authentication.service.AuthenticationService;
import ru.skillbox.authentication.service.CaptchaService;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final CaptchaService captchaService;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create user")
    public void createUser(@RequestBody RegUserDto userDto) {
        if (!captchaService.validateCaptcha(userDto.getCaptchaSecret()
                , userDto.getCaptchaCode())) {
            log.error("Пользователь '{}' ввел невалидную капчу.", userDto.getEmail());
            throw new CaptchaValidatedExcepction("No pass captcha");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            log.error("Ошибка регистрации. Email '{}' уже зарегистрирован.", userDto.getEmail());
            throw new AlreadyExistsException("Email уже занят");
        }
        authenticationService.register(userDto);

    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/logout")
    @Operation(summary = "Logout")
    public void logout(@RequestHeader("Authorization") String authorizationHeader) {
        authenticationService.logout(authorizationHeader);
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<AuthenticationResponse> loginUser(
            @RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.login(authenticationRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.getRefreshToken(refreshTokenRequest));
    }
}

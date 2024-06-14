package ru.skillbox.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.userservice.model.dto.AccountDto;

@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class StorageController {

    @PostMapping
    public ResponseEntity<AccountDto> uploadFile(@RequestBody MultipartFile file) {
        return ResponseEntity.ok(new AccountDto());
    }
}

package ru.skillbox.userservice.data.dto;

import lombok.Data;

@Data
public class AccountRecoveryRq {
    String email;
    String password;
}

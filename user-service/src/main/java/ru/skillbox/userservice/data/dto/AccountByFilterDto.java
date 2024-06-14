package ru.skillbox.userservice.data.dto;

import lombok.Data;

@Data
public class AccountByFilterDto {
    AccountSearchDto accountSearchDto;
    int pageSize;
    int pageNumber;
}

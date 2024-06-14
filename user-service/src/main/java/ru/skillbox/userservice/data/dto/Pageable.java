package ru.skillbox.userservice.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class Pageable {

    Integer page;
    Integer size;
    List<String> sort;
}

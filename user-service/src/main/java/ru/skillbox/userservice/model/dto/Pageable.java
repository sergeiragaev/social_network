package ru.skillbox.userservice.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class Pageable {

    Integer page;
    Integer size;
    List<String> sort;
}

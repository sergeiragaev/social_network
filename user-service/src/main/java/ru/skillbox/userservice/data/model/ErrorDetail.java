package ru.skillbox.userservice.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ErrorDetail {

    private int status;
    private long timeStamp;
    private String title;
    private String detail;
    private String developerMessage;

}

package ru.skillbox.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.skillbox.userservice.model.entity.ErrorDetail;

import java.nio.file.AccessDeniedException;
import java.util.Date;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController extends ResponseEntityExceptionHandler {


    @ExceptionHandler({ChangeSetPersister.NotFoundException.class, ObjectNotFoundException.class, HttpClientErrorException.NotFound.class})
    public ResponseEntity<?> notFoundObject(Exception exception) {
        return templateResponseException(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> accessDenied(AccessDeniedException exception) {
        return templateResponseException(exception, HttpStatus.FORBIDDEN);
    }


    private ResponseEntity<?> templateResponseException(String message, Exception exception, HttpStatus status) {
        return ResponseEntity.status(status.value())
                .body(createDefaultErrorDetail(message, exception, status.value()));
    }

    private ResponseEntity<?> templateResponseException(Exception exception, HttpStatus status) {
        return templateResponseException(exception.getMessage(), exception, status);
    }

    private ErrorDetail createDefaultErrorDetail(String message, Exception exception, int statusValue) {
        return new ErrorDetail()
                .setTitle(message)
                .setStatus(statusValue)
                .setTimeStamp(new Date().getTime())
                .setDetail(exception.getMessage())
                .setDeveloperMessage(exception.getClass().getName());
    }

}

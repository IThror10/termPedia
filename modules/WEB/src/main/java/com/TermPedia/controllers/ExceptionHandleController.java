package com.TermPedia.controllers;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.DataConflictException;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.dto.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Hidden
@RestControllerAdvice
public class ExceptionHandleController {
    @ExceptionHandler(FormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleFormatExceptions(ActionsException ex) {
        return createBody(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleNotFoundException(ActionsException ex) {
        return createBody(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity handleConflictException(ActionsException ex) {
        return createBody(ex.getMessage(), HttpStatus.CONFLICT);
    }

    private ResponseEntity createBody(String error, HttpStatus status) {
        Map<String, String> map = new HashMap<>();
        map.put("timestamp", new Date().toString());
        map.put("status", status.toString());
        map.put("error", error);
        return new ResponseEntity(map, status);
    }
}

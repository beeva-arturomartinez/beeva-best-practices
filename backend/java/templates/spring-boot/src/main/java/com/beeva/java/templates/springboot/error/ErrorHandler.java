package com.beeva.java.templates.springboot.error;

import com.beeva.java.templates.springboot.exception.MyCustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This class will handle exceptions thrown along our code
 *
 * @Author BEEVA
 */
@ControllerAdvice
public class ErrorHandler {
    @Autowired
    ObjectMapper objectMapper;

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ObjectNode> handleException(JsonProcessingException ex) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("error", "Something went wrong :(");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(objectNode);
    }

    @ExceptionHandler(MyCustomException.class)
    public ResponseEntity<ObjectNode> handleMyCustomException(MyCustomException ex) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("error", "This is a controlled exception");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(objectNode);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ObjectNode> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

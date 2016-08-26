package com.beeva.java.templates.springboot.controller;

import com.beeva.java.templates.springboot.exception.MyCustomException;
import com.beeva.java.templates.springboot.properties.ApplicationProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a sample RestController class
 *
 * @Author BEEVA
 */
@RestController
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ApplicationProperties applicationProperties;

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public String hello() throws JsonProcessingException {
        LOGGER.info("Entered /hello endpoint");

        ObjectNode result = om.createObjectNode();
        result.put("message", "Hello world!");
        result.put("property", "The value of beeva.myProperty is " + applicationProperties.getMyProperty());
        return om.writeValueAsString(result);
    }

    @RequestMapping(value = "/controlled-error", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String error() {
        throw new MyCustomException("This is a thrown exception");
    }
}

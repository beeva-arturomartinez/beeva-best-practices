package com.beeva.java.templates.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @Author BEEVA
 */
@SpringBootApplication
public class Application {
    protected Application() {}

    public static void main(String[] args){

        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }
}

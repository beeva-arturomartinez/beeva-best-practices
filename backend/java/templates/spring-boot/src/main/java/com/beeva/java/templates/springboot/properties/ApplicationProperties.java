package com.beeva.java.templates.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * It is considered a good practice to centralize all properties in a single file. We can use a ConfigurationProperties
 * bean, or just a regular bean with @Values injected.
 *
 * @Author BEEVA
 */
@ConfigurationProperties(prefix = "beeva")
@Component
public class ApplicationProperties {
    String myProperty;

    public String getMyProperty() {
        return myProperty;
    }

    public void setMyProperty(String myProperty) {
        this.myProperty = myProperty;
    }
}

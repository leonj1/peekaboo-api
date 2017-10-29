package com.josemleon.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class AddSecretContext {
    private String message;
    private String password;
    private int expiryMinutes;

    public AddSecretContext(String message, String password, int expiryMinutes) {
        this.message = message;
        this.password = password;
        this.expiryMinutes = expiryMinutes;
    }

    public String getMessage() {
        return message;
    }

    public String getPassword() {
        return password;
    }

    public int getExpiryMinutes() {
        return expiryMinutes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("message", message)
                .append("password", password)
                .append("expiryMinutes", expiryMinutes)
                .toString();
    }
}

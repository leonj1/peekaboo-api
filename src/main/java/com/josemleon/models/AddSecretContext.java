package com.josemleon.models;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class AddSecretContext {
    private String message;
    private String password;
    @SerializedName("expires_minutes")
    private int expiresMinutes;

    public AddSecretContext(String message, String password, int expiresMinutes) {
        this.message = message;
        this.password = password;
        this.expiresMinutes = expiresMinutes;
    }

    public String getMessage() {
        return message;
    }

    public String getPassword() {
        return password;
    }

    public int getExpiresMinutes() {
        return expiresMinutes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("message", message)
                .append("password", password)
                .append("expiresMinutes", expiresMinutes)
                .toString();
    }
}

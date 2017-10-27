package com.josemleon.configs;

import com.josemleon.AppProperty;
import com.josemleon.exceptions.PropertiesFileNotFoundException;

import java.io.IOException;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2016
 **/
public class AppProperties {

    private AppProperty getProperty;

    public AppProperties(AppProperty getProperty) {
        this.getProperty = getProperty;
    }

    public int getHttpServerPort() throws PropertiesFileNotFoundException, IOException {
        return Integer.parseInt(this.getProperty.value("http.server.port"));
    }

    public int getSecretDefaultExpiryMinutes() throws PropertiesFileNotFoundException, IOException {
        return Integer.parseInt(this.getProperty.value("secret.default.expiry.minutes"));
    }

    public int getSaltPasswordRounds() throws PropertiesFileNotFoundException, IOException {
        return Integer.parseInt(this.getProperty.value("password.salt.num.rounds"));
    }
}

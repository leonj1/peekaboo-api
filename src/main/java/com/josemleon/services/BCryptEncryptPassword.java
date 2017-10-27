package com.josemleon.services;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class BCryptEncryptPassword implements EncryptPassword {

    @Override
    public String encrypt(String salt, String password) {
        return BCrypt.hashpw(
                String.format(
                        "%s%s",
                        salt,
                        password
                ),
                salt
        );
    }
}

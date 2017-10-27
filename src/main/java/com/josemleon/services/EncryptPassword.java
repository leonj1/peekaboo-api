package com.josemleon.services;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public interface EncryptPassword {
    String encrypt(String salt, String password);

    class Fake implements EncryptPassword {

        @Override
        public String encrypt(String salt, String password) {
            return "encrypted";
        }
    }
}

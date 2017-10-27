package com.josemleon.services;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public interface SaltGenerator {
    String salt();

    class Fake implements SaltGenerator {

        @Override
        public String salt() {
            return "someRandomSalt";
        }
    }
}

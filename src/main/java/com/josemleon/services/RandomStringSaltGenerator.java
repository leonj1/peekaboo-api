package com.josemleon.services;

import java.security.SecureRandom;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class RandomStringSaltGenerator implements SaltGenerator {
    private int rounds;

    public RandomStringSaltGenerator(int rounds) {
        this.rounds = rounds;
    }

    @Override
    public String salt() {
        return BCrypt.genSalt(
                this.rounds,
                new SecureRandom()
        );
    }
}

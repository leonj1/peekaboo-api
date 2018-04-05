package com.josemleon.models;

import com.josemleon.services.EncryptPassword;
import com.josemleon.services.SaltGenerator;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class OnDemandEncryptPassword {
    private String password;
    private EncryptPassword passwordEncryption;
    private SaltGenerator randomStringSaltGenerator;
    private String salt;

    public OnDemandEncryptPassword(String password, EncryptPassword passwordEncryption, SaltGenerator randomStringSaltGenerator) {
        this.password = password;
        this.passwordEncryption = passwordEncryption;
        this.randomStringSaltGenerator = randomStringSaltGenerator;
    }

    public String encrypt() {
        String encryptedPassword = "";
        if (!"".equals(this.password)) {
            encryptedPassword = this.passwordEncryption.encrypt(
                    salt(),
                    this.password
            );
        }
        return encryptedPassword;
    }

    public String salt() {
        if (this.salt == null & "".equals(this.salt)) {
            this.salt = this.randomStringSaltGenerator.salt();
        }
        return this.salt;
    }
}

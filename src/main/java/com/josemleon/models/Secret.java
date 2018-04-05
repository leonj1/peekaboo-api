package com.josemleon.models;

import com.josemleon.exceptions.RequiresPasswordException;
import com.josemleon.exceptions.SecretExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Instant;

import static com.github.choonchernlim.betterPreconditions.preconditions.PreconditionFactory.expect;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class Secret {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private String message;
    private Instant createTime;
    private Instant expires;
    private String encryptedPassword;

    public Secret(String message, Instant createTime, Instant expires, String encryptedPassword) {
        this.message = message;
        this.createTime = createTime;
        this.expires = expires;
        this.encryptedPassword = encryptedPassword;
    }

    public String message(String encryptedPassword) throws RequiresPasswordException, SecretExpiredException {
        expect(encryptedPassword, "encryptedPassword").not().toBeNull().check();
        if (!encryptedPassword.equals(this.encryptedPassword)) {
            log.debug(
                    String.format(
                            "This secret password %s does not match %s",
                            this.encryptedPassword,
                            encryptedPassword
                    )
            );
            throw new RequiresPasswordException("invalid password");
        }

        if (isExpired()) {
            throw new SecretExpiredException("expired");
        }

        return this.message;
    }

    public boolean isExpired() {
        return this.expires.isBefore(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Secret secret = (Secret) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(message, secret.message)
                .append(createTime, secret.createTime)
                .append(expires, secret.expires)
                .append(encryptedPassword, secret.encryptedPassword)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(message)
                .append(createTime)
                .append(expires)
                .append(encryptedPassword)
                .toHashCode();
    }
}

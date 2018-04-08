package com.josemleon.services;

import com.josemleon.exceptions.NotFoundException;
import com.josemleon.exceptions.RequiresPasswordException;
import com.josemleon.exceptions.SecretExpiredException;
import com.josemleon.models.QueueSummary;
import com.josemleon.models.Secret;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.github.choonchernlim.betterPreconditions.preconditions.PreconditionFactory.expect;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class SecretService {
    private Map<UUID, Secret> secrets;
    private EncryptPassword passwordEncryption;

    public SecretService(Map<UUID, Secret> secrets, EncryptPassword passwordEncryption) {
        this.secrets = secrets;
        this.passwordEncryption = passwordEncryption;
    }

    public UUID addSecret(Secret secret) {
        expect(secret, "secret").not().toBeNull().check();
        UUID uuid = UUID.randomUUID();
        this.secrets.put(
                uuid,
                secret
        );
        return uuid;
    }

    public String getSecret(UUID id, String password) throws NotFoundException, SecretExpiredException, RequiresPasswordException {
        expect(id, "id").not().toBeNull().check();
        expect(password, "secret").not().toBeNull().check();

        if (!this.secrets.containsKey(id)) {
            throw new NotFoundException("entry does not exist");
        }

        Secret secret = this.secrets.get(id);
        if (secret.isExpired()) {
            this.secrets.remove(id);
            throw new SecretExpiredException("expired");
        }

        String encrypted = "";
        if (!"".equals(password)) {
            encrypted = this.passwordEncryption.encrypt(
                    secret.salt(),
                    password
            );
        }

        String message = secret.message(encrypted);
        this.secrets.remove(id);
        return message;
    }

    public QueueSummary fetchQueueStats() {
        int numUnexpiredEntries = 0;
        Set<UUID> keys = this.secrets.keySet();

        for(UUID id : keys) {
            Secret secret = this.secrets.get(id);
            if (!secret.isExpired()) {
                numUnexpiredEntries++;
            }
        }

        return new QueueSummary(
                keys.size(),
                numUnexpiredEntries
        );
    }
}

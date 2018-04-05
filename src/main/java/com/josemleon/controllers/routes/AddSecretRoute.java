package com.josemleon.controllers.routes;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.josemleon.controllers.SimpleExitRoute;
import com.josemleon.models.AddSecretContext;
import com.josemleon.models.Secret;
import com.josemleon.services.EncryptPassword;
import com.josemleon.services.SaltGenerator;
import com.josemleon.services.SecretService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class AddSecretRoute implements Route {
    private SecretService secretService;
    private EncryptPassword passwordEncryption;
    private SaltGenerator randomStringSaltGenerator;
    private int secretDefaultExpiryMinutes;
    private Gson gson;

    public AddSecretRoute(SecretService secretService, EncryptPassword passwordEncryption, SaltGenerator randomStringSaltGenerator, int secretDefaultExpiryMinutes) {
        this.secretService = secretService;
        this.passwordEncryption = passwordEncryption;
        this.randomStringSaltGenerator = randomStringSaltGenerator;
        this.secretDefaultExpiryMinutes = secretDefaultExpiryMinutes;
        this.gson = new Gson();
    }

    public String execute(Response res, String payload) {
        AddSecretContext context;
        try {
            context = this.gson.fromJson(payload, AddSecretContext.class);
        } catch (JsonSyntaxException e) {
            return SimpleExitRoute.builder(res).BAD_REQUEST_400().text("invalid json", e);
        }

        String encryptedPassword = "";
        String salt = this.randomStringSaltGenerator.salt();
        if (!"".equals(context.getPassword())) {
            encryptedPassword = this.passwordEncryption.encrypt(
                    salt,
                    context.getPassword()
            );
        }

        UUID uuid = this.secretService.addSecret(
                new Secret(
                        context.getMessage(),
                        Instant.now(),
                        Instant.now().plus(
                                context.getExpiryMinutes() > 0 ? context.getExpiryMinutes() : this.secretDefaultExpiryMinutes,
                                ChronoUnit.MINUTES
                        ),
                        encryptedPassword,
                        salt
                )
        );

        return SimpleExitRoute.builder(res).OK_200().text(uuid.toString());
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return execute(response, request.body());
    }
}

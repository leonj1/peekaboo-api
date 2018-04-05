package com.josemleon.controllers.routes;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.josemleon.controllers.SimpleExitRoute;
import com.josemleon.models.AddSecretContext;
import com.josemleon.models.OnDemandEncryptPassword;
import com.josemleon.models.Secret;
import com.josemleon.services.EncryptPassword;
import com.josemleon.services.SaltGenerator;
import com.josemleon.services.SecretService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

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

    public AddSecretRoute(SecretService secretService, EncryptPassword passwordEncryption, SaltGenerator randomStringSaltGenerator, int secretDefaultExpiryMinutes, Gson gson) {
        this.secretService = secretService;
        this.passwordEncryption = passwordEncryption;
        this.randomStringSaltGenerator = randomStringSaltGenerator;
        this.secretDefaultExpiryMinutes = secretDefaultExpiryMinutes;
        this.gson = gson;
    }

    public String execute(Response res, String payload) {
        AddSecretContext context;
        try {
            context = this.gson.fromJson(payload, AddSecretContext.class);
            OnDemandEncryptPassword onDemandEncryptPassword = new OnDemandEncryptPassword(
                    context.getPassword(),
                    this.passwordEncryption,
                    this.randomStringSaltGenerator
            );
            return SimpleExitRoute
                    .builder(res)
                    .OK_200()
                    .text(
                            this.secretService.addSecret(
                                    new Secret(
                                            context.getMessage(),
                                            Instant.now(),
                                            Instant.now().plus(
                                                    context.getExpiryMinutes() > 0 ? context.getExpiryMinutes() : this.secretDefaultExpiryMinutes,
                                                    ChronoUnit.MINUTES
                                            ),
                                            onDemandEncryptPassword.encrypt(),
                                            onDemandEncryptPassword.salt()
                                    )
                            ).toString()
                    );
        } catch (JsonSyntaxException e) {
            return SimpleExitRoute
                    .builder(res)
                    .BAD_REQUEST_400()
                    .text("invalid json", e);
        }
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return execute(response, request.body());
    }
}

package com.josemleon.controllers;

import com.josemleon.controllers.routes.AddSecretRoute;
import com.josemleon.controllers.routes.GetSecretWithPasswordRoute;
import com.josemleon.controllers.routes.HealthCheckRoute;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class SecretsController implements Controller {
    private HealthCheckRoute healthCheckRoute;
    private AddSecretRoute addSecretRoute;
    private GetSecretWithPasswordRoute getSecretWithPasswordRoute;

    public SecretsController(HealthCheckRoute healthCheckRoute, AddSecretRoute addSecretRoute, GetSecretWithPasswordRoute getSecretWithPasswordRoute) {
        this.healthCheckRoute = healthCheckRoute;
        this.addSecretRoute = addSecretRoute;
        this.getSecretWithPasswordRoute = getSecretWithPasswordRoute;
    }

    @Override
    public void expose() {
        get("/health", this.healthCheckRoute);
        get("/secrets/:uuid/:password", this.getSecretWithPasswordRoute);
        get("/secrets/:uuid", this.getSecretWithPasswordRoute);
        post("/secrets", this.addSecretRoute);
    }
}

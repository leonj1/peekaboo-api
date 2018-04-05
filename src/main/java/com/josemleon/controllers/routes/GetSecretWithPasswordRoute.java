package com.josemleon.controllers.routes;

import com.josemleon.controllers.SimpleExitRoute;
import com.josemleon.exceptions.NotFoundException;
import com.josemleon.exceptions.RequiresPasswordException;
import com.josemleon.exceptions.SecretExpiredException;
import com.josemleon.services.SecretService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.UUID;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class GetSecretWithPasswordRoute implements Route {
    private SecretService secretService;

    public GetSecretWithPasswordRoute(SecretService secretService) {
        this.secretService = secretService;
    }

    public String execute(Response res, UUID uuid, String password) {
        try {
            return this.secretService.getSecret(
                    uuid,
                    password
            );
        } catch (NotFoundException e) {
            return SimpleExitRoute.builder(res).NOT_FOUND_404().text(e.getMessage());
        } catch (SecretExpiredException e) {
            return SimpleExitRoute.builder(res).FORBIDDEN_403().text(e.getMessage());
        } catch (RequiresPasswordException e) {
            return SimpleExitRoute.builder(res).UNAUTHORIZED_401().text(e.getMessage());
        }
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        UUID id;
        try {
            id = UUID.fromString(request.params("uuid"));
            String password = "";
            if (null != request.params("password")) {
                password = request.params("password");
            }
            return execute(response, id, password);
        } catch (Exception e) {
            return SimpleExitRoute.builder(response).BAD_REQUEST_400().text("invalid UUID", e);
        }
    }
}

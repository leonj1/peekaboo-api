package com.josemleon.controllers.routes;

import com.josemleon.controllers.SimpleExitRoute;
import com.josemleon.models.QueueSummary;
import com.josemleon.services.SecretService;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class FetchQueueStatsRoute implements Route {
    private SecretService secretService;

    public FetchQueueStatsRoute(SecretService secretService) {
        this.secretService = secretService;
    }

    public String execute(Response res) {
        return SimpleExitRoute.builder(res)
                .OK_200()
                .json(
                        this.secretService.fetchQueueStats(),
                        QueueSummary.class
                );
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return execute(response);
    }
}

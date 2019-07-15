package com.josemleon.controllers;

import com.josemleon.controllers.routes.AddSecretRoute;
import com.josemleon.controllers.routes.FetchQueueStatsRoute;
import com.josemleon.controllers.routes.GetSecretWithPasswordRoute;
import com.josemleon.controllers.routes.HealthCheckRoute;
import com.josemleon.controllers.routes.rooms.AddMessageRoute;
import com.josemleon.controllers.routes.rooms.CreateRoomRoute;
import com.josemleon.controllers.routes.rooms.DeleteRoomRoute;
import com.josemleon.controllers.routes.rooms.FetchRoomRoute;

import static spark.Spark.delete;
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
    private FetchQueueStatsRoute fetchQueueStatsRoute;
    private String statsPassword;
    private CreateRoomRoute createRoomRoute;
    private FetchRoomRoute fetchRoomRoute;
    private AddMessageRoute addMessageRoute;
    private DeleteRoomRoute deleteRoomRoute;

    public SecretsController(HealthCheckRoute healthCheckRoute, AddSecretRoute addSecretRoute, GetSecretWithPasswordRoute getSecretWithPasswordRoute, FetchQueueStatsRoute fetchQueueStatsRoute, String statsPassword, CreateRoomRoute createRoomRoute, FetchRoomRoute fetchRoomRoute, AddMessageRoute addMessageRoute, DeleteRoomRoute deleteRoomRoute) {
        this.healthCheckRoute = healthCheckRoute;
        this.addSecretRoute = addSecretRoute;
        this.getSecretWithPasswordRoute = getSecretWithPasswordRoute;
        this.fetchQueueStatsRoute = fetchQueueStatsRoute;
        this.statsPassword = statsPassword;
        this.createRoomRoute = createRoomRoute;
        this.fetchRoomRoute = fetchRoomRoute;
        this.addMessageRoute = addMessageRoute;
        this.deleteRoomRoute = deleteRoomRoute;
    }

    @Override
    public void expose() {
        get("/health", this.healthCheckRoute);
        get("/secrets/:uuid/:password", this.getSecretWithPasswordRoute);
        get("/secrets/:uuid", this.getSecretWithPasswordRoute);
        post("/secrets", this.addSecretRoute);
        get("/stats/secrets/" + this.statsPassword, this.fetchQueueStatsRoute);

        // rooms
        post("/rooms", this.createRoomRoute);
        get("/room", this.fetchRoomRoute);
        post("/room/:roomId", this.addMessageRoute);
        delete("/room/:roomId", this.deleteRoomRoute);
    }
}

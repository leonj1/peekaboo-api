package com.josemleon.controllers.routes.rooms;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.josemleon.context.CreateRoomContext;
import com.josemleon.controllers.SimpleExitRoute;
import com.josemleon.models.Room;
import com.josemleon.services.RoomService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class CreateRoomRoute implements Route {
    private RoomService roomService;
    private Gson gson;

    public CreateRoomRoute(RoomService roomService) {
        this.roomService = roomService;
        this.gson = new Gson();
    }

    private String execute(Response res) {
        CreateRoomContext context = null;
        try {
            context = this.gson.fromJson(res.body(), CreateRoomContext.class);
        } catch (JsonSyntaxException e) {
            return SimpleExitRoute.builder(res)
                    .BAD_REQUEST_400()
                    .text("invalid json");
        }
        Instant expires = Instant.now().plus(context.getExpires(), ChronoUnit.DAYS);
        return SimpleExitRoute.builder(res)
                .OK_200()
                .text(
                        this.roomService.createRoom(
                                new Room(
                                        expires,
                                        context.getParticipants()
                                )
                        )
                );
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return execute(response);
    }
}

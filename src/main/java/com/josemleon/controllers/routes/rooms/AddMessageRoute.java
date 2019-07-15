package com.josemleon.controllers.routes.rooms;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.josemleon.context.AddMessageContext;
import com.josemleon.controllers.SimpleExitRoute;
import com.josemleon.exceptions.ParticipantDoesNotExist;
import com.josemleon.exceptions.RoomDoesNotExist;
import com.josemleon.exceptions.RoomHasExpired;
import com.josemleon.models.Message;
import com.josemleon.services.RoomService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.Instant;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class AddMessageRoute implements Route {
    private RoomService roomService;
    private Gson gson;

    public AddMessageRoute(RoomService roomService) {
        this.roomService = roomService;
        this.gson = new Gson();
    }

    private String execute(Response res, String roomId, String participant, String message) {
        try {
            this.roomService.addMessage(
                    roomId,
                    new Message(
                            Instant.now(),
                            participant,
                            message
                    )
            );
            return SimpleExitRoute.builder(res)
                    .OK_200()
                    .text("");
        } catch (RoomDoesNotExist|RoomHasExpired|ParticipantDoesNotExist e) {
            return SimpleExitRoute.builder(res)
                    .FORBIDDEN_403()
                    .text("sorry");
        }
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        AddMessageContext context = null;
        try {
            context = this.gson.fromJson(request.body(), AddMessageContext.class);
        } catch (JsonSyntaxException e) {
            return SimpleExitRoute.builder(response)
                    .BAD_REQUEST_400()
                    .text("invalid json");
        }
        return execute(response, request.queryParams("roomId"), context.getParticipant(), context.getMessage());
    }
}

package com.josemleon.controllers.routes.rooms;

import com.josemleon.controllers.SimpleExitRoute;
import com.josemleon.services.RoomService;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class DeleteRoomRoute implements Route {
    private RoomService roomService;

    public DeleteRoomRoute(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        this.roomService.deleteRoom(request.queryParams("roomId"));
        return SimpleExitRoute.builder(response)
                .OK_200()
                .text("done");
    }
}

package com.josemleon.controllers.routes.rooms;

import com.josemleon.controllers.SimpleExitRoute;
import com.josemleon.exceptions.ParticipantDoesNotExist;
import com.josemleon.exceptions.RoomDoesNotExist;
import com.josemleon.exceptions.RoomHasExpired;
import com.josemleon.models.Room;
import com.josemleon.services.RoomService;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.utils.StringUtils;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class FetchRoomRoute implements Route {
    private RoomService roomService;

    public FetchRoomRoute(RoomService roomService) {
        this.roomService = roomService;
    }

    private String execute(Response res, String roomId, String participant) {
        try {
            Room room = this.roomService.fetchRoom(roomId, participant);
            return SimpleExitRoute.builder(res)
                    .OK_200()
                    .json(room, Room.class);
        } catch (RoomDoesNotExist|ParticipantDoesNotExist|RoomHasExpired e) {
            return SimpleExitRoute.builder(res)
                    .FORBIDDEN_403()
                    .text("sorry");
        }
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String roomId = request.queryParams("roomId");
        String participant = request.headers("participant");
        if(StringUtils.isEmpty(participant)) {
            return SimpleExitRoute.builder(response)
                    .BAD_REQUEST_400()
                    .text("need participant");
        }
        if(StringUtils.isEmpty(roomId)) {
            return SimpleExitRoute.builder(response)
                    .BAD_REQUEST_400()
                    .text("id required");
        }
        return execute(response, participant, roomId);
    }
}

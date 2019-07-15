package com.josemleon.services;

import com.josemleon.exceptions.ParticipantDoesNotExist;
import com.josemleon.exceptions.RoomDoesNotExist;
import com.josemleon.exceptions.RoomHasExpired;
import com.josemleon.models.Message;
import com.josemleon.models.Room;
import com.josemleon.sockets.ChatWebSocketHandler;

import java.util.Map;
import java.util.UUID;

import static com.github.choonchernlim.betterPreconditions.preconditions.PreconditionFactory.expect;
import static spark.Spark.webSocket;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class RoomService {
    private Map<String, Room> rooms;

    public RoomService(Map<String, Room> rooms) {
        this.rooms = rooms;
    }

    // create the room and return the room id
    public String createRoom(Room room) {
        expect(room, "room").not().toBeNull().check();
        boolean found = false;
        while(!found) {
            UUID id = UUID.randomUUID();
            String[] tokens = id.toString().split("-");
            if(this.rooms.get(tokens[0]) != null) {
                this.rooms.put(tokens[0], room);
                // TODO Spark need to re-init to register the handler
                webSocket(
                        "/" + tokens[0],
                        new ChatWebSocketHandler()
                );
                return tokens[0];
            }
        }
        return "";
    }

    // TODO Broadcast which messages have been read by participant
    public Room fetchRoom(String roomId, String participant) throws RoomDoesNotExist, ParticipantDoesNotExist, RoomHasExpired {
        expect(roomId, "roomId").not().toBeNull().not().toBeBlank().check();
        expect(participant, "participant").not().toBeNull().not().toBeBlank().check();
        Room room = this.rooms.get(roomId);
        if(room == null) {
            throw new RoomDoesNotExist("");
        }
        if(!room.participants().contains(participant)) {
            throw new ParticipantDoesNotExist("");
        }
        if(room.isExpired()) {
            throw new RoomHasExpired("");
        }
        // TODO mark all messages in this room read by this participant
        return room;
    }

    // TODO broadcast that room has been deleted
    public void deleteRoom(String roomId) {
        expect(roomId, "roomId").not().toBeNull().not().toBeBlank().check();
        Room room = this.rooms.get(roomId);
        if(room != null) {
            this.rooms.remove(roomId);
        }
        // Broadcast here
    }

    // TODO broadcast message creation
    public void addMessage(String roomId, Message message) throws RoomDoesNotExist, RoomHasExpired, ParticipantDoesNotExist {
        expect(roomId, "roomId").not().toBeNull().not().toBeBlank().check();
        expect(message, "message").not().toBeNull().check();
        Room room = fetchRoom(roomId, message.creator());
        this.rooms.put(roomId, room.addMessage(message));
        // Broadcast here
    }
}

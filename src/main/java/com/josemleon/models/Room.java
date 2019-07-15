package com.josemleon.models;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class Room {
    @SerializedName("create_date")
    private Instant createDate;
    private Instant expires;
    private List<Message> messages;
    private List<String> participants;

    public Room(Instant expires, List<String> participants) {
        this.expires = expires;
        this.participants = participants;
    }

    public Room(Room room, Message message) {
        this.createDate = room.createDate;
        this.expires = room.expires;
        this.participants = room.participants;
        this.messages = room.messages;
        this.messages.add(message);
    }

    public Room addMessage(Message message) {
        return new Room(this, message);
    }

    public boolean isExpired() {
        return this.expires.isBefore(Instant.now());
    }

    // getters + setters

    public Instant createDate() {
        return createDate;
    }

    public Instant expires() {
        return expires;
    }

    public List<Message> messages() {
        return messages;
    }

    public List<String> participants() {
        return participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return expires == room.expires &&
                Objects.equals(createDate, room.createDate) &&
                Objects.equals(participants, room.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createDate, expires, participants);
    }

    @Override
    public String toString() {
        return "Room{" +
                ", createDate=" + createDate +
                ", expires=" + expires +
                ", messages=" + messages +
                ", participants=" + participants +
                '}';
    }
}

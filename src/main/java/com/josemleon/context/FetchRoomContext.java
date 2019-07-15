package com.josemleon.context;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class FetchRoomContext {
    private String participant;

    public FetchRoomContext(String participant) {
        this.participant = participant;
    }

    public String getParticipant() {
        return participant;
    }
}

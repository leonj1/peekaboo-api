package com.josemleon.context;

import java.util.List;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class CreateRoomContext {
    private long expires;
    private List<String> participants;

    public CreateRoomContext(long expires, List<String> participants) {
        this.expires = expires;
        this.participants = participants;
    }

    public long getExpires() {
        return expires;
    }

    public List<String> getParticipants() {
        return participants;
    }
}

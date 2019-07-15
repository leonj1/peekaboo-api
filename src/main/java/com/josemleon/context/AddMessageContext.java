package com.josemleon.context;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class AddMessageContext {
    private String message;
    private String participant;

    public AddMessageContext(String message, String participant) {
        this.message = message;
        this.participant = participant;
    }

    public String getMessage() {
        return message;
    }

    public String getParticipant() {
        return participant;
    }
}

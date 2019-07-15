package com.josemleon.models;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class Message {
    @SerializedName("create_date")
    private Instant createDate;
    private String creator;
    private String message;
    @SerializedName("read_by")
    private List<String> readBy;

    public Message(Instant createDate, String creator, String message) {
        this.createDate = createDate;
        this.creator = creator;
        this.message = message;
        this.readBy = new ArrayList<String>(){{ add(creator); }};
    }

    // getters + setters

    public Instant createDate() {
        return createDate;
    }

    public String creator() {
        return creator;
    }

    public String message() {
        return message;
    }

    public List<String> readBy() {
        return readBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(createDate, message1.createDate) &&
                Objects.equals(creator, message1.creator) &&
                Objects.equals(message, message1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createDate, creator, message);
    }

    @Override
    public String toString() {
        return "Message{" +
                "created=" + createDate +
                ", creator='" + creator + '\'' +
                ", message='" + message + '\'' +
                ", readBy=" + readBy +
                '}';
    }
}

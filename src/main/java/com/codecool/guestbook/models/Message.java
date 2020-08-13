package com.codecool.guestbook.models;

public class Message {

    private final String message;
    private final String name;
    private final String  date;

    public Message(String message, String name) {
        this(message, name, "");
    }

    public Message(String message, String name, String date) {
        this.message = message;
        this.name = name;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}

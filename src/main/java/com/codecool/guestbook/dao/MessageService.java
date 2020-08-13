package com.codecool.guestbook.dao;

import com.codecool.guestbook.models.Message;
import com.google.gson.Gson;

public class MessageService {

    private Dao<Message> messageDao = new MessageDao();
    private Gson gson = new Gson();

    public String getAll() {
        return gson.toJson(messageDao.getAll());
    }

    public void save(String json) {
        messageDao.save(gson.fromJson(json, Message.class));
    }

}

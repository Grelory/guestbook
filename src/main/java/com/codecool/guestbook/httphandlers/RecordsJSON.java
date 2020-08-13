package com.codecool.guestbook.httphandlers;

import com.codecool.guestbook.dao.MessageService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;

public class RecordsJSON implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        MessageService messageService = new MessageService();
        String messagesJson = messageService.getAll();
        System.out.println(messagesJson);

        exchange.getResponseHeaders().put("Content-type", Collections.singletonList("application/json"));
        exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
        exchange.sendResponseHeaders(200, messagesJson.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(messagesJson.getBytes());
        outputStream.close();
    }
}

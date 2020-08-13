package com.codecool.guestbook;


import com.codecool.guestbook.dao.MessageService;
import com.codecool.guestbook.httphandlers.MessageReceiver;
import com.codecool.guestbook.httphandlers.RecordsJSON;
import com.codecool.guestbook.httphandlers.Static;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App
{
    public static void main( String[] args ) throws IOException {

        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("/guestbook", new Static());
        server.createContext("/static/css/guestBookStyle.css", new Static());
        server.createContext("/static/js/guestBookScript.js", new Static());
        server.createContext("/records", new RecordsJSON());
        server.createContext("/message-receiver", new MessageReceiver());
        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();

    }
}

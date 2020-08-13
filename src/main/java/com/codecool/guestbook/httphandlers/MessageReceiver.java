package com.codecool.guestbook.httphandlers;

import com.codecool.guestbook.dao.MessageService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class MessageReceiver implements HttpHandler {

    private MessageService service = new MessageService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String response = "";
        String method = exchange.getRequestMethod(); // "POST" or "GET"

        if (method.equals("POST")) {
            // retrieve data
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);

            String jsonData = parseFormData(br.readLine());
            service.save(jsonData);
            response = "data saved";
        }

        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private String parseFormData(String formData) throws UnsupportedEncodingException {
        String[] pairs = formData.split("&");
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i = 0; i < pairs.length; i++) {
            String[] keyValue = pairs[i].split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            sb.append("\"");
            sb.append(keyValue[0]);
            sb.append("\": \"");
            sb.append(value);
            sb.append(i!=pairs.length-1 ? "\",": "\"");
        }
        sb.append("}");
        return sb.toString();

    }

}

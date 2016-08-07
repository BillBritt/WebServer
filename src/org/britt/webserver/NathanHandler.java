package org.britt.webserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class NathanHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String text = "<h1>Server start success if you see this message</h1>" + "<h1>Port: " + 9000 + "</h1>DAD IS NOT A JERK!!!";

        sendTextResponse(httpExchange, text);
    }

    private void sendTextResponse(HttpExchange httpExchange, String text) throws IOException {
        httpExchange.sendResponseHeaders(200, text.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(text.getBytes());
        os.close();
    }
}

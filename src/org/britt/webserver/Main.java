package org.britt.webserver;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {
        int port = 9000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("server started at " + port);
        server.createContext("/nathan", new NathanHandler());
        server.createContext("/bill", new BillHandler());
        server.setExecutor(null);
        server.start();
    }
}

package org.example.lesson14;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = startProductServer();

        try {
            String baseUrl = "http://localhost:" + server.getAddress().getPort();
            ProductApiClient client = new ProductApiClient(HttpClient.newHttpClient(), baseUrl);
            System.out.println("Response: " + client.fetchProductJson("P100"));
        } finally {
            server.stop(0);
        }
    }

    private static HttpServer startProductServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext("/products/P100", Main::handleProductRequest);
        server.start();
        return server;
    }

    private static void handleProductRequest(HttpExchange exchange) throws IOException {
        String accept = exchange.getRequestHeaders().getFirst("Accept");
        if (!"application/json".equals(accept)) {
            exchange.sendResponseHeaders(406, -1);
            exchange.close();
            return;
        }

        byte[] body = "{\"id\":\"P100\",\"name\":\"Keyboard\"}"
                .getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, body.length);
        exchange.getResponseBody().write(body);
        exchange.close();
    }
}

package de.max.education.exercise1;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;

public class Solution implements ExerciseInterface {

    public HttpServer startServer() {
        InetSocketAddress port = new InetSocketAddress(8080);
        HttpServer server = SimpleFileServer.createFileServer(port, Path.of("").toAbsolutePath(), SimpleFileServer.OutputLevel.INFO);
        server.start();
        return server;
    }

    public HttpServer startServerWithHandler() {
        InetSocketAddress port = new InetSocketAddress(8080);
        HttpServer server = SimpleFileServer.createFileServer(port, Path.of("").toAbsolutePath(), SimpleFileServer.OutputLevel.INFO);
        var handler = SimpleFileServer.createFileHandler(Path.of("src/main/resources").toAbsolutePath());
        server.createContext("/workshop", handler);
        server.start();
        return server;
    }

    public void startServerWithCustomHandler() {
        InetSocketAddress port = new InetSocketAddress(8080);
        var putHandler = HttpHandlers.of(200, Headers.of("X-method", "PUT"), "Custom PUT body");
        var failureHandler = HttpHandlers.of(501, Headers.of("X-method", "not GET/PUT"), "Request method not covered by handler");
        var putOrErrorHandler = HttpHandlers.handleOrElse(r -> r.getRequestMethod().equals("PUT"), putHandler, failureHandler);
        var getHandler = HttpHandlers.of(200, Headers.of("X-method", "GET"), "Custom GET body");
        var combinedGetHandler = HttpHandlers.handleOrElse(r -> r.getRequestMethod().equals("GET"), getHandler, putOrErrorHandler);
        HttpServer server;
        try {
            server = HttpServer.create(port, 10, "/", combinedGetHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.start();
    }
}

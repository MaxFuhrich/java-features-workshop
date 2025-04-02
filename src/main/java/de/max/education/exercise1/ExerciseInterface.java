package de.max.education.exercise1;

import com.sun.net.httpserver.HttpServer;

public interface ExerciseInterface {
    //TODO r
    HttpServer startServer();
    HttpServer startServerWithHandler();
    //HttpServer startServerWithCustomHandler()
    //TODO: check if this is really a new feature
}

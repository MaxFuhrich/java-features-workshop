package de.max.education.exercise3;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Main {

    /*
    Task 1: Increase the number of tasks. What happens if you increase the value too much?
    Task 2: Refactor the method manyThreads to make use of virtual threads. Does the method run with the amount of tasks
    that caused an exception earlier?
    For the solution, check the Solution class (also, if you want, you can create an object of that type instead of
    Exercise();
     */
    public static void main(String[] args) {
        int numberOfTasks = 1000;
        ExerciseInterface exercise = new Exercise();
        exercise.manyThreads(numberOfTasks);
    }



    static void virtualThreads(int numberOfTasks) {

    }
}



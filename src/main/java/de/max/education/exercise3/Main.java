package de.max.education.exercise3;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Main {

    /*
    Exercise 3: Virtual Threads
    Task 1: Increase numberOfTasks. What happens if you increase the value too much?
    Task 2: Refactor the method manyThreads to make use of virtual threads. Does the method run with the amount of tasks
    that caused an exception earlier?
    For the solution, check the Solution class (also, if you want to, you can create an object of that type instead of
    Exercise();
     */
    public static void main(String[] args) {
        int numberOfTasks = 10;
        ExerciseInterface exercise = new Exercise();
        exercise.manyThreads(numberOfTasks);
    }


    static void threadsWaitingTwoSeconds(int numberOfTasks, ExecutorService executor) throws InterruptedException, ExecutionException {
        Future<Integer> f = null;
        long startTime = System.nanoTime();
        for (int i = 0; i < numberOfTasks; i++) {
            f = executor.submit(() -> {
                try {
                    Thread.sleep(Duration.ofSeconds(2));
                    return 0;
                } catch (InterruptedException e) {
                    return 1;
                }
            });
        }
        assert f != null;
        f.get();
        executor.shutdown();
        long stopTime = System.nanoTime();
        System.out.println("Platform threads finished");
        System.out.println("Execution time: " + Duration.ofNanos(stopTime - startTime).toMillis() + "ms");
    }
}



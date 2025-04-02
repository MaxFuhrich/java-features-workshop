package de.max.education.exercise3;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Solution implements ExerciseInterface{
    @Override
    public void manyThreads(int numberOfTasks) {
        long startTime = System.nanoTime();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) { //alternatively, use newFixedThreadPool(int nThreads)
            IntStream.range(0, numberOfTasks).forEach(i -> {
                Future<Integer> f = executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(2));
                    return i;
                });
                //System.out.println("Task " + i + ": " + f.state());
            });
            executor.shutdown();
            if (executor.awaitTermination(1, TimeUnit.DAYS)) {
                long stopTime = System.nanoTime();
                System.out.println("Platform threads finished");
                System.out.println("Execution time: " + Duration.ofNanos(stopTime - startTime).toMillis() + "ms");
            }
        } catch (Exception e) {
            System.out.println("Platform threads failed: " + e.getMessage());
        }
    }
}

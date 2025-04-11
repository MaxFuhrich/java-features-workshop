package de.max.education.exercise3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Solution implements ExerciseInterface{

    @Override
    public void manyThreads(int numberOfTasks) {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Main.threadsWaitingTwoSeconds(numberOfTasks, executor);
        } catch (Exception e) {
            System.out.println("Platform threads failed: " + e.getMessage());
        }
    }


}

package de.max.education.exercise3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Exercise implements ExerciseInterface{
    @Override
    public void manyThreads(int numberOfTasks) {
        ThreadFactory tf = Thread::new;
        //Executors.newThreadPerTaskExecutor()
        //Executors.newCachedThreadPool()
        try (ExecutorService executor = Executors.newThreadPerTaskExecutor(tf)) { //alternatively, use newFixedThreadPool(int nThreads)
            Main.threadsWaitingTwoSeconds(numberOfTasks, executor);
        } catch (Exception e) {
            System.out.println("Platform threads failed: " + e.getMessage());
        }
    }
}

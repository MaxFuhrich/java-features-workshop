package de.max.education.exercise1;

/*
Exercise 1: Simple Webservers
The tasks are described in the class Exercise. The solution can be found in the class Solution. Additionally, you can
create a Solution object instead of Exercise to see it in action.
 */

public class Main {
    public static void main(String[] args) {
        ExerciseInterface exercise = new Exercise();
        exercise.startServer();
        //exercise.startServerWithHandler();
    }

}

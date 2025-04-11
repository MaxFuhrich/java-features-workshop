package de.max.education.exercise4;

import java.util.*;

public class Main {

    /*
    Exercise 4: Sequenced Collections
    Refactor the methods of the ExerciseInterface implemented in Exercise (or create a new class)
    For the solution, check the Solution class (also, if you want to, you can create an object of that type instead of
    Exercise();
     */
    public static void main(String[] args) {
        //setup
        List<String> numberList = new ArrayList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        NavigableSet<String> navSetNumbers = new TreeSet<>(numberList);
        NavigableMap<String, String> navMapNumbers = makeNavMap();
        LinkedHashSet<String> linkedHashSetNumbers = new LinkedHashSet<>(numberList);

        //for the solution, see the Solution class (and/or create a new object of that type instead of Exercise
        ExerciseInterface exercise = new Exercise();

        System.out.println("Reversed list:");
        exercise.reversedList(numberList);
        System.out.println("Reversed NavigableSet:");
        exercise.reversedSet(navSetNumbers);
        System.out.println("Reversed NavigableMap:");
        exercise.reversedNavigableMap(navMapNumbers);
        System.out.println("Last Element LinkedHashSet:");
        exercise.linkedHashSetLastElement(linkedHashSetNumbers);
    }


    static NavigableMap<String, String> makeNavMap() {
        NavigableMap<String, String> navMap = new TreeMap<>();
        navMap.put("1", "One");
        navMap.put("2", "Two");
        navMap.put("3", "Three");
        navMap.put("4", "Four");
        navMap.put("5", "Five");
        return navMap;
    }

}



package de.max.education.exercise2;

import de.max.education.exercise2.records.*;

import java.util.ArrayList;
import java.util.List;

/*
Exercise 2: Record Patterns and Pattern Matching for Switch
Task 1: Refactor the Methods of the class Exercise to make use of record patterns and pattern matching for switch.
The solution can be found in the class Solution. Additionally, you can create a Solution object instead of Exercise to
see it in action.
 */
public class Main {

    public static void main(String[] args) {
        List<Animal> animals = setUpAnimals();
        ExerciseInterface  exercise= new Exercise();

        exercise.printDogNamesAndTricks(animals);

        Zoo sortedAnimals = exercise.animalListsByType(animals);
        System.out.println("Dogs: " + sortedAnimals.dogs());
        System.out.println("Cats: " + sortedAnimals.cats());
        System.out.println("Birds: " + sortedAnimals.birds());
    }

    static List<Animal> setUpAnimals() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Dog("Rufus", "Sit, Stay, Down"));
        animals.add(new Cat("Garfield"));
        animals.add(new Dog("Pluto", "Sit, Roll over, Stay"));
        animals.add(new Bird("Tweety"));
        animals.add(new Dog("Rush", "Sit, Down"));
        animals.add(new Cat("Onyx"));
        animals.add(new Cat("Felix"));
        return animals;
    }



}

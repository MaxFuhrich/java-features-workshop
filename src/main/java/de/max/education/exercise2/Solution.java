package de.max.education.exercise2;

import de.max.education.exercise2.records.*;

import java.util.ArrayList;
import java.util.List;

public class Solution implements ExerciseInterface{

    @Override
    public void printDogNamesAndTricks(List<Animal> animals) {
        for (Animal animal : animals) {
            if (animal instanceof Dog(String name, String tricks)) {
                System.out.println(name + " - Known tricks: " + tricks);
            }
        }
    }

    @Override
    public Zoo animalListsByType(List<Animal> animals) {
        Zoo sortedAnimals = new Zoo(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        for (Animal animal : animals) {
            switch (animal) {
                case Cat cat -> sortedAnimals.cats().add(cat);
                case Dog dog -> sortedAnimals.dogs().add(dog);
                case Bird bird -> sortedAnimals.birds().add(bird);
                default -> throw new IllegalStateException("Unexpected value: " + animal);
            }
        }
        System.out.println("Number of cats: " + sortedAnimals.cats().size());
        System.out.println("Number of dogs: " + sortedAnimals.dogs().size());
        System.out.println("Number of birds: " + sortedAnimals.birds().size());
        return sortedAnimals;
    }
}

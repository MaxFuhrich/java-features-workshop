package de.max.education.exercise2;

import de.max.education.exercise2.records.*;

import java.util.ArrayList;
import java.util.List;

public class Exercise implements ExerciseInterface{

    /*
    Refactor this method by making use of record pattern, directly extracting the fields of Dog into variables
     */
    @Override
    public void printDogNamesAndTricks(List<Animal> animals) {
        for (Animal animal : animals) {
            if (animal instanceof Dog) {
                Dog dog = (Dog) animal;
                System.out.println(dog.name() + " - Known tricks: " + dog.knownTricks());
            }
        }
    }

    /*
    Refactor this method by making use of pattern matching for switch
     */
    @Override
    public Zoo animalListsByType(List<Animal> animals) {
        Zoo sortedAnimals = new Zoo(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        for (Animal animal : animals) {
            if (animal instanceof Cat cat) {sortedAnimals.cats().add(cat);}
            else if (animal instanceof Dog dog) {sortedAnimals.dogs().add(dog);}
            else if (animal instanceof Bird bird) {sortedAnimals.birds().add(bird);}
        }
        System.out.println("Number of cats: " + sortedAnimals.cats().size());
        System.out.println("Number of dogs: " + sortedAnimals.dogs().size());
        System.out.println("Number of birds: " + sortedAnimals.birds().size());
        return sortedAnimals;
    }
}

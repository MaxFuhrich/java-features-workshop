package de.max.education.exercise2;

import de.max.education.exercise2.records.Animal;
import de.max.education.exercise2.records.Car;
import de.max.education.exercise2.records.Dog;
import de.max.education.exercise2.records.Engine;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Object> probablyCars = new ArrayList<>();
        Car car = new Car("BMW", 5, new Engine(234, 8, 4));
        probablyCars.add(car);
        System.out.println(car);

        int totalNumberOfSeats = totalNumberOfSeats(probablyCars);
        System.out.println("Total number of seats: " + totalNumberOfSeats);
    }
    //get component from record directly, using records patterns
    public static int totalNumberOfSeats(List<Object> mostlyCars){
        return 0;
    }

    /*
    refactor this method to make use of the record patterns
     */
    public static List<String> getDogNames(List<Animal> animals){
        List<String> dogNames = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal instanceof Dog) {
                Dog dog = (Dog) animal;
                dogNames.add(dog.name() + "Known tricks: " + dog.knownTricks());
            }
        }
        return dogNames;
    }

    /*
    Count how many animals of each type exist within the list. Do so by making use of the switch record pattern
     */
    public static String animalCountByType(List<Animal> animals){
        return "";
    }
}

//TODO set up record types


//refactor old code, that doesn't make use of records patterns (or pattern matching at all)

//combine pattern matching for switch pattern matching with record pattern
/*
zb records für verschiedene dinge (boot, auto, flugzeug) -> alle haben preis
preis ausgeben
 */

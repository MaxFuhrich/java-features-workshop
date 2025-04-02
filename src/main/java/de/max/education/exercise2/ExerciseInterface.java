package de.max.education.exercise2;

import de.max.education.exercise2.records.Animal;

import java.util.List;

public interface ExerciseInterface {
    int totalNumberOfSeats(List<Object> mostlyCars);
    List<String> getDogNames(List<Animal> animals);
    String animalCountByType(List<Animal> animals);
}

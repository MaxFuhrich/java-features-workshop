package de.max.education.exercise2;

import de.max.education.exercise2.records.Animal;
import de.max.education.exercise2.records.Zoo;

import java.util.List;

public interface ExerciseInterface {
    void printDogNamesAndTricks(List<Animal> animals);
    Zoo animalListsByType(List<Animal> animals);
}

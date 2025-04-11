package de.max.education.exercise2.records;

public record Bird(String name) implements Animal{

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}

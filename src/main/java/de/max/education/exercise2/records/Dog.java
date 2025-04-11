package de.max.education.exercise2.records;

public record Dog(String name, String knownTricks) implements Animal{
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}

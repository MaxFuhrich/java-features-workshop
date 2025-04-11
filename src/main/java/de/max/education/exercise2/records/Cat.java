package de.max.education.exercise2.records;

public record Cat(String name) implements Animal{
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}

package de.max.education.exercise4;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.NavigableMap;
import java.util.NavigableSet;

public interface ExerciseInterface {
    void reversedList(List<String> list);
    void reversedSet(NavigableSet<String> navSet);
    void reversedNavigableMap(NavigableMap<String, String> navMap);
    void linkedHashSetLastElement(LinkedHashSet<String> set);
}

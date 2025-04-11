package de.max.education.exercise4;

import java.util.*;

public class Solution implements ExerciseInterface{
    @Override
    public void reversedList(List<String> list) {
        for (String e: list.reversed()){
            System.out.println(e);
        }
    }

    @Override
    public void reversedSet(NavigableSet<String> navSet) {
        for (String e: navSet.reversed()){
            System.out.println(e);
        }
    }

    @Override
    public void reversedNavigableMap(NavigableMap<String, String> navMap) {
        for (Map.Entry<String, String> e: navMap.reversed().entrySet()){
            System.out.println(e);
        }
    }

    @Override
    public void linkedHashSetLastElement(LinkedHashSet<String> set) {
        System.out.println(set.getLast());
    }
}

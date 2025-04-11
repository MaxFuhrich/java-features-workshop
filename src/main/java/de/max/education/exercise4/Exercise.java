package de.max.education.exercise4;

import java.util.*;

public class Exercise implements ExerciseInterface{

    public void reversedList(List<String> list) {
        for (ListIterator<String> it = list.listIterator(list.size()); it.hasPrevious();) {
            String e = it.previous();
            System.out.println(e);
        }

    }

    public void reversedSet(NavigableSet<String> navSet) {
        for (String e : navSet.descendingSet())
            System.out.println(e);
    }

    public void reversedNavigableMap(NavigableMap<String, String> navMap) {
        for (Map.Entry<String, String> e: navMap.descendingMap().entrySet()) {
            System.out.println(e);
        }
    }

    @Override
    public void linkedHashSetLastElement(LinkedHashSet<String> set) {
        String last = "";
        for (String e: set) {
            last = e;
        }
        System.out.println(last);
    }
}

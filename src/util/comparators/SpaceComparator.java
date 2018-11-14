package util.comparators;

import buildings.interfaces.Space;

import java.util.Comparator;

public class SpaceComparator implements Comparator<Space> {
    @Override
    public int compare(Space o1, Space o2) {
        return Integer.compare(o2.getRoomsAmount(), o1.getRoomsAmount());
    }
}

package util.comparators;

import buildings.interfaces.Floor;

import java.util.Comparator;

public class FloorComparator implements Comparator<Floor> {

    @Override
    public int compare(Floor o1, Floor o2) {
        return -Double.compare(o1.totalArea(), o2.totalArea());
    }
}

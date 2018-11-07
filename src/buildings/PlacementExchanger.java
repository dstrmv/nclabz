package buildings;

import buildings.exceptions.InexchangeableFloorsException;
import buildings.exceptions.InexchangeableSpacesException;

public final class PlacementExchanger {

    public static boolean isSpacesExchangeable(Space s1, Space s2) {
        return s1.getArea() == s2.getArea()
                && s1.getRoomsAmount() == s2.getRoomsAmount();
    }

    public static boolean isFloorsExchangeable(Floor f1, Floor f2) {
        return f1.totalRooms() == f2.totalRooms() && f1.totalArea() == f2.totalArea();
    }

    public static void exchangeFloorRooms(Floor floor1, int index1, Floor floor2, int index2)
            throws InexchangeableSpacesException {

        Space s1 = floor1.getSpace(index1);
        Space s2 = floor2.getSpace(index2);

        if (!isSpacesExchangeable(s1, s2)) {
            throw new InexchangeableSpacesException();
        }

        floor1.setSpace(index1, s2);
        floor2.setSpace(index2, s1);
    }

    public static void exchangeBuildingFloors(Building b1, int index1, Building b2, int index2)
            throws InexchangeableFloorsException {

        Floor f1 = b1.getFloor(index1);
        Floor f2 = b2.getFloor(index2);

        if (!isFloorsExchangeable(f1, f2)) {
            throw new InexchangeableFloorsException();
        }

        b1.setFloor(index1, f2);
        b2.setFloor(index2, f1);
    }

}

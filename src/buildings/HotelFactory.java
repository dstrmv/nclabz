package buildings;

import buildings.dwelling.Flat;
import buildings.dwelling.hotel.Hotel;
import buildings.dwelling.hotel.HotelFloor;

public class HotelFactory implements BuildingFactory {
    @Override
    public Space createSpace(double area) {
        return new Flat(area);
    }

    @Override
    public Space createSpace(double area, int roomsAmount) {
        return new Flat(area, roomsAmount);
    }

    @Override
    public Floor createFloor(int spacesAmount) {
        return new HotelFloor(spacesAmount);
    }

    @Override
    public Floor createFloor(Space[] spaces) {
        return new HotelFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorsAmount, int[] spacesAmount) {
        return new Hotel(floorsAmount, spacesAmount);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new Hotel(floors);
    }
}

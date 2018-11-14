package buildings.interfaces;

public interface BuildingFactory {

    Space createSpace(double area);

    Space createSpace(double area, int roomsAmount);

    Floor createFloor(int spacesAmount);

    Floor createFloor(Space[] spaces);

    Building createBuilding(int floorsAmount, int[] spacesAmount);

    Building createBuilding(Floor[] floors);

}

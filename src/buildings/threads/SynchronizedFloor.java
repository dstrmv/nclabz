package buildings.threads;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.util.Iterator;

public class SynchronizedFloor implements Floor {
    private Floor floor;

    public SynchronizedFloor(Floor floor) {
        this.floor = floor;
    }

    @Override
    public synchronized int spacesAmount() {
        return floor.spacesAmount();
    }

    @Override
    public synchronized double totalArea() {
        return floor.totalArea();
    }

    @Override
    public synchronized int totalRooms() {
        return floor.totalRooms();
    }

    @Override
    public synchronized Space[] getSpaces() {
        return floor.getSpaces();
    }

    @Override
    public synchronized Space getSpace(int num) {
        return floor.getSpace(num);
    }

    @Override
    public synchronized void setSpace(int num, Space space) {
        floor.setSpace(num, space);
    }

    @Override
    public synchronized void addSpace(int num, Space space) {
        floor.addSpace(num, space);
    }

    @Override
    public synchronized void deleteSpace(int num) {
        floor.deleteSpace(num);
    }

    @Override
    public synchronized Space getBestSpace() {
        return floor.getBestSpace();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return floor.clone();
    }

    @Override
    public Iterator<Space> iterator() {
        return floor.iterator();
    }
}

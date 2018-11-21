package buildings.dwelling;

import buildings.exceptions.FloorIndexOutOfBoundsException;
import buildings.exceptions.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import util.Quicksort;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Dwelling implements Building, Serializable, Cloneable {

    private Floor[] floors;

    public Dwelling(Floor[] floors) {
        this.floors = floors;
    }

    public Dwelling(int floorsAmount, int[] flatsOnFloorAmount) {

        if (floorsAmount <= 0) {
            throw new IllegalArgumentException("negative or zero floors");
        }

        floors = new Floor[floorsAmount];
        for (int i = 0; i < floors.length; i++) {
            if (flatsOnFloorAmount[i] <= 0) {
                throw new IllegalArgumentException(String.format("negative or zero flats on %d floor", i));
            }
            floors[i] = new DwellingFloor(flatsOnFloorAmount[i]);
        }
    }

    @Override
    public double getCostCoef() {
        return 1000;
    }

    @Override
    public int floorsAmount() {
        return floors.length;
    }

    @Override
    public int spacesAmount() {
        int spaces = 0;
        for (Floor f : floors) {
            spaces += f.spacesAmount();
        }
        return spaces;
    }

    @Override
    public double totalArea() {
        int area = 0;
        for (Floor f : floors) {
            area += f.totalArea();
        }
        return area;
    }

    @Override
    public int totalRooms() {
        int rooms = 0;
        for (Floor f : floors) {
            rooms += f.totalRooms();
        }
        return rooms;
    }

    @Override
    public Floor[] getFloors() {
        return floors;
    }

    @Override
    public Floor getFloor(int num) {
        if (num < 0 || num >= floors.length) {
            throw new FloorIndexOutOfBoundsException();
        }
        return floors[num];
    }

    @Override
    public void setFloor(int num, Floor floor) {
        if (num < 0 || num >= floors.length) {
            throw new FloorIndexOutOfBoundsException();
        }
        floors[num] = floor;
    }

    @Override
    public Space getSpace(int num) {

        if (num >= spacesAmount() || num < 0) {
            throw new SpaceIndexOutOfBoundsException();
        }

        for (int i = 0; i < floors.length; i++) {
            if (num < floors[i].spacesAmount()) {
                return floors[i].getSpace(num);
            }
            num = num - floors[i].spacesAmount();
        }

        return null;
    }

    @Override
    public void setSpace(int num, Space space) {
        if (num >= spacesAmount() || num < 0) {
            throw new SpaceIndexOutOfBoundsException();
        }

        for (int i = 0; i < floors.length; i++) {
            if (num < floors[i].spacesAmount()) {
                floors[i].setSpace(num, space);
                return;
            }
            num = num - floors[i].spacesAmount();
        }
    }

    @Override
    public void addSpace(int num, Space space) {

        if (num > this.spacesAmount() || num < 0) {
            throw new SpaceIndexOutOfBoundsException();
        }

        int totalSpaces = 0;
        for (int i = 0; i < floors.length; i++) {
            totalSpaces += floors[i].spacesAmount();
            if (num < totalSpaces) {
                floors[i].addSpace(floors[i].spacesAmount() - (totalSpaces - num), space);
                return;
            }
        }
        int lastFloor = floors.length - 1;
        floors[lastFloor].addSpace(floors[lastFloor].spacesAmount() - (totalSpaces - num), space);

    }

    @Override
    public void deleteSpace(int num) {

        if (num < 0 || num >= this.spacesAmount()) {
            throw new SpaceIndexOutOfBoundsException();
        }

        int totalSpaces = 0;
        for (int i = 0; i < floors.length; i++) {
            totalSpaces += floors[i].spacesAmount();
            if (num < totalSpaces) {
                floors[i].deleteSpace(floors[i].spacesAmount() - (totalSpaces - num));
                return;
            }
        }
    }

    @Override
    public Space getBestSpace() {
        if (floors.length == 0) {
            throw new FloorIndexOutOfBoundsException("zero floors");
        }
        if (this.spacesAmount() == 0) {
            throw new SpaceIndexOutOfBoundsException("zero flats");
        }

        Space best = floors[0].getBestSpace();
        Space current = best;
        for (Floor f : floors) {
            current = f.getBestSpace();
            if (current.getArea() > best.getArea()) {
                best = current;
            }
        }
        return best;
    }

    @Override
    public Space[] spacesSortedByAreaDescending() {

        if (floors.length == 0) {
            throw new FloorIndexOutOfBoundsException("zero floors");
        }
        if (this.spacesAmount() == 0) {
            throw new SpaceIndexOutOfBoundsException("zero flats");
        }

        Space[] sortedFlats = new Space[spacesAmount()];

        int index = 0;
        int newIndex = 0;
        for (Floor f : floors) {
            newIndex = f.spacesAmount();
            System.arraycopy(f.getSpaces(), 0, sortedFlats, index, newIndex);
            index += newIndex;
        }

        Quicksort.sort(sortedFlats);

        return sortedFlats;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Dwelling dwelling = (Dwelling) super.clone();
        dwelling.floors = this.floors.clone();
        for (int i = 0; i < floors.length; i++) {
            dwelling.floors[i] = (Floor) this.floors[i].clone();
        }
        return dwelling;
    }

    @Override
    public Iterator<Floor> iterator() {
        return new Iterator<Floor>() {

            int index = 0;

            @Override
            public boolean hasNext() {
                return index < floors.length;
            }

            @Override
            public Floor next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return floors[index++];
            }
        };
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Dwelling\n");
        sb.append(floorsAmount());
        for (int i = 0; i < floorsAmount(); i++) {
            sb.append("\n").append(this.getFloor(i).toString());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof Dwelling)) return false;
        Dwelling dwelling = (Dwelling) obj;
        if (dwelling.floorsAmount() != this.floorsAmount()) return false;
        for (int i = 0; i < dwelling.floorsAmount(); i++) {
            if (!dwelling.getFloor(i).equals(this.getFloor(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = this.floorsAmount();
        for (int i = 0; i < this.floorsAmount(); i++) {
            hash ^= getFloor(i).hashCode();
        }
        return hash;
    }
}

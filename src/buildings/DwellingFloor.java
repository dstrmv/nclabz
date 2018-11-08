package buildings;

import buildings.exceptions.SpaceIndexOutOfBoundsException;

import java.io.Serializable;

public class DwellingFloor implements Floor, Serializable, Cloneable {

    private Space[] spaces;

    public DwellingFloor(Space[] spaces) {
        this.spaces = spaces;
    }

    public DwellingFloor(int capacity) {
        this.spaces = new Space[capacity];
        for (int i = 0; i < spaces.length; i++) {
            spaces[i] = new Flat();
        }
    }

    @Override
    public int spacesAmount() {
        return spaces.length;
    }

    @Override
    public double totalArea() {
        double totalArea = 0;
        for (int i = 0; i < spaces.length; i++) {
            totalArea += spaces[i].getArea();
        }
        return totalArea;
    }

    @Override
    public int totalRooms() {
        int totalRooms = 0;
        for (int i = 0; i < spaces.length; i++) {
            totalRooms += spaces[i].getRoomsAmount();
        }
        return totalRooms;
    }

    @Override
    public Space[] getSpaces() {
        return spaces;
    }

    @Override
    public Space getSpace(int num) {
        if (num < 0 || num >= spaces.length) {
            throw new SpaceIndexOutOfBoundsException();
        }
        return spaces[num];
    }

    @Override
    public void setSpace(int num, Space space) {
        if (num < 0 || num >= spaces.length) {
            throw new SpaceIndexOutOfBoundsException();
        }
        spaces[num] = space;
    }

    @Override
    public void addSpace(int num, Space space) {
        if (num > spaces.length || num < 0) {
            throw new SpaceIndexOutOfBoundsException();
        }

        Space[] newSpaces = new Space[spacesAmount() + 1];
        System.arraycopy(spaces, 0, newSpaces, 0, num);
        newSpaces[num] = space;
        System.arraycopy(spaces, num, newSpaces, num + 1, newSpaces.length - num - 1);
        spaces = newSpaces;

    }

    @Override
    public void deleteSpace(int num) {
        if (num >= spacesAmount() || num < 0 || spacesAmount() == 0) {
            throw new SpaceIndexOutOfBoundsException();
        }
        Space[] newSpaces = new Space[spacesAmount() - 1];
        System.arraycopy(spaces, 0, newSpaces, 0, num);
        System.arraycopy(spaces, num + 1, newSpaces, num, spacesAmount() - num - 1);
        spaces = newSpaces;

    }

    @Override
    public Space getBestArea() {
        if (spacesAmount() == 0) {
            return null;
        }
        Space resultSpace = spaces[0];

        for (Space s : spaces) {
            if (s.getArea() > resultSpace.getArea()) {
                resultSpace = s;
            }
        }
        return resultSpace;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("DwellingFloor\n");
        sb.append(this.spacesAmount());
        for (int i = 0; i < spacesAmount(); i++) {
            sb.append("\n").append(spaces[i].toString());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof DwellingFloor)) return false;
        DwellingFloor df = (DwellingFloor) obj;
        if (df.spacesAmount() != this.spacesAmount()) return false;
        for (int i = 0; i < df.spacesAmount(); i++) {
            if (!df.getSpace(i).equals(this.getSpace(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = this.spacesAmount();
        for (int i = 0; i < spacesAmount(); i++) {
            hash ^= getSpace(i).hashCode();
        }
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DwellingFloor df = (DwellingFloor) super.clone();
        df.spaces = this.spaces.clone();
        for (int i = 0; i < df.spacesAmount(); i++) {
            df.spaces[i] = (Space) this.spaces[i].clone();
        }
        return df;
    }
}

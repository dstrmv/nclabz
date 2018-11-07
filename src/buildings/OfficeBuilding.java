package buildings;

import buildings.exceptions.FloorIndexOutOfBoundsException;
import buildings.exceptions.SpaceIndexOutOfBoundsException;

import java.io.Serializable;

public class OfficeBuilding implements Building, Serializable {

    private Node head;
    private int size;

    public OfficeBuilding(int floorsAmount, int[] officesOnFloor) {

        if (floorsAmount <= 0) {
            throw new IllegalArgumentException("zero floors");
        }

        Floor floor;
        for (int i = 0; i < floorsAmount; i++) {
            if (officesOnFloor[i] < 0) {
                throw new IllegalArgumentException("negative offices on floor");
            }
            floor = new OfficeFloor(officesOnFloor[i]);
            this.add(new Node(floor));
        }
    }


    public OfficeBuilding(Floor[] floors) {
        for (int i = 0; i < floors.length; i++) {
            this.add(new Node(floors[i]));
        }
    }

    @Override
    public int floorsAmount() {
        return size;
    }

    @Override
    public int spacesAmount() {
        int spacesAmount = 0;
        for (int i = 0; i < size; i++) {
            spacesAmount += this.getFloor(i).spacesAmount();
        }
        return spacesAmount;
    }

    @Override
    public double totalArea() {
        double totalArea = 0;
        for (int i = 0; i < size; i++) {
            totalArea += this.getFloor(i).totalArea();
        }
        return totalArea;
    }

    @Override
    public int totalRooms() {
        int totalRooms = 0;
        for (int i = 0; i < size; i++) {
            totalRooms += this.getFloor(i).totalRooms();
        }
        return totalRooms;
    }

    @Override
    public Floor[] getFloors() {
        Floor[] floors = new Floor[size];
        for (int i = 0; i < size; i++) {
            floors[i] = this.getFloor(i);
        }
        return floors;
    }

    @Override
    public Floor getFloor(int num) {
        return this.getNode(num).content;
    }

    @Override
    public void setFloor(int num, Floor floor) {
        this.setNode(num, new Node(floor));
    }


    @Override
    public Space getSpace(int num) {

        int spacesAmount = this.spacesAmount();

        if (num >= spacesAmount || num < 0) {
            throw new SpaceIndexOutOfBoundsException();
        }

        int floorsAmount = this.floorsAmount();
        Floor currentFloor;
        int spacesOnCurrentFloor;
        for (int i = 0; i < floorsAmount; i++) {
            currentFloor = this.getFloor(i);
            spacesOnCurrentFloor = currentFloor.spacesAmount();
            if (num < spacesOnCurrentFloor) {
                return currentFloor.getSpace(num);
            }
            num = num - spacesOnCurrentFloor;
        }
        return null;
    }

    @Override
    public void setSpace(int num, Space space) {

        int spacesAmount = this.spacesAmount();

        if (num >= spacesAmount || num < 0) {
            throw new SpaceIndexOutOfBoundsException();
        }

        int floorsAmount = this.floorsAmount();
        int spacesOnCurrentFloor;
        Floor floor;
        for (int i = 0; i < floorsAmount; i++) {
            floor = this.getFloor(i);
            spacesOnCurrentFloor = floor.spacesAmount();
            if (num < spacesOnCurrentFloor) {
                floor.setSpace(num, space);
                return;
            }
            num = num - spacesOnCurrentFloor;
        }
    }

    @Override
    public void addSpace(int num, Space space) {

        int spacesAmount = this.spacesAmount();

        if (num > spacesAmount || num < 0) {
            throw new SpaceIndexOutOfBoundsException();
        }

        int totalSpaces = 0;
        Floor floor;
        int floorsAmount = this.floorsAmount();
        for (int i = 0; i < floorsAmount; i++) {
            floor = this.getFloor(i);
            totalSpaces += floor.spacesAmount();
            if (num < totalSpaces) {
                floor.addSpace(floor.spacesAmount() - (totalSpaces - num), space);
                return;
            }
        }
        int lastFloor = floorsAmount - 1;
        floor = this.getFloor(lastFloor);
        floor.addSpace(floor.spacesAmount() - (totalSpaces - num), space);
    }

    @Override
    public void deleteSpace(int num) {

        int spacesAmount = this.spacesAmount();

        if (num >= spacesAmount || num < 0) {
            throw new SpaceIndexOutOfBoundsException();
        }

        int totalSpaces = 0;
        Floor floor;
        int floorsAmount = this.floorsAmount();
        for (int i = 0; i < floorsAmount; i++) {
            floor = this.getFloor(i);
            totalSpaces += floor.spacesAmount();
            if (num < totalSpaces) {
                floor.deleteSpace(floor.spacesAmount() - (totalSpaces - num));
                return;
            }
        }
    }

    @Override
    public Space getBestArea() {
        if (this.floorsAmount() == 0) {
            throw new FloorIndexOutOfBoundsException("zero floors");
        }
        if (this.spacesAmount() == 0) {
            throw new SpaceIndexOutOfBoundsException("zero offices");
        }
        Space best = getFloor(0).getBestArea();
        Space current = best;
        for (int i = 0; i < floorsAmount(); i++) {
            current = getFloor(i).getBestArea();
            if (current.getArea() > best.getArea()) {
                best = current;
            }
        }
        return best;
    }

    @Override
    public Space[] spacesSortedByAreaDescending() {
        if (this.floorsAmount() == 0) {
            throw new FloorIndexOutOfBoundsException("zero floors");
        }
        if (this.spacesAmount() == 0) {
            throw new SpaceIndexOutOfBoundsException("zero offices");
        }

        Space[] sortedSpaces = new Space[spacesAmount()];
        Space[] spacesOnCurrentFloor;

        int index = 0;
        int newIndex = 0;
        for (int i = 0; i < floorsAmount(); i++) {
            spacesOnCurrentFloor = getFloor(i).getSpaces();
            newIndex = spacesOnCurrentFloor.length;

            System.arraycopy(spacesOnCurrentFloor, 0, sortedSpaces, index, newIndex);
            index += newIndex;
        }

        Quicksort.sort(sortedSpaces);

        return sortedSpaces;
    }

    public void addFloor(int num, Floor floor) {
        this.add(num, new Node(floor));
    }

    public void deleteFloor(int num) {
        this.deleteNode(num);
    }


    // add to the end of list
    private void add(Node node) {
        if (size == 0) {
            head = node;
            head.next = head;
            head.prev = head;
        } else if (size == 1) {
            node.prev = head;
            node.next = head;
            head.next = node;
            head.prev = node;
        } else {
            node.prev = head.prev;
            node.next = head;
            head.prev = node;
            node.prev.next = head.prev;
        }
        size++;
    }


    private void add(int num, Node node) {
        if (num > size || num < 0) {
            throw new FloorIndexOutOfBoundsException("floor number larger than size or negative");
        }
        if (num == size) {
            this.add(node);
            return;
        }
        if (num == 0) {
            node.next = head;
            node.prev = head.prev;
            head.prev = node;
            head = node;
        } else {
            // num - 1 -- amount of iterations to right
            // size - num -- amount of iterations to left
            Node buffer = head;
            if (num - 1 < size - num) {
                buffer = this.getNode(num - 1);
                node.prev = buffer;
                node.next = buffer.next;
                buffer.next.prev = node;
                buffer.next = node;
            } else {
                buffer = this.getNode(size - num);
                node.prev = buffer.prev;
                node.next = buffer;
                buffer.prev.next = node;
                buffer.prev = node;
            }
        }
        size++;
    }

    private Node getNode(int num) {

        if (num < 0 || num >= size) {
            throw new FloorIndexOutOfBoundsException("incorrect floor number");
        }

        Node buffer = head;
        if (num < size - num) {
            for (int i = 0; i < num; i++) {
                buffer = buffer.next;
            }
        } else {
            for (int i = 0; i < size - num; i++) {
                buffer = buffer.prev;
            }
        }
        return buffer;
    }

    private void deleteNode(int num) {
        if (num < 0 || num >= size) {
            throw new FloorIndexOutOfBoundsException("floor number is negative or not less than size");
        }
        if (size == 1) {
            head = null;
        } else if (num == 0) {
            head.prev.next = head.next;
            head.next.prev = head.prev;
            head.prev = null;
            head = head.next;
        } else {
            Node buffer = this.getNode(num);
            buffer.next.prev = buffer.prev;
            buffer.prev.next = buffer.next;
            buffer.next = null;
            buffer.prev = null;
        }
        size--;
    }

    private void setNode(int num, Node node) {

        Node nodeToChange = getNode(num);
        nodeToChange.content = node.content;

    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("OfficeBuilding\n");
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
        if (!(obj instanceof OfficeBuilding)) return false;
        OfficeBuilding building = (OfficeBuilding) obj;
        if (building.size != this.size) return false;

        Node bufferThis = this.head;
        Node bufferObj = building.head;

        for (int i = 0; i < building.size; i++) {
            if (!bufferThis.content.equals(bufferObj.content)) {
                return false;
            }
            bufferThis = bufferThis.next;
            bufferObj = bufferObj.next;
        }
        return true;
    }

    @Override
    public int hashCode() {

        int hash = size;
        Node buffer = head;
        for (int i = 0; i < size; i++) {
            hash ^= buffer.content.hashCode();
            buffer = buffer.next;
        }
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return null;
    }

    private class Node implements Serializable {

        private Floor content;
        private Node prev;
        private Node next;

        Node(Floor content, Node prev, Node next) {
            this.content = content;
            this.prev = prev;
            this.next = next;
        }

        Node() {
            this(null, null, null);
        }

        Node(Floor content) {
            this(content, null, null);
        }
    }
}

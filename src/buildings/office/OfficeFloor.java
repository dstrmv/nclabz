package buildings.office;

import buildings.Floor;
import buildings.Space;
import buildings.exceptions.SpaceIndexOutOfBoundsException;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OfficeFloor implements Floor, Serializable, Cloneable {

    private Node head;
    private int size;

    public OfficeFloor(int officeAmount) {

        if (officeAmount <= 0)
            throw new IllegalArgumentException();

        head = new Node(new Office(), head);
        Node buffer = head;

        for (int i = 1; i < officeAmount; i++) {
            buffer.next = new Node(new Office(), head);
            buffer = buffer.next;
        }
        size = officeAmount;
    }


    public OfficeFloor(Space[] spaces) {

        if (spaces.length == 0) {
            head = new Node();
            size = 0;
            return;
        }

        head = new Node(spaces[0]);
        head.next = head;
        Node buffer = head;

        for (int i = 1; i < spaces.length; i++) {
            buffer.next = new Node(spaces[i], head);
            buffer = buffer.next;
        }
        size = spaces.length;
    }

    @Override
    public int spacesAmount() {
        return size;
    }

    @Override
    public double totalArea() {

        if (size == 0) return 0;
        double totalArea = 0;
        Node buffer = head;
        for (int i = 0; i < size; i++) {
            totalArea += buffer.content.getArea();
            buffer = buffer.next;
        }
        return totalArea;
    }

    @Override
    public int totalRooms() {
        if (size == 0) return 0;
        int totalRooms = 0;
        Node buffer = head;
        for (int i = 0; i < size; i++) {
            totalRooms += buffer.content.getRoomsAmount();
            buffer = buffer.next;
        }
        return totalRooms;
    }

    @Override
    public Space[] getSpaces() {
        Space[] spaces = new Space[size];
        Node buffer = head;
        for (int i = 0; i < size; i++) {
            spaces[i] = buffer.content;
            buffer = buffer.next;
        }
        buffer = null;
        return spaces;
    }

    @Override
    public Space getSpace(int num) {
        return this.getNode(num).content;
    }

    @Override
    public void setSpace(int num, Space space) {
        getNode(num).content = space;
    }

    @Override
    public void addSpace(int num, Space space) {
        addNode(num, new Node(space));
    }

    @Override
    public void deleteSpace(int num) {
        deleteNode(num);
    }

    @Override
    public Space getBestSpace() {
        if (size == 0) {
            throw new SpaceIndexOutOfBoundsException();
        }

        Node buffer = head;
        Space best = buffer.content;

        for (int i = 0; i < size; i++) {

            if (buffer.content.getArea() > best.getArea()) {
                best = buffer.content;
            }
            buffer = buffer.next;
        }
        return best;
    }


    private Node getNode(int num) {
        if (num < 0 || num >= size)
            throw new SpaceIndexOutOfBoundsException();

        Node buffer = head;
        for (int i = 0; i < num; i++) {
            buffer = buffer.next;
        }
        return buffer;
    }


    private void addNode(int num, Node node) {
        if (num < 0 || num > size) {
            throw new SpaceIndexOutOfBoundsException();
        }

        if (num == 0) {
            node.next = head;
            head = node;
        } else {
            Node prev = getNode(num - 1);
            node.next = prev.next;
            prev.next = node;
        }
        size++;
    }


    private void deleteNode(int num) {
        if (num < 0 || num >= size) {
            throw new SpaceIndexOutOfBoundsException();
        }
        if (num == 0) {
            head = head.next;
        } else {
            Node prev = getNode(num - 1);
            prev.next = prev.next.next;
        }
        size--;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Node buffer = head;
        sb.append("OfficeFloor\n");
        sb.append(this.spacesAmount());
        for (int i = 0; i < size; i++) {
            sb.append("\n").append(buffer.content.toString());
            buffer = buffer.next;
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof OfficeFloor)) return false;
        OfficeFloor of = (OfficeFloor) obj;
        if (of.size != this.size) return false;

        Node bufferThis = this.head;
        Node bufferObj = of.head;

        for (int i = 0; i < of.size; i++) {
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
        OfficeFloor of = (OfficeFloor) super.clone();
        //for (int i = 0; i < size; i++) {
        of.head = (Node) this.head.clone();
        //}
        return of;
    }

    @Override
    public Iterator<Space> iterator() {
        return new Iterator<Space>() {
            int index = 0;
            Node current = head;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Space next() {

                if (!hasNext())
                    throw new NoSuchElementException();

                Space next;
                next = current.content;
                current = current.next;
                index++;
                return next;
            }
        };
    }

    private class Node implements Serializable, Cloneable {

        private Space content;
        private Node next;

        Node() {
            this.content = null;
            this.next = null;
        }

        Node(Space content, Node next) {
            this.content = content;
            this.next = next;
        }

        Node(Space content) {
            this.content = content;
            this.next = null;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {

            Node node = (Node) super.clone();
            node.content = (Space) this.content.clone();

            if (this.next == head) {
                return node;
            }
            node.next = (Node) this.next.clone();
            return node;
        }
    }
}

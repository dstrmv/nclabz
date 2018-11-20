package util;

import buildings.factory.DwellingFactory;
import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.interfaces.Building;
import buildings.interfaces.BuildingFactory;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.office.Office;
import buildings.office.OfficeBuilding;
import buildings.office.OfficeFloor;

import java.io.*;
import java.util.*;

public class Buildings {

    static private BuildingFactory buildingFactory = new DwellingFactory();

    public static void setBuildingFactory(BuildingFactory buildingFactory) {
        Buildings.buildingFactory = buildingFactory;
    }

    public static void outputBuilding(Building building, OutputStream out) {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        try {

            int floorsAmount = building.floorsAmount();
            dataOutputStream.writeInt(floorsAmount);
            Floor currentFloor;
            Space currentSpace;
            int spacesOnFloor;

            for (int i = 0; i < floorsAmount; i++) {
                currentFloor = building.getFloor(i);

                spacesOnFloor = currentFloor.spacesAmount();
                dataOutputStream.writeInt(spacesOnFloor);

                for (int j = 0; j < spacesOnFloor; j++) {
                    currentSpace = currentFloor.getSpace(j);

                    dataOutputStream.writeInt(currentSpace.getRoomsAmount());
                    dataOutputStream.writeDouble(currentSpace.getArea());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Building inputBuilding(InputStream in) {
        Building building = null;
        try {
            DataInputStream dataInputStream = new DataInputStream(in);

            int floorsAmount = dataInputStream.readInt();
            int spacesOnFloor;

            Floor[] floors = new Floor[floorsAmount];
            Space[] spaces;

            int rooms;
            double area;

            for (int i = 0; i < floorsAmount; i++) {

                spacesOnFloor = dataInputStream.readInt();
                spaces = new Space[spacesOnFloor];

                for (int j = 0; j < spacesOnFloor; j++) {
                    rooms = dataInputStream.readInt();
                    area = dataInputStream.readDouble();

                    spaces[j] = buildingFactory.createSpace(area, rooms);
                }

                floors[i] = buildingFactory.createFloor(spaces);
            }

            building = buildingFactory.createBuilding(floors);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return building;
    }

    public static void writeBuilding(Building building, Writer out) {
        PrintWriter writer = new PrintWriter(out);

        writer.println(building.floorsAmount());

        Floor floor;
        for (int i = 0; i < building.floorsAmount(); i++) {
            floor = building.getFloor(i);

            writer.println(floor.spacesAmount());
            Space space;
            for (int j = 0; j < floor.spacesAmount(); j++) {
                space = floor.getSpace(j);

                writer.printf(Locale.GERMANY, "%f%n", space.getArea());
                writer.println(space.getRoomsAmount());
            }
        }

    }

    public static Building readBuilding(Reader in) {
        Building building = null;
        Floor[] floors;
        Space[] spaces;

        StreamTokenizer tokenizer = new StreamTokenizer(in);

        try {
            tokenizer.nextToken();
            int floorsAmount = (int) tokenizer.nval;

            floors = new Floor[floorsAmount];
            for (int i = 0; i < floorsAmount; i++) {
                tokenizer.nextToken();
                int spacesOnFloor = (int) tokenizer.nval;
                spaces = new Space[spacesOnFloor];
                for (int j = 0; j < spacesOnFloor; j++) {
                    tokenizer.nextToken();
                    double area = tokenizer.nval;
                    tokenizer.nextToken();
                    int rooms = (int) tokenizer.nval;

                    spaces[j] = buildingFactory.createSpace(area, rooms);
                }

                floors[i] = buildingFactory.createFloor(spaces);

            }
            building = buildingFactory.createBuilding(floors);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return building;
    }

    public static void writeBuildingFormat(Building building, Writer out) {
        Formatter formatter = new Formatter(new PrintWriter(out));

        formatter.format("%d%n", building.floorsAmount());

        Floor floor;
        for (int i = 0; i < building.floorsAmount(); i++) {
            floor = building.getFloor(i);

            formatter.format("%d%n", floor.spacesAmount());
            Space space;
            for (int j = 0; j < floor.spacesAmount(); j++) {
                space = floor.getSpace(j);

                formatter.format("%f%n%d%n", space.getArea(), space.getRoomsAmount());

            }
        }

    }

    public static Building readBuilding(Scanner scanner) {
        Building building = null;
        Floor[] floors;
        Space[] spaces;

        int floorsAmount = scanner.nextInt();

        floors = new Floor[floorsAmount];
        for (int i = 0; i < floorsAmount; i++) {
            scanner.nextLine();
            int spacesOnFloor = scanner.nextInt();
            spaces = new Space[spacesOnFloor];

            for (int j = 0; j < spacesOnFloor; j++) {
                scanner.nextLine();
                double area = scanner.nextDouble();
                scanner.nextLine();
                int rooms = scanner.nextInt();

                spaces[j] = buildingFactory.createSpace(area, rooms);
            }

            floors[i] = buildingFactory.createFloor(spaces);

        }
        building = buildingFactory.createBuilding(floors);
        return building;
    }


    public static void serializeBuilding(Building building, OutputStream out) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(building);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Building deserializeBuilding(InputStream in) {

        Building building = null;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(in);
            building = (Building) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return building;
    }

    public static Space[] sortSpaces(Space[] sp) {
        Space[] result = new Space[sp.length];
        System.arraycopy(sp, 0, result, 0, sp.length);
        Arrays.sort(result);
        return result;
    }

    public static Floor[] sortFloors(Floor[] fl) {
        Floor[] result = new Floor[fl.length];
        System.arraycopy(fl, 0, result, 0, fl.length);
        Arrays.sort(result);
        return result;
    }

    public static <E extends Comparable<E>> E[] sort(E[] e) {
        E[] result = Arrays.copyOf(e, e.length);
        Arrays.sort(result);
        return result;
    }

    public static Space[] sortSpaces(Space[] sp, Comparator<Space> comp) {
        Space[] result = Arrays.copyOf(sp, sp.length);
        Arrays.sort(result, comp);
        return result;
    }

    public static Floor[] sortFloors(Floor[] fl, Comparator<Floor> comp) {
        Floor[] result = Arrays.copyOf(fl, fl.length);
        Arrays.sort(result, comp);
        return result;
    }

    public static <E> E[] sort(E[] e, Comparator<E> comp) {
        E[] result = Arrays.copyOf(e, e.length);
        Arrays.sort(result, comp);
        return result;
    }

    public static Space newSpace(Double area) {
        return buildingFactory.createSpace(area);
    }

    public static Space newSpace(Double area, int rooms) {
        return buildingFactory.createSpace(area, rooms);
    }

    public static Floor newFloor(int spacesAmount) {
        return buildingFactory.createFloor(spacesAmount);
    }

    public static Floor newFloor(Space[] spaces) {
        return buildingFactory.createFloor(spaces);
    }

    public static Building newBuilding(Floor[] floors) {
        return buildingFactory.createBuilding(floors);
    }

    public static Building newBuilding(int floorsAmount, int[] spacesOnFloor) {
        return buildingFactory.createBuilding(floorsAmount, spacesOnFloor);
    }
}

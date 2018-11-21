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
import buildings.threads.SynchronizedFloor;
import com.sun.xml.internal.bind.v2.TODO;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

                writer.println(space.getArea());
                writer.println(space.getRoomsAmount());
            }
        }

    }

    public static Building readBuilding(Reader in) {
        Building building = null;
        Floor[] floors;
        Space[] spaces;

        StreamTokenizer tokenizer = new StreamTokenizer(in);
        //tokenizer.parseNumbers();
        //tokenizer.ordinaryChar('\'');
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

    public static Space createSpace(double area) {
        return buildingFactory.createSpace(area);
    }

    public static Space createSpace(double area, int rooms) {
        return buildingFactory.createSpace(area, rooms);
    }

    public static Floor createFloor(int spacesAmount) {
        return buildingFactory.createFloor(spacesAmount);
    }

    public static Floor createFloor(Space[] spaces) {
        return buildingFactory.createFloor(spaces);
    }

    public static Building createBuilding(Floor[] floors) {
        return buildingFactory.createBuilding(floors);
    }

    public static Building createBuilding(int floorsAmount, int[] spacesOnFloor) {
        return buildingFactory.createBuilding(floorsAmount, spacesOnFloor);
    }

    public static Space createSpace(double area, Class<? extends Space> spaceClass) {

        try {
            Constructor<?> con = spaceClass.getConstructor(double.class);
            return (Space) con.newInstance(area);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException();

    }

    public static Space createSpace(double area, int rooms, Class<? extends Space> spaceClass) {

        try {
            Constructor<?> con = spaceClass.getConstructor(double.class, int.class);
            return (Space) con.newInstance(area, rooms);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException();

    }

    public static Floor createFloor(int spacesAmount, Class<? extends Floor> floorClass) {

        try {
            Constructor<?> con = floorClass.getConstructor(int.class);
            return (Floor) con.newInstance(spacesAmount);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static Floor createFloor(Space[] spaces, Class<? extends Floor> floorClass) {

        try {
            Constructor<?> con = floorClass.getConstructor(Space[].class);
            return (Floor) con.newInstance((Object) spaces);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static Building createBuilding(Floor[] floors, Class<? extends Building> buildingClass) {
        try {
            Constructor<? extends Building> con = buildingClass.getConstructor(Floor[].class);
            return (Building) con.newInstance((Object) floors);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static Building createBuilding(int floorsAmount, int[] spacesOnFloor, Class<? extends Building> buildingClass) {
        try {
            Constructor<? extends Building> con = buildingClass.getConstructor(int.class, int[].class);
            return (Building) con.newInstance(floorsAmount, (Object) spacesOnFloor);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static Building readBuilding(Scanner scanner,
                                        Class<? extends Space> spaceType,
                                        Class<? extends Floor> floorType,
                                        Class<? extends Building> buildingType) {
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

                spaces[j] = Buildings.createSpace(area, rooms, spaceType);
            }

            floors[i] = Buildings.createFloor(spaces, floorType);

        }
        building = Buildings.createBuilding(floors, buildingType);
        return building;
    }

    public static Building readBuilding(Reader in,
                                        Class<? extends Space> spaceType,
                                        Class<? extends Floor> floorType,
                                        Class<? extends Building> buildingType) {
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

                    spaces[j] = Buildings.createSpace(area, rooms, spaceType);
                }

                floors[i] = Buildings.createFloor(spaces, floorType);

            }
            building = Buildings.createBuilding(floors, buildingType);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return building;
    }


    public static Building inputBuilding(InputStream in,
                                         Class<? extends Space> spaceType,
                                         Class<? extends Floor> floorType,
                                         Class<? extends Building> buildingType) {
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

                    spaces[j] = Buildings.createSpace(area, rooms, spaceType);
                }

                floors[i] = Buildings.createFloor(spaces, floorType);
            }

            building = Buildings.createBuilding(floors, buildingType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return building;
    }


    public static void writeBuildingTypes(Building building, Writer out) {

        PrintWriter writer = new PrintWriter(out);
        writer.println(building.getClass().getName());
        for (Floor f : building.getFloors()) {
            writer.println(f.getClass().getName());
            for (Space s : f.getSpaces()) {
                writer.println(s.getClass().getName());
            }
        }
    }

    public Floor synchronizedFloor(Floor floor) {
        return new SynchronizedFloor(floor);
    }
    //TODO
//    public static Building readBuildingwithTypes(Scanner info, Scanner types) {
//        Building building = null;
//        Floor[] floors;
//        Space[] spaces;
//
//        int floorsAmount = info.nextInt();
//
//        floors = new Floor[floorsAmount];
//        for (int i = 0; i < floorsAmount; i++) {
//            info.nextLine();
//            int spacesOnFloor = info.nextInt();
//            spaces = new Space[spacesOnFloor];
//
//            for (int j = 0; j < spacesOnFloor; j++) {
//                info.nextLine();
//                double area = info.nextDouble();
//                info.nextLine();
//                int rooms = info.nextInt();
//
//                spaces[j] = buildingFactory.createSpace(area, rooms);
//            }
//
//            floors[i] = buildingFactory.createFloor(spaces);
//
//        }
//        building = buildingFactory.createBuilding(floors);
//        return building;
//    }


}




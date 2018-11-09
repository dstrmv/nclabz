package buildings;

import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.office.Office;
import buildings.office.OfficeBuilding;
import buildings.office.OfficeFloor;

import java.io.*;
import java.util.Formatter;
import java.util.Locale;
import java.util.Scanner;

public class Buildings {

    static private BuildingFactory buildingFactory = new DwellingFactory();

    public static void setBuildingFactory(BuildingFactory buildingFactory) {
        Buildings.buildingFactory = buildingFactory;
    }

    public static void outputBuilding(Building building, OutputStream out) {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        try {
            if (building instanceof Dwelling) {
                dataOutputStream.writeChar('D');
            } else if (building instanceof OfficeBuilding) {
                dataOutputStream.writeChar('O');
            }

            int floorsAmount = building.floorsAmount();
            dataOutputStream.writeInt(floorsAmount);
            Floor currentFloor;
            Space currentSpace;
            int spacesOnFloor;

            for (int i = 0; i < floorsAmount; i++) {
                currentFloor = building.getFloor(i);

                if (currentFloor instanceof OfficeFloor) {
                    dataOutputStream.writeChar('O');
                } else if (currentFloor instanceof DwellingFloor) {
                    dataOutputStream.writeChar('D');
                }

                spacesOnFloor = currentFloor.spacesAmount();
                dataOutputStream.writeInt(spacesOnFloor);

                for (int j = 0; j < spacesOnFloor; j++) {
                    currentSpace = currentFloor.getSpace(j);

                    if (currentSpace instanceof Flat) {
                        dataOutputStream.writeChar('F');
                    } else if (currentSpace instanceof Office) {
                        dataOutputStream.writeChar('O');
                    }

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

            char buildingType = dataInputStream.readChar();
            int floorsAmount = dataInputStream.readInt();
            int spacesOnFloor;

            Floor[] floors = new Floor[floorsAmount];
            Space[] spaces;

            char floorType;
            char spaceType;
            Floor floor;
            Space space;
            int rooms;
            double area;

            for (int i = 0; i < floorsAmount; i++) {
                floorType = dataInputStream.readChar();
                spacesOnFloor = dataInputStream.readInt();
                spaces = new Space[spacesOnFloor];

                for (int j = 0; j < spacesOnFloor; j++) {
                    spaceType = dataInputStream.readChar();
                    rooms = dataInputStream.readInt();
                    area = dataInputStream.readDouble();

                    if (spaceType == 'F') {
                        space = new Flat(area, rooms);
                    } else {
                        space = new Office(area, rooms);    // spaceType == 'O'
                    }
                    spaces[j] = space;
                }

                if (floorType == 'D') {
                    floor = new DwellingFloor(spaces);
                } else {
                    floor = new OfficeFloor(spaces);    // floorType == 'O'
                }

                floors[i] = floor;
            }

            if (buildingType == 'D') {
                building = new Dwelling(floors);
            } else {
                building = new OfficeBuilding(floors);  // buildingType == 'O'
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return building;
    }

    public static void writeBuilding(Building building, Writer out) {
        PrintWriter writer = new PrintWriter(out);

        if (building instanceof Dwelling) {
            writer.println("Dwelling");
        } else {
            writer.println("OfficeBuilding");
        }

        writer.println(building.floorsAmount());

        Floor floor;
        for (int i = 0; i < building.floorsAmount(); i++) {
            floor = building.getFloor(i);

            if (floor instanceof DwellingFloor) {
                writer.println("DwellingFloor");
            } else {
                writer.println("OfficeFloor");
            }

            writer.println(floor.spacesAmount());
            Space space;
            for (int j = 0; j < floor.spacesAmount(); j++) {
                space = floor.getSpace(j);

                if (space instanceof Flat) {
                    writer.println("Flat");
                } else {
                    writer.println("Office");
                }

                writer.printf(Locale.GERMANY, "%f%n", space.getArea());
                writer.println(space.getRoomsAmount());
            }
        }

    }

    public static Building readBuilding(Reader in) {
        Building building = null;
        Floor[] floors;
        Floor floor;
        Space[] spaces;
        Space space;

        StreamTokenizer tokenizer = new StreamTokenizer(in);

        try {
            tokenizer.nextToken();
            String buildingType = tokenizer.sval;
            tokenizer.nextToken();
            int floorsAmount = (int) tokenizer.nval;

            floors = new Floor[floorsAmount];
            for (int i = 0; i < floorsAmount; i++) {
                tokenizer.nextToken();
                String floorType = tokenizer.sval;
                tokenizer.nextToken();
                int spacesOnFloor = (int) tokenizer.nval;
                spaces = new Space[spacesOnFloor];
                for (int j = 0; j < spacesOnFloor; j++) {
                    tokenizer.nextToken();
                    String spaceType = tokenizer.sval;
                    tokenizer.nextToken();
                    double area = tokenizer.nval;
                    tokenizer.nextToken();
                    int rooms = (int) tokenizer.nval;

                    if (spaceType.equals("Flat")) {
                        space = new Flat(area, rooms);
                    } else {
                        space = new Office(area, rooms);
                    }
                    spaces[j] = space;
                }

                if (floorType.equals("DwellingFloor")) {
                    floor = new DwellingFloor(spaces);
                } else {
                    floor = new OfficeFloor(spaces);
                }

                floors[i] = floor;

            }

            if (buildingType.equals("Dwelling")) {
                building = new Dwelling(floors);
            } else {
                building = new OfficeBuilding(floors);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return building;
    }

    public static void writeBuildingFormat(Building building, Writer out) {
        Formatter formatter = new Formatter(new PrintWriter(out));

        if (building instanceof Dwelling) {
            formatter.format("Dwelling%n");

        } else {
            formatter.format("OfficeBuilding%n");
        }

        formatter.format("%d%n", building.floorsAmount());

        Floor floor;
        for (int i = 0; i < building.floorsAmount(); i++) {
            floor = building.getFloor(i);

            if (floor instanceof DwellingFloor) {
                formatter.format("DwellingFloor%n");
            } else {
                formatter.format("OfficeFloor%n");
            }

            formatter.format("%d%n", floor.spacesAmount());
            Space space;
            for (int j = 0; j < floor.spacesAmount(); j++) {
                space = floor.getSpace(j);

                if (space instanceof Flat) {
                    formatter.format("Flat%n");
                } else {
                    formatter.format("Office%n");
                }

                formatter.format("%f%n%d%n", space.getArea(), space.getRoomsAmount());

            }
        }

    }

    public static Building readBuilding(Scanner scanner) {
        Building building = null;
        Floor[] floors;
        Floor floor;
        Space[] spaces;
        Space space;

        String buildingType = scanner.nextLine();
        int floorsAmount = scanner.nextInt();


        floors = new Floor[floorsAmount];
        for (int i = 0; i < floorsAmount; i++) {
            scanner.nextLine();
            String floorType = scanner.nextLine();
            int spacesOnFloor = scanner.nextInt();
            spaces = new Space[spacesOnFloor];

            for (int j = 0; j < spacesOnFloor; j++) {
                scanner.nextLine();
                String spaceType = scanner.nextLine();
                double area = scanner.nextDouble();
                scanner.nextLine();
                int rooms = scanner.nextInt();

                if (spaceType.equals("Flat")) {
                    space = new Flat(area, rooms);
                } else {
                    space = new Office(area, rooms);
                }
                spaces[j] = space;
            }

            if (floorType.equals("DwellingFloor")) {
                floor = new DwellingFloor(spaces);
            } else {
                floor = new OfficeFloor(spaces);
            }

            floors[i] = floor;

        }

        if (buildingType.equals("Dwelling")) {
            building = new Dwelling(floors);
        } else {
            building = new OfficeBuilding(floors);
        }

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

}


































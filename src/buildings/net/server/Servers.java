package buildings.net.server;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import util.Buildings;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Servers {
    public static Building[] readBuildingsWithTypes(Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<Building> buildings = new ArrayList<>();
        Building b;
        String input = null;

        try {
            while ((input = bufferedReader.readLine()) != null) {
                if (input.equals("PEPEGA")) break;
                String buildingType = input;
                Buildings.setBuildingFactory(Buildings.getFactoryFromBuildingClassName(buildingType));
                b = readBuilding(bufferedReader);
                buildings.add(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buildings.toArray(new Building[0]);
    }

    public static void writeCosts(Building[] buildings, Writer out) {
        PrintWriter writer = new PrintWriter(out);
        double cost;
        for (Building b : buildings) {
            try {
                cost = calculateCost(b);
                writer.println(cost);
            } catch (BuildingUnderArrestException e) {
                writer.println("arrested");
            }
        }
        writer.println("PEPEGA");
        writer.flush();
    }

    public static double calculateCost(Building building) throws BuildingUnderArrestException {
        if (isArrested(building)) throw new BuildingUnderArrestException();
        return building.totalArea() * building.getCostCoef();

    }

    protected static boolean isArrested(Building building) {
        Random r = new Random();
        return r.nextInt(10) == 0;
    }

    protected static Building readBuilding(BufferedReader reader) {
        Building building = null;
        Floor[] floors;
        Space[] spaces;

        try {

            int floorsAmount = Integer.parseInt(reader.readLine());

            floors = new Floor[floorsAmount];
            for (int i = 0; i < floorsAmount; i++) {

                int spacesOnFloor = Integer.parseInt(reader.readLine());
                spaces = new Space[spacesOnFloor];
                for (int j = 0; j < spacesOnFloor; j++) {

                    double area = Double.parseDouble(reader.readLine());
                    int rooms = Integer.parseInt(reader.readLine());

                    spaces[j] = Buildings.createSpace(area, rooms);
                }

                floors[i] = Buildings.createFloor(spaces);

            }
            building = Buildings.createBuilding(floors);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return building;
    }
}

package buildings.net.server.sequental;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import util.Buildings;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class BinaryServer {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        try {
            ServerSocket serverSocket = new ServerSocket(1099);
            Socket clientSocket = null;
            BufferedReader reader = null;
            PrintWriter writer = null;
            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                writer = new PrintWriter(clientSocket.getOutputStream());
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                Building[] buildings = readBuildingsWithTypes(reader);
                System.out.println("building are readed");
                writeCosts(buildings, writer);
                System.out.println("costs are wrote");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Building[] readBuildingsWithTypes(Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<Building> buildings = new ArrayList<>();
        Building b;
        String input = null;

        try {
            while ((input = bufferedReader.readLine()) != null || !bufferedReader.ready()) {
                System.out.println(input);
                if (input.equals(String.format("%n"))) break;
                System.out.println("read building");
                String buildingType = input;
                Buildings.setBuildingFactory(Buildings.getFactoryFromBuildingClassName(buildingType));
                b = readBuilding(bufferedReader);
                buildings.add(b);
                System.out.println("here");
            }
        } catch (IOException e) {
            System.out.println("exc");
        }
        System.out.println("return");
        return buildings.toArray(new Building[0]);
    }

    private static void writeCosts(Building[] buildings, Writer out) {
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
        writer.flush();
    }

    private static double calculateCost(Building building) throws BuildingUnderArrestException {
        if (isArrested(building)) throw new BuildingUnderArrestException();
        return building.totalArea() * building.getCostCoef();

    }

    private static boolean isArrested(Building building) {
        Random r = new Random();
        return r.nextInt(10) == 0;
    }

    private static Building readBuilding(BufferedReader reader) {
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

package buildings.net.server.sequental;

import buildings.interfaces.Building;
import util.Buildings;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class BinaryServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1099);
            Socket clientSocket = null;
            BufferedReader reader = null;
            PrintWriter writer = null;
            while (true) {
                clientSocket = serverSocket.accept();
                writer = new PrintWriter(clientSocket.getOutputStream());
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                Building[] buildings = readBuildingsWithTypes(reader);
                writeCosts(buildings, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Building[] readBuildingsWithTypes(Reader reader) {
        Scanner sc = new Scanner(reader);
        List<Building> buildings = new ArrayList<>();
        Building b;
        String buildingType = sc.nextLine();
        while (!buildingType.isEmpty()) {
            Buildings.setBuildingFactory(Buildings.getFactoryFromBuildingClassName(buildingType));
            b = Buildings.readBuilding(sc);
            buildings.add(b);
            buildingType = sc.nextLine();
        }
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
    }

    private static double calculateCost(Building building) throws BuildingUnderArrestException {
        if (isArrested(building)) throw new BuildingUnderArrestException();
        return building.totalArea() * building.getCostCoef();

    }

    private static boolean isArrested(Building building) {
        Random r = new Random();
        return r.nextInt(10) == 0;
    }
}

package buildings.net.server.sequental;

import buildings.interfaces.Building;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

public class BinaryServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1099);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private double calculateCost(Building building) throws BuildingUnderArrestException {
        if (isArrested(building)) throw new BuildingUnderArrestException();
        return building.totalArea() * building.getCostCoef();

    }

    private boolean isArrested(Building building) {
        Random r = new Random();
        return r.nextInt(10) == 0;
    }

}

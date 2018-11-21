package buildings.net.server.sequental;

import buildings.interfaces.Building;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

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



            }
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

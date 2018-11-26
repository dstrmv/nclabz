package buildings.net.server.sequental;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.net.server.Servers;
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
                Building[] buildings = Servers.readBuildingsWithTypes(reader);
                System.out.println("building are readed");
                Servers.writeCosts(buildings, writer);
                System.out.println("costs are wrote");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

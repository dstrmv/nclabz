package buildings.net.server.parallel;

import buildings.interfaces.Building;
import buildings.net.server.Servers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BinaryServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1099);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                System.out.println("ready");
                Socket client = serverSocket.accept();
                System.out.println("client connected");
                Thread thread = new Thread(() -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                         PrintWriter writer = new PrintWriter(client.getOutputStream())) {
                        Building[] buildings = Servers.readBuildingsWithTypes(reader);
                        //System.out.println("building are readed");
                        Servers.writeCosts(buildings, writer);
                        //System.out.println("costs are wrote");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



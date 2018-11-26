package buildings.net.client;

import buildings.net.server.Servers;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SerialClient {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("localhost", 1099);
             ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream());
             ObjectInputStream serReader = new ObjectInputStream(new FileInputStream("buildings.ser"))
        ) {

            try {
                writer.writeObject(serReader.readObject());

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

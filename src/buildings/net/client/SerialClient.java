package buildings.net.client;

import buildings.net.server.BuildingUnderArrestException;
import buildings.net.server.Servers;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SerialClient {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("localhost", 1099);

             ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream());
             ObjectInputStream serReader = new ObjectInputStream(new FileInputStream("buildings.ser"));
             PrintWriter costsWriter = new PrintWriter(new FileWriter("costs.txt"));
        ) {

            try {
                System.out.println("started");
                writer.writeObject(serReader.readObject());
                System.out.println("object wrote");
                writer.flush();
                Object[] readedObject = (Object[]) reader.readObject();
                for (Object o : readedObject) {
                    if (o instanceof BuildingUnderArrestException) {
                        costsWriter.println("arrested");
                    } else {
                        costsWriter.println(o);
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

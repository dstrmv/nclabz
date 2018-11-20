package buildings.threads;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class Cleaner extends Thread {

    private Floor floor;

    public Cleaner(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        int i = 0;
        for (Space s : floor) {
            System.out.println(String.format(
                    "Cleaning space number %d with total area %f square meters",
                    i++,
                    s.getArea()
            ));
            if (isInterrupted()) {
                System.out.println("Cleaner is interrupted.");
                return;
            }
        }
        System.out.println("Cleaner finished work.");
    }
}


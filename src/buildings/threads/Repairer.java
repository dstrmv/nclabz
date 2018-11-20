package buildings.threads;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class Repairer extends Thread {

    private Floor floor;

    public Repairer(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        int i = 0;
        for (Space s : floor) {
            System.out.println(String.format(
                    "Repairing space number %d with total area %f square meters",
                    i++,
                    s.getArea()
            ));
            if (isInterrupted()) {
                System.out.println("Repairer is interrupted.");
                return;
            }
        }
        System.out.println("Repairer finished work.");
    }
}

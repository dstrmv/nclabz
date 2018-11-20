package buildings.threads;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class SequentalRepairer implements Runnable {

    private Floor floor;
    private Semaphore semaphore;

    public SequentalRepairer(Floor floor, Semaphore semaphore) {
        this.floor = floor;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        int i = 0;
        for (Space s: floor) {
            semaphore.acquire(this);
            System.out.println(String.format(
                    "Repairing space number %d with total area %f square meters",
                    i++,
                    s.getArea()
            ));
            semaphore.release(this);
            if (Thread.interrupted()) {
                System.out.println("Repairer is interrupted.");
                return;
            }
        }
    }
}

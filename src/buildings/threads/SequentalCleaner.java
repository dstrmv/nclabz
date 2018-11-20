package buildings.threads;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class SequentalCleaner implements Runnable {

    private Floor floor;
    private Semaphore semaphore;

    public SequentalCleaner(Floor floor, Semaphore semaphore) {
        this.floor = floor;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        int i = 0;
        for (Space s: floor) {
            semaphore.acquire();
            System.out.println(String.format(
                    "Cleaning space number %d with total area %f square meters",
                    i++,
                    s.getArea()
            ));
            semaphore.release();
            if (Thread.interrupted()) {
                System.out.println("Cleaner is interrupted.");
                return;
            }
        }
    }
}

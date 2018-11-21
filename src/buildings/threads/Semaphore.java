package buildings.threads;

import buildings.interfaces.Floor;

public class Semaphore {

    private boolean free;
    private String acquiredBy;

    public Semaphore() {
        free = true;
        acquiredBy = SequentalCleaner.class.getName();
    }

    public void acquire(Runnable thread) {
        synchronized (this) {
            if (!free) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (acquiredBy.equals(thread.getClass().getName())) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            free = false;
        }
    }

    public void release(Runnable thread) {
        synchronized (this) {
            free = true;
            acquiredBy = thread.getClass().getName();
            this.notifyAll();
        }
    }
}

package buildings.threads;

import buildings.interfaces.Floor;

public class Semaphore {

    private boolean free;
    private String acquiredBy;
    private Object lock;

    public Semaphore() {
        free = true;
        acquiredBy = SequentalCleaner.class.getName();
        lock = new Object();
    }

    public void acquire(Runnable thread) {
        synchronized (lock) {
            if (!free) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (acquiredBy.equals(thread.getClass().getName())) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            free = false;
        }
    }

    public void release(Runnable thread) {
        synchronized (lock) {
            free = true;
            acquiredBy = thread.getClass().getName();
            lock.notifyAll();
        }
    }
}

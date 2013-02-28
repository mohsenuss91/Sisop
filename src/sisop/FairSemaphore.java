package sisop;

import sisop.logging.Log;

import java.util.Vector;
import java.util.List;
import java.util.ArrayList;
import java.lang.Thread;

/**
 * This class implement a semaphore with a strictly FIFO policy using only synchronization method 
 * of Java 4.0 
 *
 * @author Samuele Dal Porto
 * @author Davide Pellegrino
 *
 */

public class FairSemaphore{
    int value;
    int inQueue;
    Thread toWake;
    List<Thread> queue = new ArrayList<Thread>();
    
    /**
     * Create a binary FairSemaphore
     */
    public FairSemaphore() {
        this.value = 1;
        this.inQueue = 0;
    }
    
    /**
     * Create a resource FairSemaphore
     *
     * @param n Value of the semaphore 
     */
    public FairSemaphore(int n) {
        this.value = n;
        this.inQueue = 0;
    }

    /**
     * Check if the semaphore queue is empty
     *
     * @return true if there is not a thread waiting on the semaphore, false otherwise
     */
    public boolean isEmpty() {
        return (inQueue == 0)?true:false;
    }

    /**
     * Check if the semaphore is free and takes control, otherwise insert the thread in the queue and suspend it on the semaphore 
     */

    public synchronized void P() {
        try {
            Thread t = Thread.currentThread(); 
            if (this.value == 0) {
                this.inQueue++;
                this.queue.add(t);
                while(t != this.toWake) wait();
            }
            this.value--;
            this.toWake = null;
        }
        catch (InterruptedException e) {
            Log.severe("Wait Error " + e);
        }
    }

    /**
     * Release the control of the semaphore; if there is a thread suspended on the semaphore, wake up the first
     */

    public synchronized void V(){
        this.value++;
        if (this.inQueue != 0) {
            this.toWake = this.queue.remove(0);
            this.inQueue--;
            notifyAll();
        }
    }

}

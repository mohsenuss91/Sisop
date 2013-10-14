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
    List<Thread> toWakeQueue = new ArrayList<Thread>();
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
    public synchronized boolean isEmpty() {
        return (inQueue == 0)?true:false;
    }

    /**
     * Check if the semaphore is free and takes control, otherwise insert the thread in the queue and suspend it on the semaphore 
     */

    public synchronized void P() {
        try {
            boolean isWake = false;
            Thread t = Thread.currentThread(); 
            if (this.value == 0) {
                this.inQueue++;
                this.queue.add(t);
                while(!isWake){
                    for (int index = 0; index < this.toWakeQueue.size(); index++) {
                        if (t == this.toWakeQueue.get(index) ) {
                            isWake = true;
                            this.toWakeQueue.remove(index);
                        }
                    }
                    if (!isWake) {
                        // Log.info("FairSemaphore: " + t.getName() + " Wait");
                        wait();   
                    }
                }
                // Log.info("FairSemaphore: " + t.getName() + " Wakeup");
            }
            this.value--;
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
            Thread t = this.queue.remove(0);
            // Log.info("Wake up: " + t.getName());
            this.toWakeQueue.add(t);
            this.inQueue--;
            notifyAll();
        }
    }

}

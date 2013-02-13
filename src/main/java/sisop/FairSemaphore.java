package sisop;

import java.util.*;
import java.lang.Thread;
import sisop.logging.Log;


public class FairSemaphore{
    int value;
    int inQueue;
    Thread toWake;
    List<Thread> queue = new ArrayList<Thread>();
    
    public FairSemaphore() {
        this.value = 1;
        this.inQueue = 0;
    }
    
    public FairSemaphore(int n) {
        this.value = n;
        this.inQueue = 0;
    }

    public boolean isEmpty() {
        return (inQueue == 0)?true:false;
    }

    public synchronized void P() {
        try {
            Thread t = Thread.currentThread(); 
            if (this.value == 0) {
                this.inQueue++;
                queue.add(t);
                //Log.info("FairSemaphore: " + t.getName() + " Wait");
                while(t != toWake) wait();
                //Log.info("FairSemaphore: " + t.getName() + " WakeUp");
            }
            this.value--;
            this.toWake = null;
            //Log.info("FairSemaphore: " + t.getName() + " Do somethings");
        }
        catch (InterruptedException e) {
            Log.severe("Wait Error " + e);
        }
    }

    public synchronized void V(){
        this.value++;
        if (this.inQueue != 0) {
            toWake = queue.remove(0);
            this.inQueue--;
            notifyAll();
        }
    }

}

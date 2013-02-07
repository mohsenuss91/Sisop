package sisop;

import java.util.*;
import java.lang.Thread;


public class FairSemaphore{
    int value;
    int inQueue;
    List<Thread> queue = new ArrayList<Thread>();
    
    public FairSemaphore() {
        this.value = 0;
        this.inQueue = 0;
    }
    
    public FairSemaphore(int n) {
        this.value = n;
        this.inQueue = 0;
    }

    public boolean isEmpty() {
        return (inQueue == 0)?true:false;
    }

    public synchronized void V() {
        try {
            if (this.value == 0) {
                this.inQueue++;
                queue.add(Thread.currentThread());
                wait();
            }
            this.value--;    
        }
        catch (InterruptedException e) {
            System.err.println("Errore nella wait " + e);
        }
    }

    public synchronized void P(){
        this.value++;
        if (this.inQueue != 0) {
            Thread t = new Thread();
            t = queue.remove(0);
            this.inQueue--;
            t.notify();
        }
    }

}

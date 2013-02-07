package sisop;

import java.util.*;
import java.lang.Thread;


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
                System.out.println(t.getName() + " Sospeso");
                while(t != toWake) wait();
                System.out.println(t.getName() + " Risvegliato");
            }
            this.value--;    
            System.out.println(t.getName() + " Eseguo");
        }
        catch (InterruptedException e) {
            System.err.println("Errore nella wait " + e);
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

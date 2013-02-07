package sisop;

import java.lang.Thread;

class Prova extends Thread {
    FairSemaphore sem;
    
    public Prova (FairSemaphore sem, String name){
        super(name);
        this.sem = sem;
    }

    public void run () {
        sem.P();
    }
    
}

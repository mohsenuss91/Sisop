package sisop;

import java.lang.Thread;
import java.lang.Integer;
import sisop.*;

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


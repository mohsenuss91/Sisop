package sisop.test;

import sisop.FairSemaphore;
import sisop.logging.Log;
import java.lang.Integer;

class Sem extends Thread {
    FairSemaphore s;

    public Sem (FairSemaphore s, String name){
        super(name);
        this.s = s;
    }

    public void run(){
        this.s.P();
    }
}

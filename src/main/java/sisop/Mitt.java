package sisop;

import java.lang.Thread;
import java.lang.Integer;
import sisop.SynchPort;
import sisop.PortVector;
import sisop.Dest;
import sisop.Test;
import sisop.logging.Log;
import java.lang.Math;




public class Mitt extends Thread {
    PortVector<Integer> port;
    Dest dest;
    // Dest[] dest = new Dest[2];
    public Mitt(String name) {
        super(name);
        dest = Test.getDest((int)(Math.random()*10)%3);
        // dest[0] = Test.getDest((int)(Math.random()*10)%3);
        // dest[1] = Test.getDest((int)(Math.random()*10)%3);
    }

    public void run () {
        try {
            int j = (int)(Math.random()*20)%5;
            this.port = this.dest.getPort();
            Log.info(Thread.currentThread().getName() + ": sendTo() " + this.dest.name + "to port: " + j);
            this.port.sendTo(j, 5,Thread.currentThread().getName());
            Thread.sleep(1000);
            Log.info(Thread.currentThread().getName() + ": Message received");
            j = (j+1)%5;
            //this.port = this.dest[j].getPort();
            Log.info(Thread.currentThread().getName() + ": sendTo() " + this.dest.name + "to port: " +j);
            this.port.sendTo(j, 1,Thread.currentThread().getName());
            Thread.sleep(100);
            Log.info(Thread.currentThread().getName() + ": Message received");
            
}
        catch (Exception e) {
            Log.info("Errore Mitt" + e);
        }
    }
    
}

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
    Dest[] dest = new Dest[4];
    public Mitt(String name) {
        super(name);
        dest[0] = Test.getDest((int)(Math.random()*10)%5);
        dest[1] = Test.getDest((int)(Math.random()*10)%5);
        dest[2] = Test.getDest((int)(Math.random()*10)%5);
        dest[3] = Test.getDest((int)(Math.random()*10)%5);        
    }

    public void run () {
        try {
            int j = (int)(Math.random()*10)%4;
            int k = (int)(Math.random()*20)%5;
            this.port = this.dest[j].getPort();
            Log.info(Thread.currentThread().getName() + ": sendTo() " + this.dest[j].name + " to Port: " + k);
            this.port.sendTo(k, 1,Thread.currentThread().getName());
            Log.info(Thread.currentThread().getName() + ": Message received");
            j = (j+1)%4;
            k = (k+1)%5;
            this.port = this.dest[j].getPort();
            Log.info(Thread.currentThread().getName() + ": sendTo() " + this.dest[j].name + " to Port: " + k);
            this.port.sendTo(k, 1,Thread.currentThread().getName());
            Log.info(Thread.currentThread().getName() + ": Message received");
            j = (j+1)%4;
            k = (k+1)%5;
            this.port = this.dest[j].getPort();
            Log.info(Thread.currentThread().getName() + ": sendTo() " + this.dest[j].name + " to Port: " + k);
            this.port.sendTo(k, 1,Thread.currentThread().getName());
            Log.info(Thread.currentThread().getName() + ": Message received");
            j = (j+1)%4;
            k = (k+1)%5;
            this.port = this.dest[j].getPort();
            Log.info(Thread.currentThread().getName() + ": sendTo() " + this.dest[j].name + " to Port: " + k);
            this.port.sendTo(k, 1,Thread.currentThread().getName());
            Log.info(Thread.currentThread().getName() + ": Message received");
            
}
        catch (Exception e) {
            
        }
    }
    
}

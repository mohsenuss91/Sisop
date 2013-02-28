package sisop;

import java.lang.Thread;
import java.lang.Integer;
import sisop.SynchPort;
import sisop.PortVector;
import sisop.Dest;
import sisop.Test;
import sisop.Server;
import sisop.logging.Log;
import java.lang.Math;




public class Mitt extends Thread {
    PortVector<Integer> port;
    //Dest[] dest = new Dest[4];
    Server server;
    int priority;
    public Mitt(int p, Server s) {
        super("Mitt" + p);
        // dest[0] = Test.getDest((int)(Math.random()*10)%5);
        // dest[1] = Test.getDest((int)(Math.random()*10)%5);
        // dest[2] = Test.getDest((int)(Math.random()*10)%5);
        // dest[3] = Test.getDest((int)(Math.random()*10)%5);        
        this.server = s;
        this.priority = p;
        this.port = s.getPort();
    }

    public void run () {
        try {
            // int j = (int)(Math.random()*10)%4;
            // int k = (int)(Math.random()*20)%5;
            // this.port = this.dest[j].getPort();
            // Log.info(Thread.currentThread().getName() + ": sendTo() " + this.dest[j].name + " to Port: " + k);
            // this.port.sendTo(k, 1,Thread.currentThread().getName());
            // Log.info(Thread.currentThread().getName() + ": Message received");
            // j = (j+1)%4;
            // k = (k+1)%5;
            // this.port = this.dest[j].getPort();
            // Log.info(Thread.currentThread().getName() + ": sendTo() " + this.dest[j].name + " to Port: " + k);
            // this.port.sendTo(k, 1,Thread.currentThread().getName());
            // Log.info(Thread.currentThread().getName() + ": Message received");
            // j = (j+1)%4;
            // k = (k+1)%5;
            // this.port = this.dest[j].getPort();
            // Log.info(Thread.currentThread().getName() + ": sendTo() " + this.dest[j].name + " to Port: " + k);
            // this.port.sendTo(k, 1,Thread.currentThread().getName());
            // Log.info(Thread.currentThread().getName() + ": Message received");
            // j = (j+1)%4;
            // k = (k+1)%5;
            // this.port = this.dest[j].getPort();
            // Log.info(Thread.currentThread().getName() + ": sendTo() " + this.dest[j].name + " to Port: " + k);
            // this.port.sendTo(k, 1,Thread.currentThread().getName());
            // Log.info(Thread.currentThread().getName() + ": Message received");
            
            int number;
            for (int i = 0; i < 4; i++) {
                number = (int)(Math.random()*100);
                Log.info(Thread.currentThread().getName() + ": Send number " + number);
                this.port.sendTo(this.priority, number, Thread.currentThread().getName());
                Thread.sleep(i*1000);
            }
}
        catch (Exception e) {
            Log.severe("Mitt Error " + e);
        }
    }
    
}

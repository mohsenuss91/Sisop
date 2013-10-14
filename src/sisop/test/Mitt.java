package sisop.test;

import java.lang.Thread;
import java.lang.Integer;
import sisop.SynchPort;
import sisop.PortVector;
import sisop.test.Dest;
import sisop.test.Test;
import sisop.Mailbox;
import sisop.logging.Log;
import java.lang.Math;




public class Mitt extends Thread {
    PortVector<Integer> port;
    Mailbox mailbox;
    int priority;

    public Mitt(int p, Mailbox m) {
        super("Mitt" + (p+1));
        this.mailbox = m;
        this.priority = p;
        this.port = m.inPortVector;
    }

    public void run () {
        try {
            int number;
            for (int i = 0; i < 4; i++) {
                number = (int)(Math.random()*100);
                Log.info(Thread.currentThread().getName() + ": Send number " + number + " to port: " + this.priority);
                this.port.sendTo(this.priority, number, Thread.currentThread().getName());
                // Thread.sleep(3000);
            }
        }
        catch (Exception e) {
            Log.severe(Thread.currentThread().getName() + " Error " + e);
        }
    }
    
}

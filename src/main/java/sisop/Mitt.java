package sisop;

import java.lang.Thread;
import java.lang.Integer;
import sisop.SynchPort;
import sisop.Dest;
import sisop.logging.Log;




public class Mitt extends Thread {
    SynchPort<Integer> port;
    Dest dest;
    public Mitt(Dest dest, String name) {
        super(name);
        this.dest = dest;
    }

    public void run () {
        try {
            this.port = this.dest.getPort();
            Log.info(Thread.currentThread().getName() + ": sendTo()");
            //System.out.println(Thread.currentThread().getName() + ": sendTo()");
            this.port.sendTo(1,Thread.currentThread().getName());
            Thread.sleep(100);
            Log.info(Thread.currentThread().getName() + ": Message received");
            //System.out.println(Thread.currentThread().getName() + ": Message received");
            
        }
        catch (Exception e) {
            
        }
    }
    
}

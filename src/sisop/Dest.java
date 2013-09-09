package sisop;

import java.lang.Thread;
import java.lang.Integer;
import sisop.SynchPort;
import sisop.PortVector;
import sisop.Mailbox;
import sisop.logging.Log;



public class Dest extends Thread {
    // public PortVector<Integer> port = new PortVector<Integer>(5);
    // public String name;
    // int[] array = new int[5];
    // int arrayLength;
    Mailbox mailbox;

    public Dest(Mailbox m){
        super("Dest");
        this.mailbox = m;
        // this.name = name;
        // for (int i = 0; i < 5; i++) {
        //     array[i] = i;
        // }
        // arrayLength = 5;
    }
    public void run () {
        try {
            Message<Integer> app;
            int cycle = 0;
            while (cycle<20){
                cycle++;
                // Thread.sleep(2000);
                app = this.mailbox.receiveFrom();
                Log.info(Thread.currentThread().getName() + ": Received data: " + app.message + " from: " + app.threadName);
            }
        }
        catch (Exception e) {
            Log.info("Dest Error " + e);   
        }
    }

    // public PortVector<Integer> getPort() {
    //     return this.port;
    // }
    
    // public String getName() {
    //     return this.name;;
    // }
}

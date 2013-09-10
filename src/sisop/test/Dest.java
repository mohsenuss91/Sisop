package sisop.test;

import java.lang.Thread;
import java.lang.Integer;
import sisop.SynchPort;
import sisop.PortVector;
import sisop.Mailbox;
import sisop.logging.Log;
import sisop.Message;


public class Dest extends Thread {
    Mailbox mailbox;

    public Dest(Mailbox m){
        super("Dest");
        this.mailbox = m;
    }

    public void run () {
        try {
            Message<Integer> app;
            int cycle = 0;
            while (cycle<20){
                cycle++;
                app = this.mailbox.receiveFrom();
                Log.info(Thread.currentThread().getName() + ": Received data: " + app.getMessage() + " from: " + app.getName());
            }
        }
        catch (Exception e) {
            Log.info("Dest Error " + e);   
        }
    }
}

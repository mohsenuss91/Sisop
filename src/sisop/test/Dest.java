package sisop.test;

import java.lang.Thread;
import java.lang.Integer;
import sisop.SynchPort;
import sisop.PortVector;
import sisop.Mailbox;
import sisop.logging.Log;
import sisop.Message;
import sisop.MessageReceived;


public class Dest extends Thread {
    Mailbox mailbox;
    PortVector<Integer> port;
    int portNumber = 1; //There are only one port in the Mailbox's outPortVector
    int[] portIndex = new int[portNumber];

    public Dest(Mailbox m){
        super("ric");
        this.mailbox = m;
        this.port = m.outPortVector;
        this.portIndex[0] = 0; //Set the required port to receive the message
    }

    public void run () {
        try {
            MessageReceived<Integer> app;
            int cycle = 0;
            while (cycle<20){
                cycle++;
                app = this.port.receiveFrom(portIndex, portNumber);
                Log.info(Thread.currentThread().getName() + ": Received data: " + app.getMessage() + " from: " + app.getName());
            }
        }
        catch (Exception e) {
            Log.info(Thread.currentThread().getName() + " Error " + e);   
        }
    }
}

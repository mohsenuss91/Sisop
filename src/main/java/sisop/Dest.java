package sisop;

import java.lang.Thread;
import java.lang.Integer;
import sisop.SynchPort;
import sisop.PortVector;
import sisop.Server;
import sisop.logging.Log;



public class Dest extends Thread {
    // public PortVector<Integer> port = new PortVector<Integer>(5);
    // public String name;
    // int[] array = new int[5];
    // int arrayLength;
    Server server;

    public Dest(Server s){
        super("Dest");
        this.server = s;
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
                Thread.sleep(2000);
                app = this.server.receiveFrom();
                //Log.info(Thread.currentThread().getName() + ": Received. Data: " + app.message + " from: " + app.threadName);
            }
        }
        catch (Exception e) {
            Log.info("Errore Dest" + e);   
        }
    }

    // public PortVector<Integer> getPort() {
    //     return this.port;
    // }
    
    // public String getName() {
    //     return this.name;;
    // }
}

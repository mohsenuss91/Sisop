package sisop;

import java.lang.Thread;
import java.lang.Integer;
import sisop.SynchPort;
import sisop.logging.Log;



public class Dest extends Thread {
    public SynchPort<Integer> port = new SynchPort<Integer>(1);
    public String name;

    public Dest(String name){
        super(name);
        this.name = name;
    }
    public void run () {
        try {
            Message<Integer> app;
            while (true){
                Thread.sleep(2000);
                Log.info(Thread.currentThread().getName() + ": receiveFrom()");
                app = port.receiveFrom();
                Log.info(Thread.currentThread().getName() + ": Received. Data: " + app.message + " from " + app.threadName);
            }
        }
        catch (Exception e) {
            
        }
    }

    public SynchPort<Integer> getPort() {
        return this.port;
    }
    
    // public String getName() {
    //     return this.name;;
    // }
}

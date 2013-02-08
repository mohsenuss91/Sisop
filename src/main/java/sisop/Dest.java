package sisop;

import java.lang.Thread;
import java.lang.Integer;
import sisop.SynchPort;
import sisop.logging.Log;



public class Dest extends Thread {
    public SynchPort<Integer> port = new SynchPort<Integer>(5);

    public Dest(String name){
        super(name);
    }
    public void run () {
        try {
            Message<Integer> app;
            while (true){
                Thread.sleep(200);
                Log.info(Thread.currentThread().getName() + ": receiveFrom()");
                //System.out.println(Thread.currentThread().getName() + ": receiveFrom()");
                app = port.receiveFrom();
                Log.info(Thread.currentThread().getName() + ": Received. Data: " + app.message + " from " + app.threadName);
                //System.out.println(Thread.currentThread().getName() + ": Received. Data: " + app.message + " from " + app.threadName);
            }
        }
        catch (Exception e) {
            
        }
    }

    public SynchPort<Integer> getPort() {
        return port;
    }
    
}

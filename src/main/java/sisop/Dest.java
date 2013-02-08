package sisop;

import java.lang.Thread;
import java.lang.Integer;
import sisop.*;



public class Dest extends Thread {
    public SynchPort<Integer> port = new SynchPort<Integer>(5);

    public void run () {
        try {
            Thread.sleep(2000);
            System.out.println("Dest:Pronto a ricevere");
            Message<Integer> app = port.receiveFrom();
            System.out.println("Dest: Ricevuto. Data: " + app.message + " da thread " + app.threadName);
        }
        catch (Exception e) {
            
        }
    }

    public SynchPort<Integer> getPort() {
        return port;
    }
    
}

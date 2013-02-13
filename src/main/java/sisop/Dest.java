package sisop;

import java.lang.Thread;
import java.lang.Integer;
import sisop.SynchPort;
import sisop.PortVector;
import sisop.logging.Log;



public class Dest extends Thread {
    public PortVector<Integer> port = new PortVector<Integer>(5);
    public String name;
    int[] array = new int[5];
    int arrayLength;

    public Dest(String name){
        super(name);
        this.name = name;
        for (int i = 0; i < 5; i++) {
            array[i] = i;
        }
        arrayLength = 5;
    }
    public void run () {
        try {
            MessageReceived<Integer> app;
            while (true){
                Thread.sleep(200);
                Log.info(Thread.currentThread().getName() + ": receiveFrom()");
                app = port.receiveFrom(array, arrayLength);
                Log.info(Thread.currentThread().getName() + ": Received. Data: " + app.message + " from: " + app.threadName + " from port: " + app.portIndex);
            }
        }
        catch (Exception e) {
            Log.info("Errore Dest" + e);   
        }
    }

    public PortVector<Integer> getPort() {
        return this.port;
    }
    
    // public String getName() {
    //     return this.name;;
    // }
}

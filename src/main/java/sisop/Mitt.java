package sisop;

import java.lang.Thread;
import java.lang.Integer;
import sisop.*;




public class Mitt extends Thread {
    SynchPort<Integer> port;
    Dest dest;
    public Mitt(Dest dest) {
        this.dest = dest;
    }

    public void run () {
        try {
            this.port = this.dest.getPort();
            System.out.println("Mitt: Mitt invia");
            this.port.sendTo(1,"Mitt");
            Thread.sleep(100);
            System.out.println("Mitt: Inviato!!!");
            
        }
        catch (Exception e) {
            
        }
    }
    
}

package sisop.test;

import sisop.FairSemaphore;
import sisop.SynchPort;
import sisop.PortVector;
import sisop.test.Mitt;
import sisop.test.Dest;
import sisop.test.Sem;
import sisop.Mailbox;
import sisop.logging.Log;

import java.lang.Thread;
import java.lang.Integer;

public class Test{
    public static void main(String[] args) {
        // ------------Test Semaphore--------------
        // try {        
        //     Log.setup("../test.log");
        //     Log.info("---------Program Start------------");
        //     FairSemaphore fsem = new FairSemaphore(0);
        //     Sem[] s = new Sem[10];
        //     for (int i = 0; i < 10; ++i) {
        //         s[i] = new Sem(fsem, "Thread"+i);
        //     }
        //     for (int i = 0; i < 10; ++i) {
            
        //         s[i].start();
        //         Thread.sleep(100);
            
        //     }
        //     Log.info("Semaforo vuoto: " + fsem.isEmpty());
        //     for (int i = 0; i < 10; ++i) {
        //         // Thread.sleep(1000);
        //         Log.info("Test wake");
        //         fsem.V();
        //     }
        // }
        // catch (Exception e) {
    
        // }
        // Log.info("----------Program End--------------");
        // System.exit(0);
        
        // -------------Test Mailbox------------------
        try {
            Log.setup("../test.log");
            Log.info("---------Program Start------------");
            Mailbox mailbox = new Mailbox();        
            mailbox.start();
            Mitt[] mitt = new Mitt[5];
            Dest dest = new Dest(mailbox);
            for (int i = 0; i < 5; i++) {
                mitt[i] = new Mitt(i, mailbox); 
                mitt[i].start();
            }
            dest.start();
            dest.join();
            Log.info("----------Program End--------------");
            System.exit(0);
        }
        catch (Exception e) {
            Log.severe("Main Error " + e);
        }
    }
}

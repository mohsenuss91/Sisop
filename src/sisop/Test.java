package sisop;

import sisop.FairSemaphore;
import sisop.SynchPort;
import sisop.PortVector;
import sisop.Mitt;
import sisop.Dest;
import sisop.Mailbox;
import sisop.logging.Log;

import java.lang.Thread;
import java.lang.Integer;

public class Test{
    //public static Dest[] dest = new Dest[5];
    // public static Server server = new Server();
    public static void main(String[] args) {
        
        //--------------Test Semaphore-------------
        // FairSemaphore sem = new FairSemaphore(0);
        // Prova[] p = new Prova[5];
        // for (int i = 0; i < 5; ++i) {
        //     p[i] = new Prova(sem, "Thread"+i);
        // }

        // for (int i = 0; i < 5; ++i) {
        //     try {
        //         p[i].start();
        //         Thread.sleep(100); 
        //     }
        //     catch (Exception e) {
                
        //     }
        // }
        // try {
        //     System.out.println(sem.isEmpty());
        //     for (int i = 0; i < 5; ++i) {
        //         Thread.sleep(100);
        //         sem.V();
        //     }
        // }
        // catch (Exception e) {
            
        // }
        //-------------------------------------------
        
        //--------------Test SynchPort----------------
        // try {
        //     Log.setup("../test.log");
        //     //Dest[] dest = new Dest[3];
        //     Mitt[] mitt = new Mitt[10];
        //     for (int j = 0; j < 3; ++j) {
        //         dest[j] = new Dest("Dest" + j);
        //         dest[j].start();
        //     }
        //     for (int i = 0; i < 10; ++i) {
        //         mitt[i] = new Mitt("Mitt" + i);
        //         mitt[i].start();
        //     }
                      
        // }
        // catch (Exception e) {
            
        // }
        //-------------------------------------------

        //-------------Test PortVector---------------
        // try {
        //     Log.setup("../test.log");
        //     Mitt[] mitt = new Mitt[50];
        //     for (int j = 0; j < 5; j++) {
        //         dest[j] = new Dest("Dest" + j);
        //         dest[j].start();
        //     }
        //     for (int i = 0; i < 50; i++) {
        //         mitt[i] = new Mitt("Mitt" + i);
        //         mitt[i].start();
        //     }
                      
        // }
        // catch (Exception e) {
        //     Log.info("Errore Test" + e);
        // }

        //--------------------------------------------
        
        //-------------Test Mailbox------------------
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


    // public static Dest getDest(int n) {
    //     return dest[n];
    // }
}

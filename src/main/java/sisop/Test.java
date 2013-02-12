package sisop;

import sisop.FairSemaphore;
import sisop.Prova;
import sisop.SynchPort;
import sisop.PortVector;
import sisop.Mitt;
import sisop.Dest;
import sisop.logging.Log;
import java.lang.Thread;
import java.lang.Integer;

public class Test{
    public static Dest[] dest = new Dest[3];
    
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
        try {
            Log.setup("../test.log");
            Mitt[] mitt = new Mitt[10];
            for (int j = 0; j < 3; j++) {
                dest[j] = new Dest("Dest" + j);
                dest[j].start();
            }
            for (int i = 0; i < 10; ++i) {
                mitt[i] = new Mitt("Mitt" + i);
                mitt[i].start();
            }
                      
        }
        catch (Exception e) {
            Log.info("Errore Test" + e);
        }
        
        
    }


    public static Dest getDest(int n) {
        return dest[n];
    }
}

package sisop;

import sisop.FairSemaphore;
import sisop.Prova;
import sisop.SynchPort;
import java.lang.Thread;
import java.lang.Integer;

public class Test{
    public static void main(String[] args) {
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
        try {
            Dest dest = new Dest();
            Mitt mitt = new Mitt(dest);
            dest.start();
            Thread.sleep(100);
            mitt.start();
            
        }
        catch (Exception e) {
            
        }
        
        
    }
}

package sisop;

import sisop.FairSemaphore.*;
import sisop.Prova;
import java.lang.Thread;

public class Test{
    public static void main(String[] args) {
        FairSemaphore sem = new FairSemaphore();
        Prova[] p = new Prova[50];
        for (int i = 0; i < 50; ++i) {
            p[i] = new Prova(sem, "Thread"+i);
        }

        for (int i = 0; i < 50; ++i) {
            try {
                p[i].start();
                Thread.sleep(100); 
            }
            catch (Exception e) {
                
            }
        }
        try {
            System.out.println(sem.isEmpty());
            for (int i = 0; i < 50; ++i) {
                Thread.sleep(100);
                sem.V();
            }
        }
        catch (Exception e) {
            
        }
    }
}

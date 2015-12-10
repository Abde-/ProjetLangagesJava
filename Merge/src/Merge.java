
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abde
 * @param <T>
 */
public class Merge<T> implements Runnable {

    /**
     * @param args the command line arguments
     */
    
    private final long sizefirst;
    private final long sizelast;
    private T[] vector;
    
    public Merge(T[] vector, final long sizel, long sizef){
        this.sizefirst = sizef;
        this.sizelast = sizel;
        this.vector = vector;
    }
    
    public Merge(T[] vector, final long sizel){
        this(vector,sizel,0);
    }
    
    public static void main(String[] args) {
        
    }
    
    @Override
    public void run(){
        if (sizelast - sizefirst > 1){
            final long m = (sizelast - sizefirst)/2 + sizefirst;
            Runnable merge1 = new Merge<>(vector,m,sizefirst);
            Runnable merge2 = new Merge<>(vector,sizelast,m);
            
            Thread[] threads = new Thread[]{ new Thread(merge1), new Thread(merge2) };
            
            for (Thread i : threads) i.start();
            
            for (Thread i: threads){
                try {
                    i.join();
                } catch (InterruptedException ex) { 
                    Logger.getLogger(Merge.class.getName()).log(Level.SEVERE, null, ex); }
            }
            
            //T[] temp = new T[(sizelast - sizefirst)/2];
            //long i
            
        }
    }
}

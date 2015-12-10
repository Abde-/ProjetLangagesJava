
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abde
 * @param <T>
 */
public class Merge<T extends Comparable> implements Runnable {

    /**
     * @param args the command line arguments
     */
    
    private final int sizefirst;
    private final int sizelast;
    private T[] vector;
    
    public Merge(T[] vector, final int sizel, int sizef){
        this.sizefirst = sizef;
        this.sizelast = sizel;
        this.vector = vector;
    }
    
    public Merge(T[] vector, final int sizel){
        this(vector,sizel,0);
    }
    
    public static void main(String[] args) {
        
    }
    
    @Override
    public void run(){
        if (sizelast - sizefirst > 1){
            final int m = (sizelast - sizefirst)/2 + sizefirst;
            Runnable merge1 = new Merge<>(vector,m,sizefirst);
            Runnable merge2 = new Merge<>(vector,sizelast,m);
            
            Thread thread1 = new Thread(merge1);
            Thread thread2 = new Thread(merge2);
            
            thread1.start();
            thread2.start();
            
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Merge.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            T[] w = (T[]) new Object[(sizelast - sizefirst)/2];
            
            int i =sizefirst, j=sizefirst, k=sizefirst;
            
            for (; i < m; ++i) w[i] = vector[i];
            while( j < m && i < sizelast) vector[k++] = vector[i].compareTo(w[j]) < 0 ? vector[i++] : w[j++]; // overload > operator
            while( j < m ) vector[k++] = w[j++];
            
            // post traitement
            
        }
    }
    
}

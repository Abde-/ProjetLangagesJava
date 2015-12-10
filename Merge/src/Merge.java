
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

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
    private ArrayList<T> vector;
    
    public Merge(ArrayList<T> vector, final int sizel, int sizef){
        this.sizefirst = sizef;
        this.sizelast = sizel;
        this.vector = vector;
    }
    
    public Merge(ArrayList<T> vector, int sizel){
        this(vector,sizel,0);
    }
    
    public static void main(String[] args) throws InterruptedException {
        int[] array = new int[]{3,1,6,4};
        ArrayList<Integer> vect = new ArrayList<>(array.length);        
        
        for (int i = 0; i <array.length; ++i) vect.add(array[i]);
        
        int length = array.length;
        
        Runnable merge = new Merge<>(vect,length);
        Thread thread = new Thread(merge);
        thread.start();
        thread.join();
        
        vect.stream().forEach((i) -> {
            System.out.println(i);
        });
    }
    
    @Override
    public void run(){
        if (sizelast - sizefirst > 1){
            
            // works, play a little with indexes
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
            
            // generic array creation SA MÈRE!!! -> essayer avec ArrayList
            ArrayList<T> w = new ArrayList<>((sizelast - sizefirst)/2);
            for(int i = 0; i < (sizelast - sizefirst)/2; ++i) w.add(vector.get(0));
            
            int i =sizefirst, j=sizefirst, k=sizefirst;
            
            for (; i < m; ++i) w.set( i, vector.get(i) );
            while( j < m && i < sizelast) vector.set( k++, vector.get(i).compareTo(w.get(j)) < 0 ? vector.get(i++) : w.get(j++) ); // overload > operator
            while( j < m ) vector.set(k++,w.get(j++));
            
            
            String y = "";
            for( int l = sizefirst; l < sizelast; ++l) y += vector.get(l).toString() + " ";
            System.out.println(y);
            // post traitement
            
        }
    }
    
}

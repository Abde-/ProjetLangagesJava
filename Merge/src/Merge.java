
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
        int[] array = new int[]{2,38,59,94,67,54,63,60,94,92,86,59,84,89,5,91,79,55,1,76,50,80,44,32,2,37,15,30,80,63,39,62,69,63,83,19,53,12,3,3,18,23,94,83,36,37,41,15,38,46,49
                ,98,40,95,87,4,17,65,77,66,58,3,15,37,33,64,45,15,15,47,28,13,46,78,25,24,14,62,58,75,49,85,23,21,94,45,41,5,74,76,78,95,78,63,4,26,19,11,2,66,4};
        ArrayList<Integer> vect = new ArrayList<>(array.length);        
        
        for (int i = 0; i <array.length; ++i) vect.add(array[i]);
        
        int length = array.length;
        
        Runnable merge = new Merge<>(vect,length);
        Thread thread = new Thread(merge);
        thread.start();
        thread.join();
        
    }
    
    @Override
    public void run(){
        if (sizelast - sizefirst > 1){
            
            // works, play a little with indexes
            final int m = (sizelast - sizefirst)/2;
            
            Runnable merge1 = new Merge<>(vector,m+sizefirst,sizefirst); // left
            Runnable merge2 = new Merge<>(vector,sizelast,m+sizefirst); // right
            
            // creation of threads
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
            
            // generic array creation for left side
            ArrayList<T> w = new ArrayList<>(m);
            for(int i = 0; i < (sizelast - sizefirst)/2; ++i) w.add(vector.get(0));
            
            int i =sizefirst, j=0, k=sizefirst;
            
            int ind = 0;
            for (; i < m+sizefirst; ++i) w.set( ind++, vector.get(i) );
            
            while( j < m && i < sizelast) vector.set( k++, vector.get(i).compareTo(w.get(j)) < 0 ? vector.get(i++) : w.get(j++) ); // overload > operator
            while( j < m ) vector.set(k++,w.get(j++));
            
            
            String y = "";
            for( int l = sizefirst; l < sizelast; ++l) y += vector.get(l).toString() + " ";
            System.out.println(y);
            // post traitement
            
        }
    }
    
}
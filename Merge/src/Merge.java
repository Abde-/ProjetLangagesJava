import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 *
 * @author El-Haman Abdeselam, Abdeselam
 * @param <T>
 * Matricule: 000411426
 * 2015-2016 BA2
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
        int[] array = new int[]{ 2,38,59,94,67,54,63,60,94,92,86,59,84,89,13 };
        ArrayList<Integer> vect = new ArrayList<>(array.length);
        
        for (int i = 0; i <array.length; ++i) vect.add(array[i]);
        
        int length = array.length;
        
        Runnable merge = new Merge<>(vect,length);
        Thread thread = new Thread(merge);
        
        System.out.println("Création de "+thread.getName());
        
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
            
            System.out.println("Création de gauche "+thread1.getName() + " et droite " + thread2.getName());
            thread1.start();
            thread2.start();
            
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Merge.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //------------------------- post traitement
            // generic array creation for left side
            ArrayList<T> w = new ArrayList<>(m);
            
            int i =sizefirst, j=0, k=sizefirst;
            
            for (; i < m+sizefirst; ++i) w.add( vector.get(i) );
            
            while( j < m && i < sizelast) vector.set( k++, vector.get(i).compareTo(w.get(j)) < 0 ? vector.get(i++) : w.get(j++) ); // overload > operator
            while( j < m ) vector.set(k++,w.get(j++));
            
            
            String y = "";
            for( int l = sizefirst; l < sizelast; ++l) y += vector.get(l).toString() + " ";
            System.out.println(Thread.currentThread().getName()+" "+y);
            
        }
    }
    
}

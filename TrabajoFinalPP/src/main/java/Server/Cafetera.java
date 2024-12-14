
package Server;

import java.io.Serializable;
import java.util.concurrent.Semaphore;

/**
 *
 * @author aleja
 */
public class Cafetera implements Serializable{
    private Repostero reposteroActual;
    private Semaphore semaforo= new Semaphore(1);
    
    public Cafetera() {
    }
    
    public void empezarCafe(Repostero repostero){
        try{
            semaforo.acquire();
            reposteroActual=repostero;
        }catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
    
    public void terminarCafe(){
        try{
            semaforo.release();
            reposteroActual=null;
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Repostero getReposteroActual() {
        return reposteroActual;
    }
    
}

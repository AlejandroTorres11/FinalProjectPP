/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.Serializable;
import static java.lang.Math.random;
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajofinalpp;

import java.util.Random;

/**
 *
 * @author aleja
 */
public class Repostero extends Thread{
    private String idRepostero; //si se pone id resulta en un error
    private int numeroDeTandas;
    private Horno horno;
    private Cafetera cafetera;
    
    private Random random = new Random();
    
    public Repostero(String idRepostero, Horno horno,Cafetera cafetera) {
        this.idRepostero = idRepostero;
        this.horno = horno;
        this.numeroDeTandas=0;
        this.cafetera= cafetera;
    }
    
   public int producirTandaGalletas() throws InterruptedException{ //produce las tandas de galletas con numero aleatorio
       sleep(2000 + random.nextInt(2000));
       int numeroAleatorio = 37 + random.nextInt(45 - 37 + 1);
       numeroDeTandas++;
       return numeroAleatorio;
   }
   
    @Override
   public void run(){
       while(true){
           try {
            boolean ultimaTanda=false;
            numeroDeTandas=0;
            //trabajando
            while(!ultimaTanda){
                int nGalletas=producirTandaGalletas();
                horno.depositarGalletas(nGalletas);
                System.out.println(idRepostero + " Deposita "+ nGalletas+ " En " + horno.getIdHorno());
                if(numeroDeTandas>=3){ 
                    ultimaTanda = (Math.random() < 0.5); //entre 3 y 5 tandas hay un 50% de que sea la ultima
                } 
                if(numeroDeTandas>=5){
                    ultimaTanda=true; //si hasta ahora no ha sido la ultima, ahora lo será
                }
            }
            //hacer Café
            cafetera.empezarCafe();
            System.out.println(idRepostero + " empieza a hacer café ");
            sleep(2000);
            cafetera.terminarCafe();
            System.out.println(idRepostero + " termina de hacer café ");
            //descansar
            System.out.println(idRepostero + " empieza a descansar ");
            sleep(3000 + random.nextInt(3000));
            System.out.println(idRepostero + " termina de descansar ");
            
            } catch (InterruptedException ie) {

            }
       }
        
   }

    public String getIdRepostero() {
        return idRepostero;
    }

    public void setId(String id) {
        this.idRepostero = idRepostero;
    }
    
    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
   
}

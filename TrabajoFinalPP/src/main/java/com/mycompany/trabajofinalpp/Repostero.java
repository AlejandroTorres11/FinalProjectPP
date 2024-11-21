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
    private int tandaGalletas;
    private int numeroDeTandas;
    private Horno horno;
    private Random random = new Random();
    
    public Repostero(String idRepostero, int tandaGalletas, Horno horno) {
        this.idRepostero = idRepostero;
        this.tandaGalletas = tandaGalletas;
        this.horno = horno;
        this.numeroDeTandas=0;
    }
    
   public int producirTandaGalletas() throws InterruptedException{ //produce las tandas de galletas con numero aleatorio
       Thread.sleep(2000 + random.nextInt(2000));
       int numeroAleatorio = 37 + random.nextInt(45 - 37 + 1);
       numeroDeTandas++;
       return numeroAleatorio;
   }
   public void depositarTanda(int nGalletas){
       //ToDo
       //horno.depositarGalletas
   }
    @Override
   public void run(){
        try {
            boolean ultimaTanda=false;
            //trabajando
            while(!ultimaTanda){
                int nGalletas=producirTandaGalletas();
                System.out.println(idRepostero + " Deposita "+ nGalletas+ " En " + horno.idHorno);
                depositarTanda(nGalletas);
                if(numeroDeTandas>=3){ 
                    ultimaTanda = (Math.random() < 0.5); //entre 3 y 5 tandas hay un 50% de que sea la ultima
                } 
                if(numeroDeTandas>=5){
                    ultimaTanda=true; //si hasta ahora no ha sido la ultima, ahora lo será
                }
            }
            //paradaParaCafé
            
            
        } catch (InterruptedException ie) {
            
        }
   }

    public String getIdRepostero() {
        return idRepostero;
    }

    public void setId(String id) {
        this.idRepostero = idRepostero;
    }

    public int getTandaGalletas() {
        return tandaGalletas;
    }

    public void setTandaGalletas(int tandaGalletas) {
        this.tandaGalletas = tandaGalletas;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
   
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajofinalpp;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author aleja
 */
public class Repostero extends Thread{
    private String idRepostero; //si se pone id resulta en un error
    private int numeroDeTandas;
    private List<Horno> listaHornos;
    private boolean esperaCafe;
    private String estado;

    private Cafetera cafetera;
    private Random random = new Random();
    
  
    public Repostero(String idRepostero, List<Horno> listaHornos, Cafetera cafetera) {
        this.idRepostero = idRepostero;
        this.listaHornos=listaHornos;
        this.numeroDeTandas = 0;
        this.cafetera = cafetera;
        this.esperaCafe=false;
        this.estado="inactivo";
    }
    
   public int producirTandaGalletas() throws InterruptedException{ //produce las tandas de galletas con numero aleatorio
       sleep(2000 + random.nextInt(2000));
       int numeroAleatorio = 37 + random.nextInt(45 - 37 + 1);
       numeroDeTandas++;
       return numeroAleatorio;
   }
   public Horno buscarHorno(){
       Horno resultado = null;
       for(Horno h:listaHornos){
           if(h.isListoParaDepositar()){
               resultado=h;
           }
       }
       return resultado;
   }
   
    @Override
   public void run(){
       while(true){
           try {
            boolean ultimaTanda=false;
            numeroDeTandas=0;
            //trabajando
            while(!ultimaTanda){
                estado="produciendo"+ "("+numeroDeTandas+"/"+5+")"; 
                    Horno horno= buscarHorno();
                    if(horno!=null && horno.isListoParaDepositar()){ //si el horno no esta lleno
                        int nGalletas=producirTandaGalletas();
                        horno.depositarGalletas(nGalletas,this);
                        
                        if(numeroDeTandas>=3 ){ 
                            ultimaTanda = (Math.random() < 0.5); //entre 3 y 5 tandas hay un 50% de que sea la ultima
                        } 
                        if(numeroDeTandas>=5){
                            ultimaTanda=true; //si hasta ahora no ha sido la ultima, ahora lo será
                        }
                    }
            }
            //hacer Café
            esperaCafe=true; //para saber cuando un repostero quiere hacer una pausa para café, sirve para saber si estan esperando para café o no
            estado="pausa para el café";
            cafetera.empezarCafe(this);
            System.out.println(idRepostero + " empieza a hacer cafe ");
            sleep(2000);
            cafetera.terminarCafe();
            System.out.println(idRepostero + " termina de hacer cafe ");
            esperaCafe=false; //deja de esperar al café
            //descansar
            estado="descanso";
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

    public boolean isEsperaCafe() {
        return esperaCafe;
    }

    public void setEsperaCafe(boolean pausa) {
        this.esperaCafe = pausa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
   
}

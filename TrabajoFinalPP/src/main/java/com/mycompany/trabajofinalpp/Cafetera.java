/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajofinalpp;

import static java.lang.Math.random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author aleja
 */
public class Cafetera {
    Semaphore semaforo= new Semaphore(1);
    
    public Cafetera() {
    }
    
    public void empezarCafe(){
        try{
            semaforo.acquire();
        }catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
    
    public void terminarCafe(){
        try{
            semaforo.release();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

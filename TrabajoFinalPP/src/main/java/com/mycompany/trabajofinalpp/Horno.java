/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajofinalpp;

import java.util.concurrent.Semaphore;

/**
 *
 * @author aleja
 */
public class Horno {
    private String idHorno;
    private int nGalletasDentro;
    
    Semaphore semaforo= new Semaphore(1);
    
    public Horno(String idHorno) {
        this.idHorno = idHorno;
        this.nGalletasDentro=0;
    }
    public void depositarGalletas(int nGalletas) {
        try {
            semaforo.acquire();
            nGalletasDentro += nGalletas;
            System.out.println("Se han depositado " + nGalletas + " galletas en el horno. Total: " + nGalletasDentro);

        } catch (InterruptedException ie) {
            ie.printStackTrace(); 
        } finally {
            semaforo.release();
        }
    }
    public String getIdHorno() {
        return idHorno;
    }

    public void setIdHorno(String idHorno) {
        this.idHorno = idHorno;
    }
    
}

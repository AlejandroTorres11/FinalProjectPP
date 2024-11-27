/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajofinalpp;

import java.util.concurrent.Semaphore;

/**
 *
 * @author alejandro
 */
public class Almacen {
    private int capacidad;//1000
    private int nGalletasDentro;
    private boolean lleno;
    Semaphore semaforo= new Semaphore(1);

    public Almacen(int capacidad) {
        this.capacidad = capacidad;
        this.nGalletasDentro = 0;
        this.lleno=false;
    }
    public void introducirPaquete(){
        try {
            semaforo.acquire();
            if(!lleno){
            nGalletasDentro+=100;
            }
            if(nGalletasDentro==capacidad){
                lleno=true;
            }
            System.out.println("Se ha depositado un paquete en el Almacén. Total: " + nGalletasDentro);

        } catch (InterruptedException ie) {
            ie.printStackTrace(); 
        } finally {
            semaforo.release();
        }
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getnGalletasDentro() {
        return nGalletasDentro;
    }

    public void setnGalletasDentro(int nGalletasDentro) {
        this.nGalletasDentro = nGalletasDentro;
    }

    public boolean isLleno() {
        return lleno;
    }

    public void setLleno(boolean lleno) {
        this.lleno = lleno;
    }
    
}

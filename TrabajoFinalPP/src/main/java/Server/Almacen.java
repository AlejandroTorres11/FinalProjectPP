/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import static java.lang.Math.random;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author alejandro
 */
public class Almacen {
    private int capacidad;//1000
    private int nGalletasDentro;
    private boolean lleno;
    private Semaphore semaforoSala= new Semaphore(1); 
    //sala de almacen ficticia, alli un empaquetador puede entrar y dormir antes de manipular la sc, para no dificultar la comida del usuario
    private  Semaphore sc= new Semaphore(1);
    private Random random= new Random();
    private boolean usuarioQuiereComer;
    private int nGalletasComidas;
    public Almacen(int capacidad) {
        this.capacidad = capacidad;
        this.nGalletasDentro = 0;
        this.lleno=false;
        this.usuarioQuiereComer=false;
        this.nGalletasComidas=0;
    }
    public boolean introducirPaquete(Empaquetador empaquetador){
        boolean metido=false;
        if(!usuarioQuiereComer){
            try {
                semaforoSala.acquire();
                Thread.sleep(2000 + random.nextInt(2000));
                sc.acquire();
                if(nGalletasDentro>=capacidad){
                    lleno=true;
                    metido=false;
                    System.out.println(empaquetador.getIdEmpaquetador()+" ha intentado depositar un paquete pero el almacen esta lleno");
                }
                if(!lleno){
                    nGalletasDentro+=100;
                    metido=true;
                    System.out.println(empaquetador.getIdEmpaquetador() +" ha depositado un paquete en el Almacen --> Total: " + nGalletasDentro +"/"+ capacidad);
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace(); 
            } finally {
                sc.release();
                semaforoSala.release();
            }
        }
        return metido;
    }
    public int comer(int n){
        usuarioQuiereComer=true;
        int resultado=0;
        try {
            sc.acquire();
            if (nGalletasDentro >= n) { // Verificar si hay suficientes galletas
                lleno=false;
                nGalletasDentro -= n;
                resultado=n;
                nGalletasComidas+=n;
                System.out.println("Usuario come " + n + " galletas, estado del almacen --> " + nGalletasDentro + "/" + capacidad);
            }
        } catch (Exception e) {
        } finally {
            usuarioQuiereComer=false;
            sc.release();
        }
        return resultado;
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

    public int getnGalletasComidas() {
        return nGalletasComidas;
    }
    
}

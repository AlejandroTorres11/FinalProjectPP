package com.mycompany.trabajofinalpp;

import static java.awt.SystemColor.control;
import java.util.Random;
import java.util.concurrent.Semaphore;


public class Horno extends Thread {
    private String idHorno;
    private final int capacidad;
    private int nGalletasDentro;
    private boolean lleno;
    private boolean listoParaEmpaquetar;
    private boolean listoParaDepositar;
    private boolean listoParaHornear;
    private Semaphore semaforo= new Semaphore(1);
    private Random random = new Random();
    
    
    public Horno(String idHorno, int capacidad) {
        this.idHorno = idHorno;
        this.capacidad = capacidad;
        this.nGalletasDentro = 0;
        this.lleno = false;
        this.listoParaEmpaquetar = false;
        this.listoParaDepositar=true;
    }

    public void depositarGalletas(int nGalletas,Repostero repostero) {
        try {
            semaforo.acquire();
            int nGalletasDesperdiciadas = 0;
            if(nGalletasDentro!=capacidad){
                if (nGalletasDentro + nGalletas >= capacidad) {
                 lleno = true;
                 listoParaHornear=true;
                 listoParaDepositar=false;
                 nGalletasDesperdiciadas = (nGalletasDentro + nGalletas) - capacidad;
                 nGalletasDentro = capacidad;

                 System.out.println(repostero.getIdRepostero()+ " ha depositado " + nGalletas + " galletas en el " + idHorno +
                                ". Total: " + nGalletasDentro + "\n            Se han desperdiciado --> " +
                                nGalletasDesperdiciadas + " galletas");
                } else {
                 nGalletasDentro += nGalletas;

                  System.out.println(repostero.getIdRepostero()+ " ha depositado " + nGalletas + " galletas en el " + idHorno +
                                ". Total: " + nGalletasDentro );
                }

            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } finally {
            semaforo.release();
        }
    }

    public void hornearGalletas() {
        try {
            semaforo.acquire();
            listoParaDepositar=false;
            System.out.println(idHorno + " Empieza a hornear");
            sleep(8000);
            System.out.println(idHorno + " termina de hornear");
            listoParaHornear=false;
            listoParaEmpaquetar = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforo.release();
        }
    }

    public int extraerGalletas(Empaquetador empaquetador) {
        try {
            semaforo.acquire();
            if (nGalletasDentro - 20 >= 0) {
                nGalletasDentro -= 20;
                sleep(500 + random.nextInt(500));
                
            }
            if (nGalletasDentro == 0) { //se ha vaciado
                listoParaEmpaquetar = false;
                lleno = false;
                listoParaDepositar = true; 
                
            }
            System.out.println(empaquetador.getIdEmpaquetador()+" ha extraido un lote --> galletas en horno: "+nGalletasDentro);
            
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } finally {
            semaforo.release();
        }
        return 20;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " ha comenzado.");
        while (true) {
            try {
                if (listoParaHornear) {
                    hornearGalletas();
                }
                sleep(1000); // Pausa el hilo durante 1 segundo, si quitas esto no funciona el horno
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getIdHorno() {
        return idHorno;
    }

    public void setIdHorno(String idHorno) {
        this.idHorno = idHorno;
    }

    public synchronized int getnGalletasDentro() {
        return nGalletasDentro;
    }

    public void setnGalletasDentro(int nGalletasDentro) {
        this.nGalletasDentro = nGalletasDentro;
    }

    

    public synchronized boolean isLleno() {
        return lleno;
    }

    public void setLleno(boolean lleno) {
        this.lleno = lleno;
    }

    public synchronized boolean isListoParaEmpaquetar() {
        return listoParaEmpaquetar;
    }

    public void setListoParaEmpaquetar(boolean listoParaEmpaquetar) {
        this.listoParaEmpaquetar = listoParaEmpaquetar;
    }

    public synchronized boolean isListoParaDepositar() {
        return listoParaDepositar;
    }

    public void setListoParaDepositar(boolean listoParaDepositar) {
        this.listoParaDepositar = listoParaDepositar;
    }
    
    
}

package com.mycompany.trabajofinalpp;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Horno extends Thread {
    private String idHorno;
    private int capacidad;
    private int nGalletasCrudasDentro;
    private int nGalletasCocinadasDentro;
    private boolean lleno;
    private boolean sinGalletasHechas;
    private Semaphore semaforoCrudas = new Semaphore(1);
    private Semaphore semaforoCocinadas = new Semaphore(1);
    private Random random = new Random();
    
    
    public Horno(String idHorno, int capacidad) {
        this.idHorno = idHorno;
        this.capacidad = capacidad;
        this.nGalletasCrudasDentro = 0;
        this.nGalletasCocinadasDentro = 0;
        this.lleno = false;
        this.sinGalletasHechas = true;
    }

    public void depositarGalletas(int nGalletas) {
        try {
            semaforoCrudas.acquire();
            int nGalletasDesperdiciadas = 0;
            if (nGalletasCrudasDentro + nGalletas >= capacidad) {
                lleno = true;
                nGalletasDesperdiciadas = (nGalletasCrudasDentro + nGalletas) - capacidad;
                nGalletasCrudasDentro = capacidad;
            } else {
                nGalletasCrudasDentro += nGalletas;
            }
            System.out.println("Se han depositado " + nGalletas + " galletas en el " + idHorno +
                               ". Total: " + nGalletasCrudasDentro + "\n            Se han desperdiciado --> " +
                               nGalletasDesperdiciadas + " galletas");

        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } finally {
            semaforoCrudas.release();
        }
    }

    public void hornearGalletas() {
        try {
            semaforoCrudas.acquire();
            sleep(8000);
            nGalletasCrudasDentro = 0;
            nGalletasCocinadasDentro = capacidad;
            lleno = false;
            sinGalletasHechas = false;
        } catch (InterruptedException e) {
        } finally {
            semaforoCrudas.release();
        }
    }

    public int extraerGalletas() {
        try {
            semaforoCocinadas.acquire();
            if (nGalletasCocinadasDentro - 20 >= 0) {
                nGalletasCocinadasDentro -= 20;
                sleep(500 + random.nextInt(500));
            }
            if (nGalletasCocinadasDentro == 0) {
                sinGalletasHechas = true;
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } finally {
            semaforoCocinadas.release();
        }
        return 20;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " ha comenzado.");
        while (true) {
          
            try {
                if (lleno) {
                    System.out.println(idHorno + " Empieza a hornear");
                    hornearGalletas();
                    System.out.println(idHorno + " termina de hornear");
                }
                sleep(1000); // Pausa el hilo durante 1 segundo
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

    public int getnGalletasCrudasDentro() {
        return nGalletasCrudasDentro;
    }

    public void setnGalletasCrudasDentro(int nGalletasCrudasDentro) {
        this.nGalletasCrudasDentro = nGalletasCrudasDentro;
    }

    public boolean isLleno() {
        return lleno;
    }

    public void setLleno(boolean lleno) {
        this.lleno = lleno;
    }
    
    
}

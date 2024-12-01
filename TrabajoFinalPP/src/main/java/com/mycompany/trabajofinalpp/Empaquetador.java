/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajofinalpp;

import java.util.List;

/**
 *
 * @author alejandro
 */
public class Empaquetador extends Thread {
    private String idEmpaquetador; // Cambiado de int a String
    private List<Horno> listaHornos;
    private Horno horno;
    private Almacen almacen;
    private int nGalletasEncima;
 
    public Empaquetador(String idEmpaquetador, Horno horno, Almacen almacen) {
        this.idEmpaquetador = idEmpaquetador; // idEmpaquetador es ahora un String
        this.horno = horno;
        this.almacen = almacen;
        this.nGalletasEncima = 0;
    }

    @Override
    public void run() {
        // Implementación del método run
        while(true){
            
            try {
                while(nGalletasEncima<100){ 
                    if(horno.isListoParaEmpaquetar()){
                        nGalletasEncima+= horno.extraerGalletas(this);
                    }
                }
                if(nGalletasEncima==100){
                    almacen.introducirPaquete(this);
                    System.out.println(idEmpaquetador +" ha enviado paquete a almacén");
                    nGalletasEncima=0;
                }
            } catch (Exception e) {
            }
        }
    }

    public String getIdEmpaquetador() { // Cambiado el tipo de retorno a String
        return idEmpaquetador;
    }

    public void setIdEmpaquetador(String idEmpaquetador) { // Cambiado el parámetro a String
        this.idEmpaquetador = idEmpaquetador;
    }

    public Horno getHorno() {
        return horno;
    }

    public void setHorno(Horno horno) {
        this.horno = horno;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public int getnGalletasEncima() {
        return nGalletasEncima;
    }

    public void setnGalletasEncima(int nGalletasEncima) {
        this.nGalletasEncima = nGalletasEncima;
    }
}

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
public class Empaquetador extends Thread{
    private int idEmpaquetador;
    private List<Horno> hornosVacios;
    private List<Horno> hornosLlenos;
    private Horno horno;
    private Almacen almacen;
    private int nGalletasEncima;

    public Empaquetador(int idEmpaquetador, List<Horno> hornosVacios, List<Horno> hornosLlenos, Horno horno, Almacen almacen, int nGalletasEncima) {
        this.idEmpaquetador = idEmpaquetador;
        this.hornosVacios = hornosVacios;
        this.hornosLlenos = hornosLlenos;
        this.horno = horno;
        this.almacen = almacen;
        this.nGalletasEncima = nGalletasEncima;
    }
    public void run(){
        
    }
    
    public int getIdEmpaquetador() {
        return idEmpaquetador;
    }

    public void setIdEmpaquetador(int idEmpaquetador) {
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.io.Serializable;

/**
 *
 * @author alejandro
 */
public class Empaquetador extends Thread implements Serializable{
    private String idEmpaquetador; // Cambiado de int a String
    private Horno horno;
    private Almacen almacen;
    private int nGalletasEncima;
    private int nLotes;
    private String estado;
    
    public Empaquetador(String idEmpaquetador, Horno horno, Almacen almacen) {
        this.idEmpaquetador = idEmpaquetador; // idEmpaquetador es ahora un String
        this.horno = horno;
        this.almacen = almacen;
        this.nGalletasEncima = 0;
        this.nLotes=0;
        this.estado="esperando";
    }

    @Override
    public void run() {
        // Implementación del método run
        while(true){
            
            try {
                while(nGalletasEncima<100){ 
                    if(horno.isListoParaEmpaquetar()){
                        estado="empaquetando";
                        nGalletasEncima+= horno.extraerGalletas(this);
                        nLotes++;
                        //System.out.println("--------------------------------"+idEmpaquetador+" lleva ya "+nLotes+"------------------------------------");
                    }
                }
                if(nGalletasEncima==100){
                    if(almacen.isLleno()){
                        estado="esperando";
                        sleep(1000);
                    }
                    else{
                        estado="introduciendo en Almacén";
                        boolean metido=almacen.introducirPaquete(this);
                        if(metido){
                            nGalletasEncima=0;
                            nLotes=0;
                        }
                    }
                }
                estado="esperando";
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

    public int getnLotes() {
        return nLotes;
    }

    public void setnLotes(int nLotes) {
        this.nLotes = nLotes;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}

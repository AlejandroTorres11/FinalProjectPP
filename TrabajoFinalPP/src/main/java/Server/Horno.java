package Server;

import static java.awt.SystemColor.control;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.Semaphore;


public class Horno extends Thread implements Serializable{
    private String idHorno;
    private final int capacidad;
    private int nGalletasDentro;
    //control
    private boolean lleno;
    private boolean listoParaEmpaquetar;
    private boolean listoParaDepositar;
    private boolean listoParaHornear;
    private Semaphore semaforo= new Semaphore(1);
    //math
    private Random random = new Random();
    //UI
    private String estado;
    private int progresoHorneado;
    private int nGalletasHorneadas;
    private boolean horneando;
    public Horno(String idHorno, int capacidad) {
        this.idHorno = idHorno;
        this.capacidad = capacidad;
        this.nGalletasDentro = 0;
        this.lleno = false;
        this.listoParaEmpaquetar = false;
        this.listoParaDepositar=true;
        this.estado="inactivo";
        this.progresoHorneado=0;
        this.nGalletasHorneadas=0;
        this.horneando=false;
    }

    public int depositarGalletas(int nGalletas,Repostero repostero) {
        int nGalletasDesperdiciadas = 0;
        try {
            if(listoParaDepositar){
                semaforo.acquire();
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
            }
            else{
                //System.out.println("---------------------"+idHorno+" NO LISTO, SERVICIO DENEGADO----------------------------------------------");
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } finally {
            semaforo.release();
        }
        return nGalletasDesperdiciadas;
    }

    public void hornearGalletas() {
        try {
            semaforo.acquire();
            horneando=true;
            listoParaDepositar=false;
            estado="Horneando";
            System.out.println(idHorno + " Empieza a hornear");
            int duracion=8000;
            int pasos = 100;     // Número de actualizaciones de la barra
            int duracionPaso = duracion / pasos; // Duración de cada paso

            for (int i = 0; i <= pasos; i++) {
                progresoHorneado=i;
                Thread.sleep(duracionPaso);
            }
            System.out.println(idHorno + " termina de hornear");
            horneando=false;
            nGalletasHorneadas+=capacidad;
            progresoHorneado=0;
            estado="inactivo";
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
            listoParaDepositar=false;
            if (nGalletasDentro - 20 >= 0) {
                sleep(500 + random.nextInt(500));
                nGalletasDentro -= 20;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getProgresoHorneado() {
        return progresoHorneado;
    }

    public void setProgresoHorneado(int progresoHorneado) {
        this.progresoHorneado = progresoHorneado;
    }

    public int getnGalletasHorneadas() {
        return nGalletasHorneadas;
    }

    public boolean isHorneando() {
        return horneando;
    }
    
    
}

package Server;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Repostero extends Thread implements Serializable{
    private String idRepostero;
    private int numeroDeTandas;
    private List<Horno> listaHornos;
    private boolean esperaCafe;
    private String estado;
    private int nGalletasGeneradasTotales;
    private int nGalletasDesperdiciadasTotales;
    private Cafetera cafetera;
    private Random random = new Random();
    private boolean paused = false; // Flag para la pausa
    private ReentrantLock lock= new ReentrantLock();
    public Repostero(String idRepostero, List<Horno> listaHornos, Cafetera cafetera) {
        this.idRepostero = idRepostero;
        this.listaHornos = listaHornos;
        this.numeroDeTandas = 0;
        this.cafetera = cafetera;
        this.esperaCafe = false;
        this.estado = "inactivo";
        this.nGalletasGeneradasTotales = 0;
        this.nGalletasDesperdiciadasTotales = 0;
    }

    public int producirTandaGalletas() throws InterruptedException {
        sleep(2000 + random.nextInt(2000));
        int numeroAleatorio = 37 + random.nextInt(45 - 37 + 1);
        numeroDeTandas++;
        return numeroAleatorio;
    }

    public Horno buscarHorno() {
        for (Horno h : listaHornos) {
            if (h.isListoParaDepositar()) {
                return h;
            }
        }
        return null;
    }

    @Override
    public void run() {
        while (true) {
            try {
                boolean ultimaTanda = false;
                numeroDeTandas = 0;
                checkIfPaused();
                while (!ultimaTanda) {
                    checkIfPaused();
                    estado = "produciendo" + "(" + numeroDeTandas + "/" + 5 + ")";
                    checkIfPaused();
                    Horno horno = buscarHorno();
                    checkIfPaused();
                    if (horno != null && horno.isListoParaDepositar()) {
                        checkIfPaused();
                        int nGalletas = producirTandaGalletas();
                        nGalletasGeneradasTotales += nGalletas;
                        checkIfPaused();
                        int nGalletasDesperdiciadas = horno.depositarGalletas(nGalletas, this);
                        nGalletasDesperdiciadasTotales += nGalletasDesperdiciadas;
                        if (numeroDeTandas >= 3) {
                            ultimaTanda = (Math.random() < 0.5); // 50% de que sea la última tanda
                        }
                        if (numeroDeTandas >= 5) {
                            ultimaTanda = true; // Si ya pasaron 5 tandas, es la última
                        }
                    }
                }
                checkIfPaused();
                esperaCafe = true;
                estado = "pausa para el café";
                checkIfPaused();
                cafetera.empezarCafe(this);
                System.out.println(idRepostero + " empieza a hacer café");
                checkIfPaused();
                sleep(2000);
                checkIfPaused();
                cafetera.terminarCafe();
                System.out.println(idRepostero + " termina de hacer café");
                esperaCafe = false;
                checkIfPaused();
                estado = "descanso";
                System.out.println(idRepostero + " empieza a descansar");
                sleep(3000 + random.nextInt(3000));
                checkIfPaused();
                System.out.println(idRepostero + " termina de descansar");

            } catch (InterruptedException e) {
                System.out.println("Hilo interrumpido: " + e.getMessage());
            }
        }
    }

    public synchronized void setPaused(boolean paused) {
        this.paused = paused;
        String estadoAnterior=estado;
        // Cambiar el estado del repostero a "pausado" si se está pausando
        if (paused) {
            estado = "pausado";
        } else {
            // Si se reanuda, actualizar el estado a "activo" o el valor correspondiente
            estado = estadoAnterior;  // O el estado que prefieras cuando se reanuda
            lock.unlock();    // Notificar que se ha reanudado el repostero
        }
    }


    // Método para verificar si está pausado y esperar si es necesario
    private synchronized void checkIfPaused() throws InterruptedException {
        if (paused) {
                    lock.lock();  // Bloquea el hilo si está pausado
                    // Mientras está bloqueado, el repostero espera
                }
    }

    public String getIdRepostero() {
        return idRepostero;
    }

    public boolean isEsperaCafe() {
        return esperaCafe;
    }

    public String getEstado() {
        return estado;
    }

    public int getnGalletasGeneradasTotales() {
        return nGalletasGeneradasTotales;
    }

    public int getnGalletasDesperdiciadasTotales() {
        return nGalletasDesperdiciadasTotales;
    }

    public boolean isPaused() {
        return paused;
    }
    
}

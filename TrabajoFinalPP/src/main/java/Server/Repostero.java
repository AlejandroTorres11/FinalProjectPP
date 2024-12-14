package Server;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Repostero extends Thread implements Serializable {
    private String idRepostero;
    private int numeroDeTandas;
    private List<Horno> listaHornos;
    private boolean esperaCafe;
    private String estado;
    private int nGalletasGeneradasTotales;
    private int nGalletasDesperdiciadasTotales;
    private Cafetera cafetera;
    private Random random = new Random();
    private boolean paused = false; // Flag para pausar el hilo

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
    private String obtenerFechaHoraActual() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + ahora.format(formato) + "] ";
    }
    public int producirTandaGalletas() throws InterruptedException {
        checkPaused(); // Verificar si el hilo está pausado
        sleep(2000 + random.nextInt(2000));
        checkPaused(); // Verificar de nuevo después de dormir
        int numeroAleatorio = 37 + random.nextInt(45 - 37 + 1);
        numeroDeTandas++;
        return numeroAleatorio;
    }

    public Horno buscarHorno() {
        checkPaused(); // Verificar si el hilo está pausado antes de buscar un horno
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
                // Verificar si el hilo está pausado en cada iteración
                checkPaused();

                boolean ultimaTanda = false;
                numeroDeTandas = 0;
                while (!ultimaTanda) {
                    checkPaused(); // Verificar si el hilo está pausado antes de producir
                    estado = "produciendo" + "(" + numeroDeTandas + "/" + 5 + ")";
                    Horno horno = buscarHorno();
                    if (horno != null && horno.isListoParaDepositar()) {
                        int nGalletas = producirTandaGalletas();
                        nGalletasGeneradasTotales += nGalletas;
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
                esperaCafe = true;
                estado = "pausa para el café";
                cafetera.empezarCafe(this);
                System.out.println(obtenerFechaHoraActual()+" "+idRepostero + " empieza a hacer café");
                sleep(2000);

                cafetera.terminarCafe();
                System.out.println(obtenerFechaHoraActual()+" "+idRepostero + " termina de hacer café");
                esperaCafe = false;
                estado = "descanso";
                System.out.println(obtenerFechaHoraActual()+" "+idRepostero + " empieza a descansar");
                sleep(3000 + random.nextInt(3000));
                System.out.println(obtenerFechaHoraActual()+" "+idRepostero + " termina de descansar");

            } catch (InterruptedException e) {
                System.out.println("Hilo interrumpido: " + e.getMessage());
            }
        }
    }

    
    public synchronized void pauseThread() {
        paused = true;
        estado = "pausado";
        System.out.println(obtenerFechaHoraActual()+ idRepostero+ " Ha sido pausado");
    }

    public synchronized void resumeThread() {
        paused = false;
        estado = "activo"; // O un estado adecuado como "produciendo"
        notify(); // Notificar que el hilo puede continuar
        System.out.println(obtenerFechaHoraActual()+ idRepostero+ " Ha sido reanudado");
        estado = "activo"; 
        notify(); 
    }

    // Método para verificar si el hilo está pausado
    private synchronized void checkPaused() {
        while (paused) {
            try {
                wait(); // Espera hasta que se llame a resumeThread()
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restaurar el estado interrumpido
                System.out.println("Hilo interrumpido durante pausa: " + e.getMessage());
            }
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
}

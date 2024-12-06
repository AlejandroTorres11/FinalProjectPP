/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Server;

import Common.InfoAlmacen;
import Common.InfoHorno;
import Common.InfoRepostero;
import Common.ObjetoRemotoImpl;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class Servidor {
    private static List<Horno> listaHornos = Collections.synchronizedList(new LinkedList<>());
    private static List<Repostero> listaReposteros = Collections.synchronizedList(new LinkedList<>());
    private static List<Empaquetador> listaEmpaquetadores = Collections.synchronizedList(new LinkedList<>());
    private static Cafetera cafetera = new Cafetera();
    private static Almacen almacen = new Almacen(1000);
    private static MainFrame mainFrame;
    
    public static void main(String[] args) {
        inicializarComponentes();
        iniciarVentanaPrincipal();
        iniciarRMI();
    }

    private static void inicializarComponentes() {
        
        for (int i = 0; i < 3; i++) {
            Horno horno = new Horno("horno" + (i + 1), 200);
            Empaquetador empaquetador = new Empaquetador("empaquetador" + (i + 1), horno, almacen);
            listaHornos.add(horno);
            listaEmpaquetadores.add(empaquetador);
        }
        
        for (int i = 0; i < 5; i++) {
            Repostero repostero = new Repostero("repostero" + (i + 1), listaHornos, cafetera);
            listaReposteros.add(repostero);
        }
    }

    private static void iniciarVentanaPrincipal() {
        mainFrame = new MainFrame(almacen, cafetera, listaHornos, listaReposteros, listaEmpaquetadores);
        mainFrame.setVisible(true);
    
        Thread hiloVentana = new Thread(mainFrame); // Asegúrate de que MainFrame implemente Runnable
        hiloVentana.start();
    }
    
    private static void iniciarRMI() {
        try {
            LocateRegistry.createRegistry(1099);
            ObjetoRemotoImpl objetoRemoto = new ObjetoRemotoImpl(listaReposteros, listaHornos, almacen);
            Naming.rebind("rmi://localhost:1099/ObjetoRMI", objetoRemoto);
            System.out.println("Servidor RMI está listo.");
        } catch (Exception e) {
            System.err.println("Error al iniciar el servidor RMI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.trabajofinalpp;

import com.mycompany.trabajofinalpp.common.ObjetoRemotoImpl;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      
        List<Horno> listaHornos= Collections.synchronizedList(new LinkedList<>());
        List<Repostero> listaReposteros= Collections.synchronizedList(new LinkedList<>());
        List<Empaquetador> listaEmpaquetadores= Collections.synchronizedList(new LinkedList<>());
        
        Almacen almacen= new Almacen(1000);
        for (int i = 0; i < 3; i++) {
            Horno horno= new Horno("horno"+(i+1), 200);
            Empaquetador empaquetador= new Empaquetador("empaquetador"+(i+1), horno, almacen);
            listaHornos.add(horno);
            listaEmpaquetadores.add(empaquetador);
        }
        Cafetera cafetera= new Cafetera();
        for (int i = 0; i < 5; i++) {
            Repostero repostero= new Repostero("repostero"+(i+1), listaHornos, cafetera);
            listaReposteros.add(repostero);
        }
        MainFrame mainFrame= new MainFrame(almacen, cafetera, listaHornos, listaReposteros, listaEmpaquetadores);
        mainFrame.setVisible(true);
        Thread hiloMainFrameThread= new Thread(mainFrame);
        hiloMainFrameThread.start();
        
        try {
                // Iniciar el registro RMI
                LocateRegistry.createRegistry(1099);

                // Crear el objeto remoto y publicarlo
                ObjetoRemotoImpl objetoRemoto = new ObjetoRemotoImpl(mainFrame);
                Naming.rebind("rmi://localhost:1099/ObjetoRMI", objetoRemoto);

                System.out.println("Servidor RMI está listo.");
            } catch (Exception e) {
                System.err.println("Error al iniciar el servidor RMI: " + e.getMessage());
                e.printStackTrace();
            }
    }
        
    
}

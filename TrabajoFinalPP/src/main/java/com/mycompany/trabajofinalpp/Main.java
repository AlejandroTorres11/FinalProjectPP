/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.trabajofinalpp;

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
        // TODO code application logic here
        List<Horno> hornosVacios = Collections.synchronizedList(new LinkedList<>());
        List<Horno> hornosLlenos = Collections.synchronizedList(new LinkedList<>());
        List<Horno> listaHornos= Collections.synchronizedList(new LinkedList<>());
        List<Repostero> listaReposteros= Collections.synchronizedList(new LinkedList<>());
        List<Empaquetador> listaEmpaquetadores= Collections.synchronizedList(new LinkedList<>());
        
        Almacen almacen= new Almacen(1000);
        for (int i = 0; i < 5; i++) {
            Horno horno= new Horno("horno"+(i+1), 200);
            Empaquetador empaquetador= new Empaquetador("empaquetador"+(i+1), hornosVacios, hornosLlenos, horno, almacen);
            hornosVacios.add(horno);
            listaHornos.add(horno);
            horno.start();
            empaquetador.start();
        }
        Cafetera cafetera= new Cafetera();
        Repostero repostero1= new Repostero("repostero1", hornosVacios,hornosLlenos, cafetera);
        Repostero repostero2= new Repostero("repostero2", hornosVacios,hornosLlenos, cafetera);

        repostero1.start();
        repostero2.start();
    }
    
}

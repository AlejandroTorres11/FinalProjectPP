/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.trabajofinalpp;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author aleja
 */
public class TrabajoFinalPP {

    public static void main(String[] args) {
        List<Horno> hornosVacios = Collections.synchronizedList(new LinkedList<>());
        List<Horno> hornosLlenos = Collections.synchronizedList(new LinkedList<>());
        Almacen almacen= new Almacen(1000);
        for (int i = 0; i < 5; i++) {
            Horno horno= new Horno("horno"+(i+1), 200);
            Empaquetador empaquetador= new Empaquetador("empaquetador"+(i+1), hornosVacios, hornosLlenos, horno, almacen);
            hornosVacios.add(horno);
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.trabajofinalpp;

/**
 *
 * @author aleja
 */
public class TrabajoFinalPP {

    public static void main(String[] args) {
        Horno horno1= new Horno("horno1");
        Cafetera cafetera= new Cafetera();
        Repostero repostero1= new Repostero("repostero1", horno1, cafetera);
        repostero1.start();
    }
}

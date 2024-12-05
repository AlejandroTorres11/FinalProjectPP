package com.mycompany.trabajofinalpp.Cliente;

import com.mycompany.trabajofinalpp.common.InterfazObjetoRemoto;
import java.rmi.Naming;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author alejandro
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Conectar al objeto remoto
            InterfazObjetoRemoto objetoRemoto= (InterfazObjetoRemoto) Naming.lookup("rmi://localhost:1099/ObjetoRMI");
            ClienteUI clienteUI= new ClienteUI(objetoRemoto);
            // Llamar a los métodos remotos

        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Common;

import java.io.Serializable;

// Definimos el record Producto como Serializable para usarlo en RMI
public record InfoRepostero(String idRepostero ,int nGalletasGeneradas,int nGalletasDesperdiciadas) implements Serializable {}
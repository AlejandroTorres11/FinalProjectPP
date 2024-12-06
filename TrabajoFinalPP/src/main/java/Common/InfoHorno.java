/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package Common;

import java.io.Serializable;

/**
 *
 * @author alejandro
 */
public record InfoHorno(String idHorno,int galletasHorneadas,boolean horneando) implements Serializable{}

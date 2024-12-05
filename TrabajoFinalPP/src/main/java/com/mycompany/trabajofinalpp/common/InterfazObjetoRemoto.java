/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.trabajofinalpp.common;
import com.mycompany.trabajofinalpp.Repostero;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InterfazObjetoRemoto extends Remote {
    List<InfoRepostero> getInfoReposteros() throws RemoteException;
    List<InfoHorno> getInfoHornos() throws RemoteException;
    InfoAlmacen getInfoAlmacen() throws RemoteException;
    public void pararRepostero(Repostero r) throws RemoteException;
    public void reanudarRepostero(Repostero r) throws RemoteException;
}

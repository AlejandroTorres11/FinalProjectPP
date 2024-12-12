
package Common;

import Server.Almacen;
import Server.Horno;
import Server.Repostero;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class ObjetoRemotoImpl extends UnicastRemoteObject implements InterfazObjetoRemoto {
    private List<Repostero> listaReposteros;
    private List<Horno> listaHornos;
    private Almacen almacen;
    
    public ObjetoRemotoImpl( List<Repostero> listaReposteros, List<Horno> listaHornos, Almacen almacen ) throws RemoteException{
        super();
        this.listaReposteros = listaReposteros;
        this.listaHornos = listaHornos;
        this.almacen = almacen;
    }
    
    @Override
    public List<Repostero> getInfoReposteros() throws RemoteException {
        return listaReposteros;
    }

    @Override
    public List<InfoHorno> getInfoHornos() throws RemoteException {
        List<InfoHorno> infoHornos = new ArrayList<>();
        for (Horno horno : listaHornos) {
            InfoHorno info = new InfoHorno(horno.getIdHorno(),horno.getnGalletasHorneadas(), horno.isHorneando());
            infoHornos.add(info);
        }
        return infoHornos;
    }

    @Override
    public InfoAlmacen getInfoAlmacen() throws RemoteException {
        return new InfoAlmacen( almacen.getnGalletasDentro(), almacen.getnGalletasComidas());
    }

    @Override
    public void pararRepostero(int pos) throws RemoteException {
        listaReposteros.get(pos).pauseThread();
    }

    @Override
    public void reanudarRepostero(int pos) throws RemoteException {
        listaReposteros.get(pos).resumeThread();
    }
    
}

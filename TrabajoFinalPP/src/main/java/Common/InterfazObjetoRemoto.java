
package Common;
import Server.Repostero;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InterfazObjetoRemoto extends Remote {
    List<Repostero> getInfoReposteros() throws RemoteException;
    List<InfoHorno> getInfoHornos() throws RemoteException;
    InfoAlmacen getInfoAlmacen() throws RemoteException;
    public void pararRepostero(int pos) throws RemoteException;
    public void reanudarRepostero(int pos) throws RemoteException;
}

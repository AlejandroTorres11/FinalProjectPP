
package Common;

import java.io.Serializable;

// Definimos el record Producto como Serializable para usarlo en RMI
public record InfoRepostero(String idRepostero ,int nGalletasGeneradas,int nGalletasDesperdiciadas) implements Serializable {}
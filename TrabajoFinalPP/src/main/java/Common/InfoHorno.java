
package Common;

import java.io.Serializable;

/**
 *
 * @author alejandro
 */
public record InfoHorno(String idHorno,int galletasHorneadas,boolean horneando) implements Serializable{}

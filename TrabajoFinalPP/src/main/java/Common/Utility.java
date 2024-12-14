
package Common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author alejandro
 */
public class Utility {
    private Utility() {
    }
    public static String obtenerFechaHoraActual() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + ahora.format(formato) + "] ";
    }
}

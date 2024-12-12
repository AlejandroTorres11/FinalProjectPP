package Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static String obtenerFechaHoraActual() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + ahora.format(formato) + "] ";
    }

    public static void redirigirSalida(String rutaArchivo) {
        try {
            // Crear un archivo de salida en modo "append"
            FileOutputStream archivoSalida = new FileOutputStream(rutaArchivo, true);

            // Crear una instancia de TeeOutputStream
            TeeOutputStream teeStream = new TeeOutputStream(System.out, archivoSalida);

            // Crear un PrintStream que use el TeeOutputStream
            PrintStream salidaDuplicada = new PrintStream(teeStream, true);

            // Redirigir System.out y System.err a la nueva salida duplicada
            System.setOut(salidaDuplicada);
            System.setErr(salidaDuplicada);

            // Mensaje inicial
            System.out.println(obtenerFechaHoraActual() + "Salida redirigida a consola y archivo: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println(obtenerFechaHoraActual() + "Error: No se pudo redirigir la salida a " + rutaArchivo);
            e.printStackTrace();
        }
    }
}


class TeeOutputStream extends OutputStream {
    private final OutputStream original;
    private final OutputStream archivo;

    public TeeOutputStream(OutputStream original, OutputStream archivo) {
        this.original = original;
        this.archivo = archivo;
    }

    @Override
    public void write(int b) throws IOException {
        original.write(b); // Escribir en la consola
        archivo.write(b);  // Escribir en el archivo
    }

    @Override
    public void flush() throws IOException {
        original.flush();
        archivo.flush();
    }

    @Override
    public void close() throws IOException {
        original.close();
        archivo.close();
    }
}

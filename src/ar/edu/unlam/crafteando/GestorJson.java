package ar.edu.unlam.crafteando;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Clase utilitaria para gestionar la lectura y escritura de archivos JSON.
 */
public class GestorJson {
    private static final Gson gson = new Gson();

    /**
     * Guarda un objeto como archivo JSON.
     *
     * @param objeto El objeto a guardar
     * @param rutaCompleta Ruta donde se guardará el archivo (puede incluir carpetas)
     * @return true si se guardó correctamente, false en caso de error
     */
    public static boolean guardar(Object objeto, String rutaCompleta) {
        Path ruta = Paths.get(rutaCompleta);
        try {
            Files.createDirectories(ruta.getParent()); // crea carpetas si hace falta
            try (FileWriter writer = new FileWriter(ruta.toFile())) {
                gson.toJson(objeto, writer);
                return true;
            }
        } catch (IOException e) {
            System.err.println("Error al guardar JSON: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lee un archivo JSON y lo convierte en un objeto del tipo especificado.
     *
     * @param rutaCompleta Ruta del archivo JSON
     * @param clase Clase del tipo a deserializar
     * @param <T> Tipo genérico
     * @return El objeto leido, o null si falló
     */
    public static <T> T leer(String rutaCompleta, Class<T> clase) {
        Path ruta = Paths.get(rutaCompleta);
        try (FileReader reader = new FileReader(ruta.toFile())) {
            return gson.fromJson(reader, clase);
        } catch (IOException e) {
            System.err.println("Error al leer JSON: " + e.getMessage());
            return null;
        }
    }
}

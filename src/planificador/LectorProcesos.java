package planificador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorProcesos {

    public static List<Proceso> leerFichero(String ruta) throws IOException, IllegalArgumentException {
        List<Proceso> procesos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int numLinea = 0;

            while ((linea = br.readLine()) != null) {
                numLinea++;
                linea = linea.trim();

                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }

                String[] partes = linea.split(";");
                if (partes.length != 3) {
                    throw new IllegalArgumentException("Línea " + numLinea + " mal formada: " + linea);
                }

                String nombre = partes[0].trim();
                int llegada;
                int rafaga;

                try {
                    llegada = Integer.parseInt(partes[1].trim());
                    rafaga = Integer.parseInt(partes[2].trim());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Línea " + numLinea + ": llegada y ráfaga deben ser números enteros.");
                }

                if (llegada < 0) {
                    throw new IllegalArgumentException("Línea " + numLinea + ": tiempo de llegada negativo (" + llegada + ").");
                }
                if (rafaga <= 0) {
                    throw new IllegalArgumentException("Línea " + numLinea + ": ráfaga debe ser mayor que cero (" + rafaga + ").");
                }

                procesos.add(new Proceso(nombre, llegada, rafaga));
            }
        }

        if (procesos.isEmpty()) {
            throw new IllegalArgumentException("El fichero de datos no contiene procesos válidos.");
        }

        return procesos;
    }
}
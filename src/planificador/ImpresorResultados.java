package planificador;

import java.util.List;

public class ImpresorResultados {

    public static void imprimir(String nombreAlgoritmo, List<String> gantt, List<Proceso> procesos,
                                int cambiosContexto, List<String> traza, boolean mostrarTraza) {
        System.out.println("=== " + nombreAlgoritmo + " ===");

        System.out.print("t\t");
        for (int i = 0; i < gantt.size(); i++) {
            System.out.printf("%-3d", i);
        }
        System.out.println();

        System.out.print("CPU\t");
        for (String celda : gantt) {
            System.out.printf("%-3s", celda);
        }
        System.out.println("\n");

        System.out.printf("%-10s %-8s %-8s %-6s %-8s %-8s %-10s\n",
                "Proceso", "Llegada", "Ráfaga", "Fin", "Retorno", "Espera", "Respuesta");

        double sumRetorno = 0;
        double sumEspera = 0;
        double sumRespuesta = 0;

        for (Proceso p : procesos) {
            System.out.printf("%-10s %-8d %-8d %-6d %-8d %-8d %-10d\n",
                    p.getNombre(), p.getLlegada(), p.getRafaga(),
                    p.getFin(), p.getRetorno(), p.getEspera(), p.getTiempoRespuesta());

            sumRetorno += p.getRetorno();
            sumEspera += p.getEspera();
            sumRespuesta += p.getTiempoRespuesta();
        }

        int n = procesos.size();
        System.out.printf("\nMedias: retorno %.2f  espera %.2f  respuesta %.2f\n",
                sumRetorno / n, sumEspera / n, sumRespuesta / n);

        System.out.println("Cambios de contexto: " + cambiosContexto);

        if (mostrarTraza) {
            System.out.println("\nTraza de estados:");
            for (String paso : traza) {
                System.out.println(paso);
            }
        }
        System.out.println();
    }
}
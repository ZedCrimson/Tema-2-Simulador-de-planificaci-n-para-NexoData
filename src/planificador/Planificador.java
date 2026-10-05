package planificador;

import java.util.List;

public interface Planificador {
    void simular(List<Proceso> procesosOriginales, boolean mostrarTraza);
}
package planificador;

public class FCFS extends AbstractPlanificador {

    @Override
    protected void insertarEnListo(Proceso p) {
        colaListos.add(p);
    }

    @Override
    protected Proceso seleccionarSiguiente() {
        return colaListos.get(0);
    }

    @Override
    protected boolean debeDesalojar(int quantumActual) {
        return false;
    }

    @Override
    protected void mostrarResultados(boolean mostrarTraza) {
        ImpresorResultados.imprimir("FCFS", diagramagantt, procesos, cambiosContexto, trazaEstados, mostrarTraza);
    }
}
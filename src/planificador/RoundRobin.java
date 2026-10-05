package planificador;

public class RoundRobin extends AbstractPlanificador {
    private final int quantum;

    public RoundRobin(int quantum) {
        this.quantum = quantum;
    }

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
        return quantumActual >= quantum;
    }

    @Override
    protected void mostrarResultados(boolean mostrarTraza) {
        ImpresorResultados.imprimir("Round Robin (q=" + quantum + ")", diagramagantt, procesos, cambiosContexto, trazaEstados, mostrarTraza);
    }
}
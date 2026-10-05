package planificador;

public class SJF extends AbstractPlanificador {

    @Override
    protected void insertarEnListo(Proceso p) {
        colaListos.add(p);
    }

    @Override
    protected Proceso seleccionarSiguiente() {
        Proceso seleccionado = colaListos.get(0);
        for (int i = 1; i < colaListos.size(); i++) {
            Proceso p = colaListos.get(i);
            if (p.getRafaga() < seleccionado.getRafaga()) {
                seleccionado = p;
            }
        }
        return seleccionado;
    }

    @Override
    protected boolean debeDesalojar(int quantumActual) {
        return false;
    }

    @Override
    protected void mostrarResultados(boolean mostrarTraza) {
        ImpresorResultados.imprimir("SJF", diagramagantt, procesos, cambiosContexto, trazaEstados, mostrarTraza);
    }
}
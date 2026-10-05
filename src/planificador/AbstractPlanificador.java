package planificador;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPlanificador implements Planificador {
    protected List<Proceso> procesos;
    protected List<Proceso> colaListos = new ArrayList<>();
    protected List<String> diagramagantt = new ArrayList<>();
    protected List<String> trazaEstados = new ArrayList<>();
    protected int cambiosContexto = 0;
    protected Proceso ultimoEjecutado = null;

    @Override
    public void simular(List<Proceso> procesosOriginales, boolean mostrarTraza) {
        this.procesos = new ArrayList<>();
        for (Proceso p : procesosOriginales) {
            this.procesos.add(new Proceso(p));
        }

        colaListos.clear();
        diagramagantt.clear();
        trazaEstados.clear();
        cambiosContexto = 0;
        ultimoEjecutado = null;

        int t = 0;
        Proceso cpu = null;
        int quantumActual = 0;
        int totalProcesos = procesos.size();
        int terminados = 0;

        while (terminados < totalProcesos) {
            for (Proceso p : procesos) {
                if (p.getLlegada() == t && p.getEstado() == EstadoProceso.NUEVO) {
                    p.setEstado(EstadoProceso.LISTO);
                    registrarTraza(t, p, EstadoProceso.NUEVO, EstadoProceso.LISTO, "llega al sistema");
                    insertarEnListo(p);
                }
            }

            boolean desaloja = false;
            if (cpu != null) {
                quantumActual++;
                if (cpu.getTiempoRestante() == 0) {
                    cpu.setEstado(EstadoProceso.TERMINADO);
                    cpu.setFin(t);
                    registrarTraza(t, cpu, EstadoProceso.EJECUCION, EstadoProceso.TERMINADO, "completa su rafaga");
                    terminados++;
                    cpu = null;
                    quantumActual = 0;
                } else if (debeDesalojar(quantumActual)) {
                    desaloja = true;
                }
            }

            if (desaloja && cpu != null) {
                if (colaListos.isEmpty()) {
                    quantumActual = 0;
                } else {
                    cpu.setEstado(EstadoProceso.LISTO);
                    registrarTraza(t, cpu, EstadoProceso.EJECUCION, EstadoProceso.LISTO, "agota el quantum");
                    insertarEnListo(cpu);
                    cpu = null;
                    quantumActual = 0;
                }
            }

            if (cpu == null && !colaListos.isEmpty()) {
                cpu = seleccionarSiguiente();
                colaListos.remove(cpu);

                if (ultimoEjecutado != null && ultimoEjecutado != cpu) {
                    cambiosContexto++;
                }
                ultimoEjecutado = cpu;

                if (cpu.getTiempoRespuesta() == -1) {
                    cpu.setTiempoRespuesta(t - cpu.getLlegada());
                }

                cpu.setEstado(EstadoProceso.EJECUCION);
                registrarTraza(t, cpu, EstadoProceso.LISTO, EstadoProceso.EJECUCION, "el planificador lo elige");
                quantumActual = 0;
            } else if (cpu == null && colaListos.isEmpty() && ultimoEjecutado != null) {
                ultimoEjecutado = null;
            }

            if (cpu != null) {
                diagramagantt.add(cpu.getNombre());
                cpu.setTiempoRestante(cpu.getTiempoRestante() - 1);
            } else {
                diagramagantt.add("-");
            }

            t++;
        }

        if (cpu != null && cpu.getTiempoRestante() == 0) {
            cpu.setEstado(EstadoProceso.TERMINADO);
            cpu.setFin(t);
            registrarTraza(t, cpu, EstadoProceso.EJECUCION, EstadoProceso.TERMINADO, "completa su rafaga");
        }

        mostrarResultados(mostrarTraza);
    }

    protected abstract void insertarEnListo(Proceso p);
    protected abstract Proceso seleccionarSiguiente();
    protected abstract boolean debeDesalojar(int quantumActual);

    protected void registrarTraza(int t, Proceso p, EstadoProceso origen, EstadoProceso destino, String motivo) {
        trazaEstados.add(String.format("t=%d %s %s -> %s (%s)", t, p.getNombre(), origen, destino, motivo));
    }

    protected abstract void mostrarResultados(boolean mostrarTraza);
}
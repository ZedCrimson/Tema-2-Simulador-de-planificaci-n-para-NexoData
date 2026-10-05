package planificador;

public class Proceso {
    private final String nombre;
    private final int llegada;
    private final int rafaga;
    private int tiempoRestante;
    private EstadoProceso estado;

    private int fin;
    private int tiempoRespuesta = -1;

    public Proceso(String nombre, int llegada, int rafaga) {
        this.nombre = nombre;
        this.llegada = llegada;
        this.rafaga = rafaga;
        this.tiempoRestante = rafaga;
        this.estado = EstadoProceso.NUEVO;
    }


    public Proceso(Proceso p) {
        this.nombre = p.nombre;
        this.llegada = p.llegada;
        this.rafaga = p.rafaga;
        this.tiempoRestante = p.rafaga;
        this.estado = EstadoProceso.NUEVO;
    }

    public String getNombre() { return nombre; }
    public int getLlegada() { return llegada; }
    public int getRafaga() { return rafaga; }
    public int getTiempoRestante() { return tiempoRestante; }
    public void setTiempoRestante(int tiempoRestante) { this.tiempoRestante = tiempoRestante; }
    public EstadoProceso getEstado() { return estado; }
    public void setEstado(EstadoProceso estado) { this.estado = estado; }
    public int getFin() { return fin; }
    public void setFin(int fin) { this.fin = fin; }
    public int getTiempoRespuesta() { return tiempoRespuesta; }
    public void setTiempoRespuesta(int tiempoRespuesta) { this.tiempoRespuesta = tiempoRespuesta; }

    public int getRetorno() { return fin - llegada; }
    public int getEspera() { return getRetorno() - rafaga; }
}
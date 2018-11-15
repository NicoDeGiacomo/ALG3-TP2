package main;

import excepciones.main.OroInsuficienteException;
import excepciones.unidades.CreacionDeCastilloException;
import unidades.Unidad;
import unidades.edificio.Castillo;

import java.util.LinkedList;
import java.util.List;

public class Jugador {

    private String nombre;
    private List<Unidad> unidades;
    private int oro;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.unidades = new LinkedList<>();
    }

    boolean tieneComoNombre(String nombre) {
        return this.nombre.equals(nombre);
    }

    void comenzarNuevoTurno() {
        for (Unidad unidad : this.unidades) {
            unidad.ejecutarTareas();
        }
    }

    public void recolectarOro(int oro) {
        this.oro += oro;
    }

    public void cobrarOro(int oro) throws OroInsuficienteException {
        if (this.oro < oro)
            throw new OroInsuficienteException("El precio del jugador es insuficiente.");
        this.oro -= oro;
    }

    public void agregarUnidad(Unidad unidad) throws OroInsuficienteException {
        unidad.cobrarCostoDeCreacion();
        this.unidades.add(unidad);
    }

    void agregarCastillo(Castillo castillo) {
        this.unidades.add(castillo);
    }

    public void devolverCostoDeUnidad(int precio, Unidad unidad) {
        this.unidades.remove(unidad);
        this.oro += precio;
    }
}

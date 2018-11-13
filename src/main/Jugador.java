package main;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.EspacioInsuficienteException;
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
            throw new OroInsuficienteException("El oro del jugador es insuficiente.");
        this.oro -= oro;
    }

    public void agregarUnidad(Unidad unidad, Unidad creador) throws OroInsuficienteException, EspacioInsuficienteException {
        unidad.cobrarCostoDeCreacion();
        this.unidades.add(unidad);
    }

    void agregarCastillo(Castillo castillo) {
        this.unidades.add(castillo);
    }
}

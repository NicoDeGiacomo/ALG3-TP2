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
    private Mapa mapa;

    public Jugador(String nombre, Mapa mapa) {
        this.nombre = nombre;
        this.mapa = mapa;
        this.unidades = new LinkedList<>();

        Castillo castillo = new Castillo(this);
        this.unidades.add(castillo);
        this.mapa.colocarUnidadEnExtremo(castillo);
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

        this.mapa.agregarUnidadCercana(unidad, creador);

        try {
            unidad.cobrarCostoDeCreacion();
        } catch (OroInsuficienteException e) {
            this.mapa.quitarUnidad(unidad); //Remuevo la unidad del mapa si no hubo oro suficiente.
            throw e;
        }

        this.unidades.add(unidad);
    }
}

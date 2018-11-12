package main;

import excepciones.main.OroInsuficienteException;
import unidades.Unidad;
import java.util.LinkedList;
import java.util.List;

public class Jugador {

    private String nombre;
    private List<Unidad> unidades;
    private int oro;

    Jugador(String nombre) {
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

    void recolectarOro(int oro) {
        this.oro += oro;
    }

    void cobrarOro(int oro) throws OroInsuficienteException {
        if (this.oro < oro)
            throw new OroInsuficienteException("El oro del jugador es insuficiente");
        this.oro -= 20;
    }

    void agregarUnidad(Unidad unidad) {
        //unidad.cobrarCostoDeCreacion(this.oro); ???
        unidad.cobrarCostoDeCreacion();
        this.unidades.add(unidad);
    }
}

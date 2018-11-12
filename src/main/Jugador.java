package main;

import excepciones.main.OroInsuficienteException;
import unidades.Unidad;
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
            throw new OroInsuficienteException("El oro del jugador es insuficiente");
        this.oro -= 20;
    }

    public void agregarUnidad(Unidad unidad) throws OroInsuficienteException {
        //unidad.cobrarCostoDeCreacion(this.oro); ???

        //Agregar unidad al mapa --> Va a romper si no hay espacio

        unidad.cobrarCostoDeCreacion(); //Si no hay oro suficiente -> Liberar espacio en el mapa
        this.unidades.add(unidad);
    }
}

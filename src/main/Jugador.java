package main;

import excepciones.main.OroInsuficienteException;
import excepciones.unidades.CreacionDeCastilloException;
import excepciones.unidades.UnidadNoAgregadaException;
import unidades.Unidad;
import unidades.edificio.Castillo;
import unidades.edificio.PlazaCentral;
import unidades.milicia.Aldeano;

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

    public void agregarUnidad(Unidad unidad) throws OroInsuficienteException {
        unidad.cobrarCostoDeCreacion();
        this.unidades.add(unidad);
    }

    void agregarUnidadesIniciales(Castillo castillo, PlazaCentral plazaCentral, List<Aldeano> aldeanos) {
        this.unidades.add(castillo);
        this.unidades.add(plazaCentral);
        this.unidades.addAll(aldeanos);
    }

    public void devolverCostoDeUnidad(int precio, Unidad unidad) throws UnidadNoAgregadaException {
        if (!this.unidades.contains(unidad))
            throw new UnidadNoAgregadaException("No se puede devolver el costo de una unidad que no le pertenece al jugador");
        this.unidades.remove(unidad);
        this.oro += precio;
    }
}

package main;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.edificio.Castillo;
import unidades.edificio.Edificio;
import unidades.edificio.PlazaCentral;
import unidades.estados.aldeano.Ocioso;
import unidades.milicia.Aldeano;
import unidades.milicia.Milicia;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Jugador {

    private static final int CANTIDAD_ALDEANOS_INICIALES = 3;
    private static final int CANTIDAD_MAX_POBLACION = 50;

    private String nombre;
    private List<Unidad> unidades;
    private int oro;
    private Mapa mapa;
    private int poblacion;

    public Jugador(String nombre, Mapa mapa) {
        this.nombre = nombre;
        this.unidades = new LinkedList<>();
        this.mapa = mapa;

        this.oro = 100;
        this.poblacion = CANTIDAD_ALDEANOS_INICIALES;

        Castillo castillo = new Castillo(this);
        PlazaCentral plazaCentral = new PlazaCentral(this);
        this.unidades.add(castillo);
        this.unidades.add(plazaCentral);
        this.mapa.colocarUnidadesEnExtremo(castillo, plazaCentral);

        for (int i = 0; i < CANTIDAD_ALDEANOS_INICIALES; i++) {
            Aldeano aldeano = new Aldeano(this);
            try {
                this.mapa.agregarUnidadCercana(aldeano, plazaCentral);
            } catch (CoordenadaInvalidaException ignore) {
            }
            this.unidades.add(aldeano);
        }
    }

    boolean tieneComoNombre(String nombre) {
        return this.nombre.equals(nombre);
    }

    void comenzarNuevoTurno() {
        for (Unidad unidad : this.unidades) {
            unidad.ejecutarTareas();
        }
    }

    public void recolectarOro(Ocioso estadoDeAldeano) {
        this.oro += estadoDeAldeano.obtenerRecollecion();
    }

    public void agregarUnidad(Milicia unidad, Edificio creador) throws OroInsuficienteException, LimiteDePoblacionException, CoordenadaInvalidaException {
        verificarOroSuficiente(unidad.verPrecio());
        verificarPoblacionSuficiente();

        this.mapa.agregarUnidadCercana(unidad, creador);
        this.unidades.add(unidad);
        this.oro -= unidad.verPrecio();
        this.poblacion += 1;
    }

    public void agregarUnidad(Edificio unidad, Aldeano creador, Point2D pos) throws OroInsuficienteException, CoordenadaInvalidaException {
        verificarOroSuficiente(unidad.verPrecio());

        this.mapa.agregarUnidadCercana(unidad, creador, pos);
        this.unidades.add(unidad);
        this.oro -= unidad.verPrecio();
    }

    private void verificarOroSuficiente(int precio) throws OroInsuficienteException {
        if (oro < precio)
            throw new OroInsuficienteException("El oro del jugador es insuficiente.");
    }

    private void verificarPoblacionSuficiente() throws LimiteDePoblacionException {
        if (this.poblacion >= CANTIDAD_MAX_POBLACION)
            throw new LimiteDePoblacionException("El limite de población llegó al máximo.");
    }

    public void removerUnidad(Edificio unidad) {
        this.unidades.remove(unidad);
    }

    public void removerUnidad(Milicia unidad) {
        this.poblacion -= 1;
        this.unidades.remove(unidad);
    }

    public List<Dibujable> unidadesCercanas(Castillo castillo) {
        return this.mapa.dibujablesAlAlcance(castillo);
    }
}

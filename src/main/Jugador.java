package main;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import javafx.scene.paint.Color;
import unidades.Unidad;
import unidades.edificios.Castillo;
import unidades.edificios.Edificio;
import unidades.edificios.PlazaCentral;
import unidades.estados.aldeano.Ocioso;
import unidades.milicias.Aldeano;
import unidades.milicias.Milicia;

import java.awt.geom.Point2D;
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
    private Color color;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.unidades = new LinkedList<>();
        this.mapa = Mapa.obtenerInstancia();

        this.oro = 100;
        this.poblacion = CANTIDAD_ALDEANOS_INICIALES;

        Castillo castillo = new Castillo(this);
        PlazaCentral plazaCentral = new PlazaCentral(this);
        plazaCentral.terminarConstruccion();
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

    public Jugador(String nombre, Color color) {
        this(nombre);
        this.color = color;
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
            throw new OroInsuficienteException("El Oro del Jugador es insuficiente!");
    }

    private void verificarPoblacionSuficiente() throws LimiteDePoblacionException {
        if (this.poblacion >= CANTIDAD_MAX_POBLACION)
            throw new LimiteDePoblacionException("El límite de Población llegó al Máximo!");
    }

    public void removerUnidad(Unidad unidad, int costeDePoblacion) {
        this.poblacion -= costeDePoblacion;
        this.unidades.remove(unidad);
        try {
            this.mapa.quitarUnidad(unidad);
        } catch (CoordenadaInvalidaException ignored) {
        }
    }

    public String verNombre() {
        return this.nombre;
    }

    public int verOro() {
        return this.oro;
    }

    public int verPoblacion() {
        return this.poblacion;
    }

    public boolean todaviaEnJuego() {
        return this.unidades.stream().anyMatch(u -> u.getClass() == Castillo.class);
    }

    public Color obtenerColor() {
        return (this.color == null) ? Color.WHITE : this.color;
    }

}

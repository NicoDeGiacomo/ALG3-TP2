package main;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.FueraDeRangoException;
import excepciones.mapa.PosicionOcupadaException;
import unidades.Unidad;
import unidades.edificio.Castillo;
import unidades.edificio.Edificio;
import unidades.edificio.PlazaCentral;
import unidades.estados.aldeano.Ocioso;
import unidades.milicia.Aldeano;
import unidades.milicia.Milicia;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class Jugador {

    private static final int CANTIDAD_ALDEANOS_INICIALES = 3;

    private String nombre;
    private List<Unidad> unidades;
    private int oro;
    private Mapa mapa;

    public Jugador(String nombre, Mapa mapa) {
        this.nombre = nombre;
        this.unidades = new LinkedList<>();
        this.mapa = mapa;

        this.oro = 100;

        Castillo castillo = new Castillo(this);
        PlazaCentral plazaCentral = new PlazaCentral(this);
        this.unidades.add(castillo);
        this.unidades.add(plazaCentral);
        this.mapa.colocarUnidadesEnExtremo(castillo, plazaCentral);

        for (int i = 0; i < CANTIDAD_ALDEANOS_INICIALES; i++) {
            Aldeano aldeano = new Aldeano(this);
            this.mapa.agregarUnidadCercana(aldeano, plazaCentral);
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

    public void agregarUnidad(Milicia unidad, Edificio creador) throws OroInsuficienteException {
        if (this.oro < unidad.verPrecio())
            throw new OroInsuficienteException("El oro del jugador es insuficiente.");

        this.unidades.add(unidad);//TODO: Verificar limite de poblacion
        this.mapa.agregarUnidadCercana(unidad, creador);
        this.oro -= unidad.verPrecio();
    }

    public void agregarUnidad(Edificio unidad, Aldeano creador, Point2D pos) throws OroInsuficienteException, FueraDeRangoException, PosicionOcupadaException {
        if (this.oro < unidad.verPrecio())
            throw new OroInsuficienteException("El oro del jugador es insuficiente.");

        this.unidades.add(unidad);//TODO: Verificar limite de poblacion
        this.mapa.agregarUnidadCercana(unidad, creador, pos);
        this.oro -= unidad.verPrecio();
    }
}

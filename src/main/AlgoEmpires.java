package main;

import excepciones.main.NombreRepetidoException;
import excepciones.main.NumeroDeJugadoresException;
import excepciones.main.OroInsuficienteException;
import excepciones.main.PartidaComenzadaException;
import excepciones.main.PartidaNoComenzadaException;
import excepciones.mapa.FueraDeRangoException;
import excepciones.mapa.PosicionOcupadaException;
import excepciones.unidades.AldeanoOcupadoException;
import excepciones.unidades.CreacionDeCastilloException;
import excepciones.unidades.UnidadNoEspecificadaException;
import unidades.Unidad;
import unidades.edificio.Castillo;
import unidades.edificio.Edificio;
import unidades.edificio.PlazaCentral;
import unidades.milicia.Aldeano;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class AlgoEmpires {

    private static final int CANTIDAD_JUGADORES_MAX = 2;
    private static final int CANTIDAD_JUGADORES_MIN = 2;
    private static final int CANTIDAD_ALDEANOS_INICIALES = 3;

    private List<Jugador> jugadores;
    private Integer turno;
    private Mapa mapa;

    AlgoEmpires() {
        this.jugadores = new LinkedList<>();
        this.turno = null;
        this.mapa = new Mapa();
    }

    Jugador comenzarPartida() throws NumeroDeJugadoresException, PartidaComenzadaException {
        if (this.turno != null)
            throw new PartidaComenzadaException("La partida ya está en juego");
        if (this.jugadores.size() < CANTIDAD_JUGADORES_MIN)
            throw new NumeroDeJugadoresException(String.format("No se pueden agregar menos de %d jugadores", CANTIDAD_JUGADORES_MIN));

        for (Jugador jugador : this.jugadores) {

            Castillo castillo = new Castillo(jugador);
            PlazaCentral plazaCentral = new PlazaCentral(jugador);
            this.mapa.colocarUnidadesEnExtremo(castillo, plazaCentral);

            List<Aldeano> aldeanos = new LinkedList<>();
            for (int i = 0; i < CANTIDAD_ALDEANOS_INICIALES; i++) {
                Aldeano aldeano = new Aldeano(jugador);
                this.mapa.agregarUnidadCercana(aldeano, plazaCentral);
                aldeanos.add(aldeano);
            }

            jugador.agregarUnidadesIniciales(castillo, plazaCentral, aldeanos);
            jugador.recolectarOro(100);
        }

        this.turno = 0;
        return this.jugadores.get(this.turno);
    }

    Jugador pasarTurno() throws PartidaNoComenzadaException {
        if (this.turno == null)
            throw new PartidaNoComenzadaException("Se debe comenzar la partida antes de pasar el turno");

        this.turno = (this.turno + 1) % this.jugadores.size();
        this.jugadores.get(this.turno).comenzarNuevoTurno();
        return this.jugadores.get(this.turno);
    }

    void agregarJugador(String nombre) throws NombreRepetidoException, NumeroDeJugadoresException {
        if (this.jugadores.size() >= CANTIDAD_JUGADORES_MAX)
            throw new NumeroDeJugadoresException(String.format("No se pueden agregar mas de %d jugadores", CANTIDAD_JUGADORES_MAX));
        for (Jugador jugador : this.jugadores) {
            if (jugador.tieneComoNombre(nombre)) {
                throw new NombreRepetidoException("No puede haber jugadores con el mismo nombre");
            }
        }

        this.jugadores.add(new Jugador(nombre));
    }

    void agregarMiliciaAJugadorEnTurno(Edificio creador, Point2D pos) throws OroInsuficienteException, FueraDeRangoException, PosicionOcupadaException, UnidadNoEspecificadaException {
        Unidad unidadCreada = creador.crearUnidad();

        try {
            this.mapa.agregarUnidadCercana(unidadCreada, creador, pos);
        } catch (FueraDeRangoException | PosicionOcupadaException e) {
            unidadCreada.devolverCosto(); //Devuelvo el costo si no hubo espacio en el mapa.
            throw e;
        }
    }

    void agregarEdificioAJugadorEnTurno(Aldeano creador, Edificio unidad, Point2D pos) throws OroInsuficienteException, AldeanoOcupadoException, FueraDeRangoException, PosicionOcupadaException, CreacionDeCastilloException {
        this.mapa.colocarUnidad(unidad, pos);
        try {
            creador.construir(unidad);
        } catch (OroInsuficienteException | CreacionDeCastilloException e) {
            this.mapa.quitarUnidad(unidad); //Remuevo la unidad del mapa si no hubo oro suficiente.
            throw e;
        }
    }

}

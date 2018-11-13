package main;

import excepciones.main.NombreRepetidoException;
import excepciones.main.NumeroDeJugadoresException;
import excepciones.main.OroInsuficienteException;
import excepciones.main.PartidaComenzadaException;
import excepciones.main.PartidaNoComenzadaException;
import unidades.edificio.Castillo;

import java.util.LinkedList;
import java.util.List;

public class AlgoEmpires {

    private static final int CANTIDAD_JUGADORES_MAX = 2;
    private static final int CANTIDAD_JUGADORES_MIN = 2;

    private List<Jugador> jugadores;
    private Integer turno;
    private Mapa mapa;

    AlgoEmpires() {
        this.jugadores = new LinkedList<>();
        this.turno = null;
        this.mapa = new Mapa();
    }

    void comenzarPartida() throws NumeroDeJugadoresException, PartidaComenzadaException, OroInsuficienteException {
        if (this.turno != null)
            throw new PartidaComenzadaException("La partida ya está en juego");
        if (this.jugadores.size() < CANTIDAD_JUGADORES_MIN)
            throw new NumeroDeJugadoresException(String.format("No se pueden agregar menos de %d jugadores", CANTIDAD_JUGADORES_MIN));

        for (Jugador jugador : this.jugadores) {
            Castillo castillo = new Castillo(new Jugador("Nico"));
            jugador.agregarUnidad(castillo);//TODO Agregar al mapa (en extremos distintos!!)
            //this.mapa.colocarUnidadEnExtremo(castillo); | this.mapa.colocarCastillo(castillo);
        }

        this.turno = 0;
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

}

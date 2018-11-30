package main;

import excepciones.main.ComienzoDePartidaException;
import excepciones.main.NombreRepetidoException;
import excepciones.main.NumeroDeJugadoresException;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class AlgoEmpires {

    private static final int CANTIDAD_JUGADORES_MAX = 2;
    private static final int CANTIDAD_JUGADORES_MIN = 2;

    private List<Jugador> jugadores;
    private Integer turno;
    private Mapa mapa;

    public AlgoEmpires() {
        this.jugadores = new LinkedList<>();
        this.turno = null;
        this.mapa = new Mapa();
    }

    public Mapa comenzarPartida() throws NumeroDeJugadoresException, ComienzoDePartidaException {
        if (this.turno != null)
            throw new ComienzoDePartidaException("La partida ya está en juego");
        if (this.jugadores.size() < CANTIDAD_JUGADORES_MIN)
            throw new NumeroDeJugadoresException(String.format("No se pueden agregar menos de %d jugadores", CANTIDAD_JUGADORES_MIN));

        this.turno = 0;
        return this.mapa;
    }

    public Jugador pasarTurno() throws ComienzoDePartidaException {
        if (this.turno == null)
            throw new ComienzoDePartidaException("Se debe comenzar la partida antes de pasar el turno");

        this.turno = (this.turno + 1) % this.jugadores.size();
        this.jugadores.get(this.turno).comenzarNuevoTurno();
        return this.jugadores.get(this.turno);
    }

    public void agregarJugador(String nombre, Color color) throws NombreRepetidoException, NumeroDeJugadoresException {
        if (this.jugadores.size() >= CANTIDAD_JUGADORES_MAX)
            throw new NumeroDeJugadoresException(String.format("No se pueden agregar mas de %d jugadores", CANTIDAD_JUGADORES_MAX));
        for (Jugador jugador : this.jugadores) {
            if (jugador.tieneComoNombre(nombre)) {
                throw new NombreRepetidoException("No puede haber jugadores con el mismo nombre");
            }
        }
        this.jugadores.add(new Jugador(nombre, this.mapa, color));
    }

    public Jugador obtenerJugadorEnTurno() {
        return this.jugadores.get(this.turno);
    }

    public List<Jugador> obtenerJugadores() {
        return jugadores;
    }
}

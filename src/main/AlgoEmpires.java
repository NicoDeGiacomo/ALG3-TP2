package main;

import excepciones.main.ComienzoDePartidaException;
import excepciones.main.NombreRepetidoException;
import excepciones.main.NumeroDeJugadoresException;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AlgoEmpires {

    private static final int CANTIDAD_JUGADORES_MAX = 2;
    private static final int CANTIDAD_JUGADORES_MIN = 2;

    private List<Jugador> jugadores;
    private Integer turno;

    public AlgoEmpires() {
        this.jugadores = new LinkedList<>();
        this.turno = null;
    }

    public void comenzarPartida() throws NumeroDeJugadoresException, ComienzoDePartidaException {
        if (this.turno != null)
            throw new ComienzoDePartidaException("La Partida ya está en Juego!");
        if (this.jugadores.size() < CANTIDAD_JUGADORES_MIN)
            throw new NumeroDeJugadoresException(String.format("No se pueden agregar menos de %d Jugadores.", CANTIDAD_JUGADORES_MIN));

        this.turno = new Random().nextInt(this.jugadores.size());
    }

    public Jugador pasarTurno() throws ComienzoDePartidaException {
        if (this.turno == null)
            throw new ComienzoDePartidaException("Se debe comenzar la Partida ANTES de pasar el Turno!");

        this.turno = (this.turno + 1) % this.jugadores.size();
        this.jugadores.get(this.turno).comenzarNuevoTurno();
        return this.jugadores.get(this.turno);
    }

    public void agregarJugador(String nombre, Color color) throws NombreRepetidoException, NumeroDeJugadoresException {
        if (this.jugadores.size() >= CANTIDAD_JUGADORES_MAX)
            throw new NumeroDeJugadoresException(String.format("No se pueden agregar más de %d Jugadores.", CANTIDAD_JUGADORES_MAX));
        for (Jugador jugador : this.jugadores) {
            if (jugador.tieneComoNombre(nombre)) {
                throw new NombreRepetidoException("No puede haber Jugadores con el mismo Nombre!");
            }
        }
        this.jugadores.add(new Jugador(nombre, color));
    }

    public Jugador obtenerJugadorEnTurno() {
        return this.jugadores.get(this.turno);
    }

    public List<Jugador> obtenerJugadores() {
        return jugadores;
    }
}

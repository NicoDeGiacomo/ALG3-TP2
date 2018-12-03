package main;

import excepciones.main.ComienzoDePartidaException;
import excepciones.main.NombreRepetidoException;
import excepciones.main.NumeroDeJugadoresException;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static org.junit.jupiter.api.Assertions.*;

public class AlgoEmpiresTests {

    @Before
    public void before() {
        Mapa.destruir();
    }

    @Test
    public void noSePuedeComenzarElJuegoConMenosDeDosJugadores() {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        assertThrows(NumeroDeJugadoresException.class, algoEmpires::comenzarPartida);
    }

    @Test
    public void agregarDosJugadoresConElMismoNombreLanzaUnError() throws NombreRepetidoException, NumeroDeJugadoresException {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        algoEmpires.agregarJugador("Nico", Color.web("#b04e50"));

        assertThrows(NombreRepetidoException.class, () -> algoEmpires.agregarJugador("Nico", Color.web("#b04e50")));
    }

    @Test
    public void noSePuedeAgregarMasDeDosJugadores() throws NombreRepetidoException, NumeroDeJugadoresException {
        AlgoEmpires algoEmpires = crearJuego();

        assertThrows(NumeroDeJugadoresException.class, () -> algoEmpires.agregarJugador("Peter", Color.web("#b04e50")));
    }

    @Test
    public void noSePuedePasarElTurnoAntesDeComenzarLaPartida() {
        AlgoEmpires algoEmpires = new AlgoEmpires();

        assertThrows(ComienzoDePartidaException.class, algoEmpires::pasarTurno);
    }

    @Test
    public void noSePuedeComenzarDosVecesLaPartida() throws NombreRepetidoException, NumeroDeJugadoresException, ComienzoDePartidaException {
        AlgoEmpires algoEmpires = crearEIniciarJuego();

        assertThrows(ComienzoDePartidaException.class, algoEmpires::comenzarPartida);
    }

    @Test
    public void pasarElTurnoCambiaLosJugadores() throws NombreRepetidoException, NumeroDeJugadoresException, ComienzoDePartidaException {
        AlgoEmpires algoEmpires = crearEIniciarJuego();

        Jugador jugador1 = algoEmpires.pasarTurno();
        Jugador jugador2 = algoEmpires.pasarTurno();
        Jugador jugador3 = algoEmpires.pasarTurno();
        assertNotEquals(jugador1, jugador2);
        assertEquals(jugador1, jugador3);
    }

    private AlgoEmpires crearJuego() throws NombreRepetidoException, NumeroDeJugadoresException {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        algoEmpires.agregarJugador("Nico", Color.web("#b04e50"));
        algoEmpires.agregarJugador("Gaston", Color.web("#b04e50"));
        return algoEmpires;
    }

    private AlgoEmpires crearEIniciarJuego() throws NombreRepetidoException, NumeroDeJugadoresException, ComienzoDePartidaException {
        AlgoEmpires algoEmpires = crearJuego();
        algoEmpires.comenzarPartida();
        return algoEmpires;
    }

    public static void agregarCienDeOroAJugador(Jugador jugador) {
        Whitebox.setInternalState(jugador, "oro", 200);
    }
}

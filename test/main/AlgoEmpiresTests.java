package main;

import excepciones.main.ComienzoDePartidaException;
import excepciones.main.NombreRepetidoException;
import excepciones.main.NumeroDeJugadoresException;
import org.junit.Test;
import unidades.estados.aldeano.Ocioso;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlgoEmpiresTests {

    @Test
    public void noSePuedeComenzarElJuegoConMenosDeDosJugadores() {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        assertThrows(NumeroDeJugadoresException.class, algoEmpires::comenzarPartida);
    }

    @Test
    public void agregarDosJugadoresConElMismoNombreLanzaUnError() throws NombreRepetidoException, NumeroDeJugadoresException {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        algoEmpires.agregarJugador("Nico");

        assertThrows(NombreRepetidoException.class, () -> algoEmpires.agregarJugador("Nico"));
    }

    @Test
    public void noSePuedeAgregarMasDeDosJugadores() throws NombreRepetidoException, NumeroDeJugadoresException {
        AlgoEmpires algoEmpires = crearJuego();

        assertThrows(NumeroDeJugadoresException.class, () -> algoEmpires.agregarJugador("Peter"));
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

        Jugador jugador = algoEmpires.pasarTurno();
        assertTrue(jugador.tieneComoNombre("Gaston"));
    }

    private AlgoEmpires crearJuego() throws NombreRepetidoException, NumeroDeJugadoresException {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        algoEmpires.agregarJugador("Nico");
        algoEmpires.agregarJugador("Gaston");
        return algoEmpires;
    }

    private AlgoEmpires crearEIniciarJuego() throws NombreRepetidoException, NumeroDeJugadoresException, ComienzoDePartidaException {
        AlgoEmpires algoEmpires = crearJuego();
        algoEmpires.comenzarPartida();
        return algoEmpires;
    }

    public static void agregarCienDeOroAJugador(Jugador jugador) {
        for (int i = 0; i < 5; i++) {
            jugador.recolectarOro(new Ocioso(jugador));
        }
    }
}

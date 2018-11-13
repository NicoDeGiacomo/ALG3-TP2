package main;

import excepciones.main.NombreRepetidoException;
import excepciones.main.NumeroDeJugadoresException;
import excepciones.main.OroInsuficienteException;
import excepciones.main.PartidaComenzadaException;
import excepciones.main.PartidaNoComenzadaException;
import org.junit.Test;
import unidades.milicia.Aldeano;

import static org.junit.jupiter.api.Assertions.*;

public class AlgoEmpiresTests {

    @Test
    public void noSePuedeComenzarElJuegoConMenosDeDosJugadores() {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        assertThrows(NumeroDeJugadoresException.class, algoEmpires::comenzarPartida);
    }

    @Test
    public void agregarDosJugadoresConElMismoNombreLanzaUnError() {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        try {
            algoEmpires.agregarJugador("Nico");
        } catch (NombreRepetidoException | NumeroDeJugadoresException e) {
            fail("Error no esperado");
        }
        assertThrows(NombreRepetidoException.class,()->{algoEmpires.agregarJugador("Nico");});
    }

    @Test
    public void noSePuedeAgregarMasDeDosJugadores() {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        try {
            algoEmpires.agregarJugador("Nico");
            algoEmpires.agregarJugador("Gaston");
        } catch (NombreRepetidoException | NumeroDeJugadoresException e) {
            fail("Error no esperado");
        }
        assertThrows(NumeroDeJugadoresException.class,()->{algoEmpires.agregarJugador("Peter");});
    }

    @Test
    public void noSePuedePasarElTurnoAntesDeComenzarLaPartida() {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        assertThrows(PartidaNoComenzadaException.class, algoEmpires::pasarTurno);
    }

    @Test
    public void noSePuedeComenzarDosVecesLaPartida() {
        AlgoEmpires algoEmpires = new AlgoEmpires();

        try {
            algoEmpires.agregarJugador("Nico");
            algoEmpires.agregarJugador("Gaston");
            algoEmpires.comenzarPartida();
        } catch (NombreRepetidoException | PartidaComenzadaException | NumeroDeJugadoresException | OroInsuficienteException e) {
            fail("Error no esperado");
        }

        assertThrows(PartidaComenzadaException.class, algoEmpires::comenzarPartida);
    }

    @Test
    public void pasarElTurnoCambiaLosJugadores() {
        AlgoEmpires algoEmpires = new AlgoEmpires();

        Jugador jugador = null;
        try {
            algoEmpires.agregarJugador("Nico");
            algoEmpires.agregarJugador("Gaston");
            algoEmpires.comenzarPartida();
            jugador = algoEmpires.pasarTurno();
        } catch (NombreRepetidoException | PartidaComenzadaException | NumeroDeJugadoresException | PartidaNoComenzadaException | OroInsuficienteException e) {
            fail("Error no esperado");
        }
        assertTrue(jugador.tieneComoNombre("Gaston"));
    }
}


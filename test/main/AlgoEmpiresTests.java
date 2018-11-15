package main;

import excepciones.main.NombreRepetidoException;
import excepciones.main.NumeroDeJugadoresException;
import excepciones.main.PartidaComenzadaException;
import excepciones.main.PartidaNoComenzadaException;

import org.junit.Test;
import unidades.edificio.Castillo;
import unidades.milicia.Aldeano;
import unidades.milicia.Arquero;

import java.awt.geom.Point2D;

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
        } catch (NombreRepetidoException | NumeroDeJugadoresException ignored) {
            fail("Error no esperado");
        }
        assertThrows(NombreRepetidoException.class, () -> algoEmpires.agregarJugador("Nico"));
    }

    @Test
    public void noSePuedeAgregarMasDeDosJugadores() {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        try {
            algoEmpires.agregarJugador("Nico");
            algoEmpires.agregarJugador("Gaston");
        } catch (NombreRepetidoException | NumeroDeJugadoresException ignored) {
            fail("Error no esperado");
        }
        assertThrows(NumeroDeJugadoresException.class, () -> algoEmpires.agregarJugador("Peter"));
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
        } catch (NombreRepetidoException | PartidaComenzadaException | NumeroDeJugadoresException ignored) {
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
        } catch (NombreRepetidoException | PartidaComenzadaException | NumeroDeJugadoresException | PartidaNoComenzadaException ignored) {
            fail("Error no esperado");
        }
        assertTrue(jugador.tieneComoNombre("Gaston"));
    }

    @Test
    public void agregarMiliciaAJugadorEnTurnoConOroInsufucienteDebeRomper() {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        try {
            algoEmpires.agregarJugador("Nico");
            algoEmpires.agregarJugador("Gaston");
            algoEmpires.comenzarPartida();
        } catch (NumeroDeJugadoresException | PartidaComenzadaException | NombreRepetidoException e) {
            fail("Error no esperado");
        }

        //assertThrows(PartidaComenzadaException.class, () -> algoEmpires.agregarUnidadAJugadorEnTurno(new Castillo(new Jugador("Nico")), new Point2D.Double(1, 1)));
    }

    @Test
    public void agregarMiliciaAJugadorEnTurnoConOroSufucienteNoDebeRomper() {
        //TODO: IMPLEMENT ME
    }
}


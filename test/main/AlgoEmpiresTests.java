package main;

import excepciones.main.*;
import excepciones.mapa.FueraDeRangoException;
import excepciones.mapa.PosicionOcupadaException;
import excepciones.unidades.CreacionDeCastilloException;
import excepciones.unidades.UnidadNoEspecificadaException;
import unidades.edificio.Castillo;
import java.awt.geom.Point2D;

import org.junit.Test;
import unidades.edificio.Cuartel;
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

        assertThrows(OroInsuficienteException.class, () -> algoEmpires.agregarMiliciaAJugadorEnTurno(new Castillo(new Jugador("Nico")), new Point2D.Double(1, 1)));
    }

    @Test
    public void agregarMiliciaAJugadorEnTurnoConOroSufucienteNoDebeRomper() {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        Jugador jugador = null;
        try {
            algoEmpires.agregarJugador("Nico");
            algoEmpires.agregarJugador("Gaston");
            jugador = algoEmpires.comenzarPartida();
        } catch (NumeroDeJugadoresException | PartidaComenzadaException | NombreRepetidoException e) {
            fail("Error no esperado");
        }
        jugador.recolectarOro(1000);
        Castillo castillo = new Castillo(jugador);

        try {
            algoEmpires.agregarMiliciaAJugadorEnTurno(castillo, new Point2D.Double(1, 1));
        } catch (OroInsuficienteException | PosicionOcupadaException | FueraDeRangoException | UnidadNoEspecificadaException e) {
            fail("Error no esperado", e);
        }
    }

    @Test
    public void agregarEdificioAJugadorEnTurnoConOroInsufucienteDebeRomper() {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        try {
            algoEmpires.agregarJugador("Nico");
            algoEmpires.agregarJugador("Gaston");
            algoEmpires.comenzarPartida();
        } catch (NumeroDeJugadoresException | PartidaComenzadaException | NombreRepetidoException e) {
            fail("Error no esperado");
        }

        assertThrows(OroInsuficienteException.class, () -> algoEmpires.agregarEdificioAJugadorEnTurno(new Aldeano(new Jugador("Nico")), new Cuartel(new Jugador("Nico")), new Point2D.Double(1, 1)));
    }

    @Test
    public void creacionDeCastilloDebeRomper() {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        try {
            algoEmpires.agregarJugador("Nico");
            algoEmpires.agregarJugador("Gaston");
            algoEmpires.comenzarPartida();
        } catch (NumeroDeJugadoresException | PartidaComenzadaException | NombreRepetidoException e) {
            fail("Error no esperado");
        }

        assertThrows(CreacionDeCastilloException.class, () -> algoEmpires.agregarEdificioAJugadorEnTurno(new Aldeano(new Jugador("Nico")), new Castillo(new Jugador("Nico")), new Point2D.Double(1, 1)));
    }
}

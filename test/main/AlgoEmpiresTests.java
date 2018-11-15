package main;

import excepciones.main.*;
import excepciones.mapa.FueraDeRangoException;
import excepciones.mapa.PosicionOcupadaException;
import excepciones.unidades.CreacionDeCastilloException;
import excepciones.unidades.UnidadNoEspecificadaException;
import org.junit.jupiter.api.function.Executable;
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

        controlarErroresInesperados(() -> algoEmpires.agregarJugador("Nico"));

        assertThrows(NombreRepetidoException.class, () -> algoEmpires.agregarJugador("Nico"));
    }

    @Test
    public void noSePuedeAgregarMasDeDosJugadores() {
        AlgoEmpires algoEmpires = new AlgoEmpires();

        controlarErroresInesperados(() -> algoEmpires.agregarJugador("Nico"));
        controlarErroresInesperados(() -> algoEmpires.agregarJugador("Gaston"));

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

        controlarErroresInesperados(() -> algoEmpires.agregarJugador("Nico"));
        controlarErroresInesperados(() -> algoEmpires.agregarJugador("Gaston"));
        controlarErroresInesperados(algoEmpires::comenzarPartida);

        assertThrows(PartidaComenzadaException.class, algoEmpires::comenzarPartida);
    }

    @Test
    public void pasarElTurnoCambiaLosJugadores() {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        controlarErroresInesperados(() -> algoEmpires.agregarJugador("Nico"));
        controlarErroresInesperados(() -> algoEmpires.agregarJugador("Gaston"));
        controlarErroresInesperados(algoEmpires::comenzarPartida);

        Jugador jugador = null;
        try {
            jugador = algoEmpires.pasarTurno();
        } catch (PartidaNoComenzadaException e) {
            fail("Error inesperado", e);
        }
        assertTrue(jugador.tieneComoNombre("Gaston"));
    }

    @Test
    public void agregarMiliciaAJugadorEnTurnoConOroInsufucienteDebeRomper() {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        controlarErroresInesperados(() -> algoEmpires.agregarJugador("Nico"));
        controlarErroresInesperados(() -> algoEmpires.agregarJugador("Gaston"));
        controlarErroresInesperados(algoEmpires::comenzarPartida);

        assertThrows(OroInsuficienteException.class, () -> algoEmpires.agregarMiliciaAJugadorEnTurno(new Castillo(new Jugador("Nico")), new Point2D.Double(1, 1)));
    }

    @Test
    public void agregarMiliciaAJugadorEnTurnoConOroSufucienteNoDebeRomper() {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        controlarErroresInesperados(() -> algoEmpires.agregarJugador("Nico"));
        controlarErroresInesperados(() -> algoEmpires.agregarJugador("Gaston"));

        Jugador jugador = null;
        try {
            jugador = algoEmpires.comenzarPartida();
        } catch (NumeroDeJugadoresException | PartidaComenzadaException e) {
            fail("Error inesperado", e);
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
        controlarErroresInesperados(() -> algoEmpires.agregarJugador("Nico"));
        controlarErroresInesperados(() -> algoEmpires.agregarJugador("Gaston"));
        controlarErroresInesperados(algoEmpires::comenzarPartida);

        assertThrows(OroInsuficienteException.class, () -> algoEmpires.agregarEdificioAJugadorEnTurno(new Aldeano(new Jugador("Nico")), new Cuartel(new Jugador("Nico")), new Point2D.Double(1, 1)));
    }

    @Test
    public void creacionDeCastilloDebeRomper() {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        controlarErroresInesperados(() -> algoEmpires.agregarJugador("Nico"));
        controlarErroresInesperados(() -> algoEmpires.agregarJugador("Gaston"));
        controlarErroresInesperados(algoEmpires::comenzarPartida);

        assertThrows(CreacionDeCastilloException.class, () -> algoEmpires.agregarEdificioAJugadorEnTurno(new Aldeano(new Jugador("Nico")), new Castillo(new Jugador("Nico")), new Point2D.Double(1, 1)));
    }

    private void controlarErroresInesperados(Executable ejecutable) {
        try {
            ejecutable.execute();
        } catch (Throwable throwable) {
            fail("Error inesperado", throwable);
        }
    }
}

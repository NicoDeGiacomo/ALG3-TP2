package main;

import excepciones.main.*;
import excepciones.mapa.FueraDeRangoException;
import excepciones.mapa.PosicionOcupadaException;
import excepciones.unidades.AldeanoOcupadoException;
import excepciones.unidades.CreacionDeCastilloException;
import excepciones.unidades.UnidadNoAgregadaException;
import excepciones.unidades.UnidadNoEspecificadaException;
import org.junit.Test;
import unidades.edificio.Castillo;
import unidades.edificio.Cuartel;
import unidades.edificio.PlazaCentral;
import unidades.milicia.Aldeano;
import unidades.milicia.Espadachin;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

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
        AlgoEmpires algoEmpires = new AlgoEmpires();

        algoEmpires.agregarJugador("Nico");
        algoEmpires.agregarJugador("Gaston");

        assertThrows(NumeroDeJugadoresException.class, () -> algoEmpires.agregarJugador("Peter"));
    }

    @Test
    public void noSePuedePasarElTurnoAntesDeComenzarLaPartida() {
        AlgoEmpires algoEmpires = new AlgoEmpires();

        assertThrows(PartidaNoComenzadaException.class, algoEmpires::pasarTurno);
    }

    @Test
    public void noSePuedeComenzarDosVecesLaPartida() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException {
        AlgoEmpires algoEmpires = new AlgoEmpires();

        algoEmpires.agregarJugador("Nico");
        algoEmpires.agregarJugador("Gaston");
        algoEmpires.comenzarPartida();

        assertThrows(PartidaComenzadaException.class, algoEmpires::comenzarPartida);
    }

    @Test
    public void pasarElTurnoCambiaLosJugadores() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException, PartidaNoComenzadaException {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        algoEmpires.agregarJugador("Nico");
        algoEmpires.agregarJugador("Gaston");
        algoEmpires.comenzarPartida();

        Jugador jugador = null;
        jugador = algoEmpires.pasarTurno();
        assertTrue(jugador.tieneComoNombre("Gaston"));
    }

    @Test
    public void agregarMiliciaAJugadorEnTurnoConOroInsufucienteDebeRomper() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        algoEmpires.agregarJugador("Nico");
        algoEmpires.agregarJugador("Gaston");
        algoEmpires.comenzarPartida();

        assertThrows(OroInsuficienteException.class, () -> algoEmpires.agregarMiliciaAJugadorEnTurno(new Castillo(new Jugador("Nico")), new Point2D.Double(1, 1)));
    }

    @Test
    public void agregarMiliciaAJugadorEnTurnoConOroSufucienteNoDebeRomper() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException, OroInsuficienteException, PosicionOcupadaException, FueraDeRangoException, UnidadNoEspecificadaException, UnidadNoAgregadaException {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        algoEmpires.agregarJugador("Nico");
        algoEmpires.agregarJugador("Gaston");

        Jugador jugador = null;
        jugador = algoEmpires.comenzarPartida();
        jugador.recolectarOro(1000);
        Castillo castillo = new Castillo(jugador);

        algoEmpires.agregarMiliciaAJugadorEnTurno(castillo, new Point2D.Double(1, 1));
    }

    @Test
    public void agregarEdificioAJugadorEnTurnoConOroInsufucienteDebeRomper() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        algoEmpires.agregarJugador("Nico");
        algoEmpires.agregarJugador("Gaston");
        algoEmpires.comenzarPartida();

        assertThrows(OroInsuficienteException.class, () -> algoEmpires.agregarEdificioAJugadorEnTurno(new Aldeano(new Jugador("Nico")), new Cuartel(new Jugador("Nico")), new Point2D.Double(1, 1)));
    }

    @Test
    public void agregarEdificioAJugadorEnTurnoOK() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException, FueraDeRangoException, PosicionOcupadaException, AldeanoOcupadoException, CreacionDeCastilloException, OroInsuficienteException {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        algoEmpires.agregarJugador("Nico");
        algoEmpires.agregarJugador("Gaston");

        Jugador jugador = null;
        jugador = algoEmpires.comenzarPartida();
        jugador.recolectarOro(1000);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        Aldeano aldeano = new Aldeano(jugador);

        algoEmpires.agregarEdificioAJugadorEnTurno(aldeano, plazaCentral, new Point2D.Double(1, 1));
    }

    @Test
    public void creacionDeCastilloDebeRomper() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        algoEmpires.agregarJugador("Nico");
        algoEmpires.agregarJugador("Gaston");
        algoEmpires.comenzarPartida();

        assertThrows(CreacionDeCastilloException.class, () -> algoEmpires.agregarEdificioAJugadorEnTurno(new Aldeano(new Jugador("Nico")), new Castillo(new Jugador("Nico")), new Point2D.Double(1, 1)));
    }

    @Test
    public void agregarMiliciaAJugadorEnTurnoDevuelveElOroSiNoHayLugarEnElMapa() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException, PosicionOcupadaException, UnidadNoEspecificadaException, UnidadNoAgregadaException, OroInsuficienteException {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        algoEmpires.agregarJugador("Nico");
        algoEmpires.agregarJugador("Gaston");

        Jugador jugador = null;
        jugador = algoEmpires.comenzarPartida();
        jugador.recolectarOro(1000);
        Castillo castillo = new Castillo(jugador);

        assertThrows(FueraDeRangoException.class, () -> algoEmpires.agregarMiliciaAJugadorEnTurno(castillo, new Point2D.Double(5000, 50000)));

        jugador.cobrarOro(1000);
    }
}

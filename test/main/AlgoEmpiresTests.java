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
import unidades.estados.aldeano.Ocioso;
import unidades.milicia.Aldeano;

import java.awt.geom.Point2D;

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

        assertThrows(PartidaNoComenzadaException.class, algoEmpires::pasarTurno);
    }

    @Test
    public void noSePuedeComenzarDosVecesLaPartida() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException {
        AlgoEmpires algoEmpires = crearEIniciarJuego();

        assertThrows(PartidaComenzadaException.class, algoEmpires::comenzarPartida);
    }

    @Test
    public void pasarElTurnoCambiaLosJugadores() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException, PartidaNoComenzadaException {
        AlgoEmpires algoEmpires = crearEIniciarJuego();

        Jugador jugador = null;
        jugador = algoEmpires.pasarTurno();
        assertTrue(jugador.tieneComoNombre("Gaston"));
    }

    @Test
    public void agregarMiliciaAJugadorEnTurnoConOroInsufucienteDebeRomper() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException {
        AlgoEmpires algoEmpires = crearEIniciarJuego();

        assertThrows(OroInsuficienteException.class, () -> algoEmpires.agregarMiliciaAJugadorEnTurno(new Castillo(new Jugador("Nico")), new Point2D.Double(1, 1)));
    }

    @Test
    public void agregarMiliciaAJugadorEnTurnoConOroSufucienteNoDebeRomper() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException, OroInsuficienteException, PosicionOcupadaException, FueraDeRangoException, UnidadNoEspecificadaException, UnidadNoAgregadaException {
        AlgoEmpires algoEmpires = crearJuego();

        Jugador jugador = null;
        jugador = algoEmpires.comenzarPartida();
        agregarCienDeOroAJugador(jugador);
        Castillo castillo = new Castillo(jugador);

        algoEmpires.agregarMiliciaAJugadorEnTurno(castillo, new Point2D.Double(1, 1));
    }

    @Test
    public void agregarArqueroAJugadorEnTurnoConOroSufucienteNoDebeRomper() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException, OroInsuficienteException, PosicionOcupadaException, FueraDeRangoException, UnidadNoEspecificadaException, UnidadNoAgregadaException {
        AlgoEmpires algoEmpires = crearJuego();

        Jugador jugador = null;
        jugador = algoEmpires.comenzarPartida();
        Cuartel cuartel = new Cuartel(jugador);

        algoEmpires.agregarEspadachinAJugadorEnTurno(cuartel, new Point2D.Double(1, 1));
    }

    @Test
    public void agregarEspadachinAJugadorEnTurnoConOroSufucienteNoDebeRomper() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException, OroInsuficienteException, PosicionOcupadaException, FueraDeRangoException, UnidadNoEspecificadaException, UnidadNoAgregadaException {
        AlgoEmpires algoEmpires = crearJuego();

        Jugador jugador = null;
        jugador = algoEmpires.comenzarPartida();
        Cuartel cuartel = new Cuartel(jugador);

        algoEmpires.agregarArqueroAJugadorEnTurno(cuartel, new Point2D.Double(1, 1));
    }

    @Test
    public void agregarEdificioAJugadorEnTurnoConOroInsufucienteDebeRomper() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException, FueraDeRangoException, PosicionOcupadaException, AldeanoOcupadoException, CreacionDeCastilloException, OroInsuficienteException {
        AlgoEmpires algoEmpires = crearJuego();
        Jugador jugador = algoEmpires.comenzarPartida();
        algoEmpires.agregarEdificioAJugadorEnTurno(new Aldeano(jugador), new PlazaCentral(jugador), new Point2D.Double(1, 1));
        assertThrows(OroInsuficienteException.class, () -> algoEmpires.agregarEdificioAJugadorEnTurno(new Aldeano(jugador), new Cuartel(jugador), new Point2D.Double(10, 10)));
    }

    @Test
    public void agregarEdificioAJugadorEnTurnoOK() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException, FueraDeRangoException, PosicionOcupadaException, AldeanoOcupadoException, CreacionDeCastilloException, OroInsuficienteException {
        AlgoEmpires algoEmpires = crearJuego();

        Jugador jugador = null;
        jugador = algoEmpires.comenzarPartida();
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        Aldeano aldeano = new Aldeano(jugador);

        algoEmpires.agregarEdificioAJugadorEnTurno(aldeano, plazaCentral, new Point2D.Double(1, 1));
    }

    @Test
    public void creacionDeCastilloDebeRomper() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException {
        AlgoEmpires algoEmpires = crearEIniciarJuego();

        assertThrows(CreacionDeCastilloException.class, () -> algoEmpires.agregarEdificioAJugadorEnTurno(new Aldeano(new Jugador("Nico")), new Castillo(new Jugador("Nico")), new Point2D.Double(1, 1)));
    }

    @Test
    public void agregarMiliciaAJugadorEnTurnoDevuelveElOroSiNoHayLugarEnElMapa() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException, PosicionOcupadaException, UnidadNoEspecificadaException, UnidadNoAgregadaException, OroInsuficienteException, FueraDeRangoException {
        AlgoEmpires algoEmpires = crearJuego();

        Jugador jugador = null;
        jugador = algoEmpires.comenzarPartida();
        agregarCienDeOroAJugador(jugador);
        Castillo castillo = new Castillo(jugador);

        assertThrows(FueraDeRangoException.class, () -> algoEmpires.agregarMiliciaAJugadorEnTurno(castillo, new Point2D.Double(5000, 50000)));
        algoEmpires.agregarMiliciaAJugadorEnTurno(castillo, new Point2D.Double(1, 1));

    }

    private AlgoEmpires crearJuego() throws NombreRepetidoException, NumeroDeJugadoresException {
        AlgoEmpires algoEmpires = new AlgoEmpires();
        algoEmpires.agregarJugador("Nico");
        algoEmpires.agregarJugador("Gaston");
        return algoEmpires;
    }

    private AlgoEmpires crearEIniciarJuego() throws NombreRepetidoException, NumeroDeJugadoresException, PartidaComenzadaException {
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

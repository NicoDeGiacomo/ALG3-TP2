package main;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.FueraDeRangoException;
import excepciones.mapa.PosicionOcupadaException;
import excepciones.unidades.UnidadNoAgregadaException;
import org.junit.Test;
import unidades.edificio.Castillo;
import unidades.edificio.PlazaCentral;
import unidades.milicia.Aldeano;
import unidades.milicia.ArmaDeAsedio;

import java.awt.geom.Point2D;

import static main.AlgoEmpiresTests.agregarCienDeOroAJugador;
import static org.junit.Assert.assertEquals;

public class JugadorTests {

    @Test
    public void noSePuedeCobrarOroConOroInsuficiente() {
        Jugador jugador = new Jugador("Nico", new Mapa());

        /*try {
            //jugador.cobrarOro(new PlazaCentral(jugador)); TODO: Fix test
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }*/
    }

    @Test
    public void cobrarOroConOroSuficiente() throws OroInsuficienteException {
        Jugador jugador = new Jugador("Nico", new Mapa());
        agregarCienDeOroAJugador(jugador);

        //jugador.cobrarOro(new PlazaCentral(jugador)); TODO: Fix test
    }

    @Test
    public void noSePuedeAgregarUnidadConOroInsuficiente() {
        Jugador jugador = new Jugador("Nico", new Mapa());

        try {
            jugador.agregarUnidad(new Aldeano(jugador), new PlazaCentral(jugador));
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void agregarUnidadConOroSuficiente() throws FueraDeRangoException, PosicionOcupadaException {
        Jugador jugador = new Jugador("Nico", new Mapa());

        try {
            jugador.agregarUnidad(new ArmaDeAsedio(jugador), new Castillo(jugador));
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

}
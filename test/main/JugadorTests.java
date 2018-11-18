package main;

import excepciones.main.LimiteDePoblacionException;
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
import static org.junit.Assert.fail;

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
    public void noSePuedeAgregarUnidadConOroInsuficiente() throws LimiteDePoblacionException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico", mapa);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);

        try {
            mapa.colocarUnidad(plazaCentral, new Point2D.Double(1,1));
        } catch (FueraDeRangoException | PosicionOcupadaException e) {
            fail("Error Inesperado");
        }

        try {
            jugador.agregarUnidad(new Aldeano(jugador), plazaCentral);
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void agregarUnidadConOroSuficiente() throws LimiteDePoblacionException {
        Jugador jugador = new Jugador("Nico", new Mapa());

        try {
            jugador.agregarUnidad(new ArmaDeAsedio(jugador), new Castillo(jugador));
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

}
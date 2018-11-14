package main;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.EspacioInsuficienteException;
import unidades.edificio.PlazaCentral;
import unidades.milicia.Aldeano;

import org.junit.Test;
import static org.junit.Assert.*;

public class JugadorTest {

    @Test
    public void noSePuedeCobrarOroConOroInsuficiente() {
        Jugador jugador = new Jugador("Nico");

        try {
            jugador.cobrarOro(100);
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void cobrarOroConOroSuficiente() {
        Jugador jugador = new Jugador("Nico");
        jugador.recolectarOro(100);

        try {
            jugador.cobrarOro(100);
        } catch (OroInsuficienteException e) {
            fail("Error inesperado.");
        }
    }

    @Test
    public void noSePuedeAgregarUnidadConOroInsuficiente() {
        Jugador jugador = new Jugador("Nico");

        try {
            jugador.agregarUnidad(new Aldeano(jugador), new PlazaCentral(jugador));
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        } catch (EspacioInsuficienteException e) {
            fail("Error inesperado.");
        }
    }

    @Test
    public void agregarUnidadConOroSuficiente() {
        Jugador jugador = new Jugador("Nico");
        jugador.recolectarOro(25);

        try {
            jugador.agregarUnidad(new Aldeano(jugador), new PlazaCentral(jugador));
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        } catch (EspacioInsuficienteException e) {
            fail("Error inesperado.");
        }
    }

}
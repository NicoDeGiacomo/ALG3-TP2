package main;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.EspacioInsuficienteException;
import org.junit.jupiter.api.Test;
import unidades.edificio.PlazaCentral;
import unidades.milicia.Aldeano;

import static org.junit.jupiter.api.Assertions.*;

class JugadorTest {

    @Test
    void noSePuedeCobrarOroConOroInsuficiente() {
        Jugador jugador = new Jugador("Nico", new Mapa());

        try {
            jugador.cobrarOro(100);
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    void cobrarOroConOroSuficiente() {
        Jugador jugador = new Jugador("Nico", new Mapa());
        jugador.recolectarOro(100);

        try {
            jugador.cobrarOro(100);
        } catch (OroInsuficienteException e) {
            fail("Error inesperado.");
        }
    }

    @Test
    void noSePuedeAgregarUnidadConOroInsuficiente() {
        Jugador jugador = new Jugador("Nico", new Mapa());

        try {
            jugador.agregarUnidad(new Aldeano(jugador), new PlazaCentral(jugador));
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        } catch (EspacioInsuficienteException e) {
            fail("Error inesperado.");
        }
    }

    @Test
    void agregarUnidadConOroSuficiente() {
        Jugador jugador = new Jugador("Nico", new Mapa());
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
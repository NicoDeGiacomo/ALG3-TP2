package main;

import excepciones.main.OroInsuficienteException;
import unidades.edificio.PlazaCentral;
import unidades.milicia.Aldeano;

import org.junit.Test;
import static org.junit.Assert.*;

public class JugadorTests {

    @Test
    public void noSePuedeCobrarOroConOroInsuficiente() {
        Jugador jugador = new Jugador("Nico");

        try {
            jugador.cobrarOro(100);
        } catch (OroInsuficienteException e) {
            assertEquals("El precio del jugador es insuficiente.", e.getMessage());
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
            jugador.agregarUnidad(new Aldeano(jugador));
        } catch (OroInsuficienteException e) {
            assertEquals("El precio del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void agregarUnidadConOroSuficiente() {
        Jugador jugador = new Jugador("Nico");
        jugador.recolectarOro(25);

        try {
            jugador.agregarUnidad(new Aldeano(jugador));
        } catch (OroInsuficienteException e) {
            assertEquals("El precio del jugador es insuficiente.", e.getMessage());
        }
    }

}
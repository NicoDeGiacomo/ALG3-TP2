package main;

import excepciones.main.OroInsuficienteException;
import org.junit.jupiter.api.function.Executable;
import unidades.edificio.PlazaCentral;
import unidades.milicia.Aldeano;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;

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

        controlarErroresInesperados(() -> jugador.cobrarOro(100));
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

    private void controlarErroresInesperados(Executable ejecutable) {
        try {
            ejecutable.execute();
        } catch (Throwable throwable) {
            fail("Error inesperado", throwable);
        }
    }

}
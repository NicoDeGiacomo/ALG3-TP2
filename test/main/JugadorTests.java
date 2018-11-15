package main;

import excepciones.main.OroInsuficienteException;
import org.junit.Test;
import unidades.milicia.Aldeano;

import static org.junit.Assert.assertEquals;

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
    public void cobrarOroConOroSuficiente() throws OroInsuficienteException {
        Jugador jugador = new Jugador("Nico");
        jugador.recolectarOro(100);

        jugador.cobrarOro(100);
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
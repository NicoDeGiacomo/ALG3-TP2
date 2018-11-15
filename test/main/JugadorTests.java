package main;

import excepciones.main.OroInsuficienteException;
import excepciones.unidades.UnidadNoAgregadaException;
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
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void cobrarOroConOroSuficiente() throws OroInsuficienteException {
        Jugador jugador = new Jugador("Nico");
        jugador.recolectarOro(100);

        jugador.cobrarOro(100);
    }

    @Test
    public void noSePuedeDevolverElCostoDeUnaUnidadNoAgregada() throws OroInsuficienteException {
        Jugador jugador = new Jugador("Nico");
        try {
            jugador.devolverCostoDeUnidad(100, new Aldeano(jugador));
        } catch (UnidadNoAgregadaException e) {
            assertEquals("No se puede devolver el costo de una unidad que no le pertenece al jugador", e.getMessage());
        }
        try {
            jugador.cobrarOro(100);
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void devolverElCostoDeUnaUnidadAgregaElOroAlJugadorYRemueveALaUnidad() throws OroInsuficienteException, UnidadNoAgregadaException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        jugador.recolectarOro(25);
        jugador.agregarUnidad(aldeano);

        jugador.devolverCostoDeUnidad(25, aldeano);

        jugador.cobrarOro(25);
        try {
            jugador.devolverCostoDeUnidad(25, aldeano);
        } catch (UnidadNoAgregadaException e) {
            assertEquals("No se puede devolver el costo de una unidad que no le pertenece al jugador", e.getMessage());
        }
    }

    @Test
    public void noSePuedeAgregarUnidadConOroInsuficiente() {
        Jugador jugador = new Jugador("Nico");

        try {
            jugador.agregarUnidad(new Aldeano(jugador));
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void agregarUnidadConOroSuficiente() {
        Jugador jugador = new Jugador("Nico");
        jugador.recolectarOro(25);

        try {
            jugador.agregarUnidad(new Aldeano(jugador));
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

}
package main;

import excepciones.main.OroInsuficienteException;
import excepciones.unidades.UnidadNoAgregadaException;
import org.junit.Test;
import unidades.edificio.PlazaCentral;
import unidades.milicia.Aldeano;
import unidades.milicia.ArmaDeAsedio;

import static main.AlgoEmpiresTests.agregarCienDeOroAJugador;
import static org.junit.Assert.assertEquals;

public class JugadorTests {

    @Test
    public void noSePuedeCobrarOroConOroInsuficiente() {
        Jugador jugador = new Jugador("Nico");

        try {
            jugador.cobrarOro(new PlazaCentral(jugador));
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void cobrarOroConOroSuficiente() throws OroInsuficienteException {
        Jugador jugador = new Jugador("Nico");
        agregarCienDeOroAJugador(jugador);

        jugador.cobrarOro(new PlazaCentral(jugador));
    }

    @Test
    public void noSePuedeDevolverElCostoDeUnaUnidadNoAgregada() throws OroInsuficienteException {
        Jugador jugador = new Jugador("Nico");
        try {
            jugador.devolverCostoDeUnidad(new Aldeano(jugador));
        } catch (UnidadNoAgregadaException e) {
            assertEquals("No se puede devolver el costo de una unidad que no le pertenece al jugador", e.getMessage());
        }
    }

    @Test
    public void devolverElCostoDeUnaUnidadAgregaElOroAlJugadorYRemueveALaUnidad() throws OroInsuficienteException, UnidadNoAgregadaException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        jugador.agregarUnidad(aldeano);

        jugador.devolverCostoDeUnidad(aldeano);

        //Compruebo que siga teniendo 100 de oro
        jugador.agregarUnidad(aldeano);
        jugador.agregarUnidad(aldeano);
        jugador.agregarUnidad(aldeano);
        jugador.agregarUnidad(aldeano);
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

        try {
            jugador.agregarUnidad(new ArmaDeAsedio(jugador));
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

}
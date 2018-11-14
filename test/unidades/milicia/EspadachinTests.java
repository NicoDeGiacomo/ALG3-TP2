package unidades.milicia;

import excepciones.unidades.AtaqueIncorrectoException;
import main.Jugador;
import unidades.edificio.Cuartel;
import unidades.edificio.PlazaCentral;
import unidades.estados.unidades.Muerto;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EspadachinTests {

    @Test
    public void test01espadachinSonCreadosCorrectamente() {
        Espadachin espadachin = new Espadachin(new Jugador("Nico"));
        assertEquals(100, espadachin.verVida());
        assertEquals(1, espadachin.verTamanio());
        espadachin.ejecutarTareas();

    }

    @Test
    public void test02espadachinProbocaDanioAMilicias() {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico"));
        Espadachin espadachinRecibeDanio = new Espadachin(new Jugador("Nico"));
        try {
            espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        } catch (AtaqueIncorrectoException e) {
            fail("Error inesperado");
        }
        assertEquals(100, espadachinHaceDanio.verVida());
        assertEquals(75, espadachinRecibeDanio.verVida());
    }

    @Test
    public void test03espadachinProbocanDanioAEdificios() {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico"));
        PlazaCentral plaza = new PlazaCentral(new Jugador("Nico"));
        try {
            espadachinHaceDanio.provocarDanio(plaza);
        } catch (AtaqueIncorrectoException e) {
            fail("Error inesperado");
        }
        assertEquals(espadachinHaceDanio.verVida(), 100);
        assertEquals(435, plaza.verVida());
    }

    @Test
    public void test04espadachinEsDaniado() {
        Espadachin espadachin = new Espadachin(new Jugador("Nico"));
        assertEquals(100, espadachin.verVida());
        espadachin.recibirDanio(20);
        assertEquals(80, espadachin.verVida());
    }
/*
    TODO
    @Test
    public void test02espadachinNoProbocaDanioAUnidadesDeMismoJugador() {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico"));
        Espadachin espadachinRecibeDanio = new Espadachin(new Jugador("Nico"));
        espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        assertEquals(100, espadachinHaceDanio.verVida());
        assertEquals(100, espadachinRecibeDanio.verVida());
    }
*/

    @Test
    public void test05espadachinMataAMilicias() {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico"));
        Espadachin espadachinRecibeDanio = new Espadachin(new Jugador("Nico"));
        try {
            espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
            espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
            espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
            assertTrue(espadachinRecibeDanio.esMapeable());
            espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        } catch (AtaqueIncorrectoException e) {
            fail("Error inesperado");
        }
        assertEquals(100, espadachinHaceDanio.verVida());
        assertEquals(0, espadachinRecibeDanio.verVida());
        assertEquals(Muerto.class, espadachinRecibeDanio.verEstadoDeUnidad().getClass());
        assertFalse(espadachinRecibeDanio.esMapeable());
    }

    @Test
    public void test05espadachinDestruyeEdificio() {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico"));
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        cuartel.recibirDanio(235);
        assertTrue(cuartel.esMapeable());
        try {
            espadachinHaceDanio.provocarDanio(cuartel);
        } catch (AtaqueIncorrectoException e) {
            fail("Error inesperado");
        }
        assertEquals(100, espadachinHaceDanio.verVida());
        assertEquals(0, cuartel.verVida());
        assertEquals(Muerto.class, cuartel.verEstadoDeUnidad().getClass());
        assertFalse(cuartel.esMapeable());
    }
}

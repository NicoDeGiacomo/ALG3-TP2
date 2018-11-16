package unidades.milicia;

import excepciones.unidades.AtaqueIncorrectoException;
import main.Jugador;
import main.Mapa;
import unidades.edificio.Cuartel;
import unidades.edificio.PlazaCentral;
import unidades.estados.unidades.Muerto;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EspadachinTests {

    @Test
    public void test01espadachinSonCreadosCorrectamente() {
        Espadachin espadachin = new Espadachin(new Jugador("Nico", new Mapa()));
        assertEquals(100, espadachin.verVida());
        assertEquals(1, espadachin.verTamanio());
        espadachin.ejecutarTareas();

    }

    @Test
    public void test02espadachinProbocaDanioAMilicias() throws AtaqueIncorrectoException {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico", new Mapa()));
        Espadachin espadachinRecibeDanio = new Espadachin(new Jugador("Nico", new Mapa()));
        espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        assertEquals(100, espadachinHaceDanio.verVida());
        assertEquals(75, espadachinRecibeDanio.verVida());
    }

    @Test
    public void test03espadachinProbocanDanioAEdificios() throws AtaqueIncorrectoException {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico", new Mapa()));
        PlazaCentral plaza = new PlazaCentral(new Jugador("Nico", new Mapa()));
        espadachinHaceDanio.provocarDanio(plaza);
        assertEquals(espadachinHaceDanio.verVida(), 100);
        assertEquals(435, plaza.verVida());
    }

    @Test
    public void test04espadachinEsDaniado() {
        Espadachin espadachin = new Espadachin(new Jugador("Nico", new Mapa()));
        assertEquals(100, espadachin.verVida());
        espadachin.recibirDanio(20);
        assertEquals(80, espadachin.verVida());
    }

    @Test
    public void test05espadachinMataAMilicias() throws AtaqueIncorrectoException {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico", new Mapa()));
        Espadachin espadachinRecibeDanio = new Espadachin(new Jugador("Nico", new Mapa()));
        espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        assertTrue(espadachinRecibeDanio.esMapeable());
        espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        assertEquals(100, espadachinHaceDanio.verVida());
        assertEquals(0, espadachinRecibeDanio.verVida());
        assertEquals(Muerto.class, espadachinRecibeDanio.verEstadoDeUnidad().getClass());
        assertFalse(espadachinRecibeDanio.esMapeable());
    }

    @Test
    public void test06espadachinDestruyeEdificio() throws AtaqueIncorrectoException {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico", new Mapa()));
        Cuartel cuartel = new Cuartel(new Jugador("Nico", new Mapa()));
        cuartel.recibirDanio(235);
        assertTrue(cuartel.esMapeable());
        espadachinHaceDanio.provocarDanio(cuartel);
        assertEquals(100, espadachinHaceDanio.verVida());
        assertEquals(0, cuartel.verVida());
        assertEquals(Muerto.class, cuartel.verEstadoDeUnidad().getClass());
        assertFalse(cuartel.esMapeable());
    }
}

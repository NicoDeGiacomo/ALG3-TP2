package unidades.milicia;

import main.Jugador;
import main.Mapa;
import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.Cuartel;
import unidades.edificio.PlazaCentral;
import unidades.estados.unidades.Muerto;

public class EspadachinTests {

    @Test
    public void test01espadachinSonCreadosCorrectamente() {
        Espadachin espadachin = new Espadachin(new Jugador("Nico"));
        Assert.assertEquals(100, espadachin.verVida());
        Assert.assertEquals(1, espadachin.verTamanio());
        espadachin.ejecutarTareas();

    }

    @Test
    public void test02espadachinProbocaDanioAMilicias() {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico"));
        Espadachin espadachinRecibeDanio = new Espadachin(new Jugador("Nico"));
        espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        Assert.assertEquals(100, espadachinHaceDanio.verVida());
        Assert.assertEquals(75, espadachinRecibeDanio.verVida());
    }

    @Test
    public void test03espadachinProbocanDanioAEdificios() {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico"));
        PlazaCentral plaza = new PlazaCentral(new Jugador("Nico"));
        espadachinHaceDanio.provocarDanio(plaza);
        Assert.assertEquals(espadachinHaceDanio.verVida(), 100);
        Assert.assertEquals(435, plaza.verVida());
    }

    @Test
    public void test04espadachinEsDaniado() {
        Espadachin espadachin = new Espadachin(new Jugador("Nico"));
        Assert.assertEquals(100, espadachin.verVida());
        espadachin.recibirDanio(20);
        Assert.assertEquals(80, espadachin.verVida());
    }
/*
    TODO
    @Test
    public void test02espadachinNoProbocaDanioAUnidadesDeMismoJugador() {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico"));
        Espadachin espadachinRecibeDanio = new Espadachin(new Jugador("Nico"));
        espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        Assert.assertEquals(100, espadachinHaceDanio.verVida());
        Assert.assertEquals(100, espadachinRecibeDanio.verVida());
    }
*/

    @Test
    public void test05espadachinMataAMilicias() {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico"));
        Espadachin espadachinRecibeDanio = new Espadachin(new Jugador("Nico"));
        espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        Assert.assertTrue(espadachinRecibeDanio.esMapeable());
        espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        Assert.assertEquals(100, espadachinHaceDanio.verVida());
        Assert.assertEquals(0, espadachinRecibeDanio.verVida());
        Assert.assertEquals(Muerto.class, espadachinRecibeDanio.verEstadoDeUnidad().getClass() );
        Assert.assertFalse(espadachinRecibeDanio.esMapeable());
    }

    @Test
    public void test05espadachinDestruyeEdificio() {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico"));
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        cuartel.recibirDanio(235);
        Assert.assertTrue(cuartel.esMapeable());
        espadachinHaceDanio.provocarDanio(cuartel);
        Assert.assertEquals(100, espadachinHaceDanio.verVida());
        Assert.assertEquals(0, cuartel.verVida());
        Assert.assertEquals(Muerto.class, cuartel.verEstadoDeUnidad().getClass() );
        Assert.assertFalse(cuartel.esMapeable());
    }
}

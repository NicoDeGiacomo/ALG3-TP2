package unidades.milicia;

import main.Jugador;
import main.Mapa;
import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.PlazaCentral;

public class EspadachinTests {

    @Test
    public void espadachinSonCreadosCorrectamente() {
        Espadachin espadachin = new Espadachin(new Jugador("Nico"));
        Assert.assertEquals(100, espadachin.verVida());
        Assert.assertEquals(1, espadachin.verTamanio());
    }

    @Test
    public void espadachinProbocaDanioAMilicias() {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico"));
        Espadachin espadachinRecibeDanio = new Espadachin(new Jugador("Nico"));
        espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        Assert.assertEquals(100, espadachinHaceDanio.verVida());
        Assert.assertEquals(75, espadachinRecibeDanio.verVida());
    }

    @Test
    public void espadachinProbocanDanioAEdificios() {
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico"));
        PlazaCentral plaza = new PlazaCentral(new Jugador("Nico"));
        espadachinHaceDanio.provocarDanio(plaza);
        Assert.assertEquals(espadachinHaceDanio.verVida(), 100);
        Assert.assertEquals(435, plaza.verVida());
    }

    @Test
    public void espadachinEsDaniado() {
        Espadachin espadachin = new Espadachin(new Jugador("Nico"));
        Assert.assertEquals(100, espadachin.verVida());
        espadachin.recibirDanio(20);
        Assert.assertEquals(80, espadachin.verVida());
    }
}

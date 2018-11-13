package unidades.milicia;

import main.Jugador;
import main.Mapa;
import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.PlazaCentral;

public class ArqueroTests {

    @Test
    public void arqueroSonCreadosCorrectamente() {
        Arquero arquero = new Arquero(new Jugador("Nico"));
        Assert.assertEquals(75, arquero.verVida());
        Assert.assertEquals(1, arquero.verTamanio());
        arquero.ejecutarTareas();
    }

    @Test
    public void test01arqueroProbocanDanioAMilicias() {
        Arquero arqueroHaceDanio = new Arquero(new Jugador("Nico"));
        Arquero arqueroRecibeDanio = new Arquero(new Jugador("Nico"));
        arqueroHaceDanio.provocarDanio(arqueroRecibeDanio);
        Assert.assertEquals(75, arqueroHaceDanio.verVida());
        Assert.assertEquals(60, arqueroRecibeDanio.verVida());
    }

    @Test
    public void test02arqueroProbocanDanioAEdificios() {
        Arquero arqueroHaceDanio = new Arquero(new Jugador("Nico"));
        PlazaCentral plaza = new PlazaCentral(new Jugador("Nico"));
        arqueroHaceDanio.provocarDanio(plaza);
        Assert.assertEquals(75, arqueroHaceDanio.verVida());
        Assert.assertEquals(440, plaza.verVida());
    }

    @Test
    public void test03arqueroSonDaniadas() {
        Arquero arquero = new Arquero(new Jugador("Nico"));
        Assert.assertEquals(75, arquero.verVida());
        arquero.recibirDanio(20);
        Assert.assertEquals(55, arquero.verVida());
    }
}

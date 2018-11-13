package unidades.milicia;

import main.Jugador;
import main.Mapa;
import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.PlazaCentral;

public class ArqueroTests {

    @Test
    public void arqueroSonCreadosCorrectamente() {
        Arquero arquero = new Arquero(new Jugador("Nico", new Mapa()));
        Assert.assertEquals(75, arquero.verVida());
        Assert.assertEquals(1, arquero.verTamanio());
    }

    @Test
    public void arqueroProbocanDanioAMilicias() {
        Arquero arqueroHaceDanio = new Arquero(new Jugador("Nico", new Mapa()));
        Arquero arqueroRecibeDanio = new Arquero(new Jugador("Nico", new Mapa()));
        arqueroHaceDanio.provocarDanio(arqueroRecibeDanio);
        Assert.assertEquals(75, arqueroHaceDanio.verVida());
        Assert.assertEquals(60, arqueroRecibeDanio.verVida());
    }

    @Test
    public void arqueroProbocanDanioAEdificios() {
        Arquero arqueroHaceDanio = new Arquero(new Jugador("Nico", new Mapa()));
        PlazaCentral plaza = new PlazaCentral(new Jugador("Nico", new Mapa()));
        arqueroHaceDanio.provocarDanio(plaza);
        Assert.assertEquals(75, arqueroHaceDanio.verVida());
        Assert.assertEquals(440, plaza.verVida());
    }

    @Test
    public void arqueroSonDaniadas() {
        Arquero arquero = new Arquero(new Jugador("Nico", new Mapa()));
        Assert.assertEquals(75, arquero.verVida());
        arquero.recibirDanio(20);
        Assert.assertEquals(55, arquero.verVida());
    }
}

package unidades.edificio;

import main.Jugador;
import main.Mapa;
import org.junit.Assert;
import org.junit.Test;

public class PlazaCentralTests {

    @Test
    public void plazaCentralSonCreadosCorrectamente() {
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Nico"));
        Assert.assertEquals(450, plazaCentral.verVida());
        Assert.assertEquals(4, plazaCentral.verTamanio());
    }

    @Test
    public void plazaCentralCreaUnidad() {
        //ToDo
    }

    @Test
    public void plazaCentralSonDaniadas() {
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Nico"));
        Assert.assertEquals(450, plazaCentral.verVida());
        plazaCentral.recibirDanio(20);
        Assert.assertEquals(430, plazaCentral.verVida());
    }
}

package unidades.edificio;

import main.Jugador;
import main.Mapa;
import org.junit.Assert;
import org.junit.Test;

public class CastilloTests {

    @Test
    public void castilloSonCreadosCorrectamente() {
        Castillo castillo = new Castillo(new Jugador("Nico"));
        Assert.assertEquals(1000, castillo.verVida());
        Assert.assertEquals(8, castillo.verTamanio());
    }

    @Test
    public void castilloCreaUnidad() {
        //ToDo
    }

    @Test
    public void castilloSonDaniadas() {
        Castillo castillo = new Castillo(new Jugador("Nico"));
        Assert.assertEquals(1000, castillo.verVida());
        castillo.recibirDanio(20);
        Assert.assertEquals(980, castillo.verVida());
    }
}

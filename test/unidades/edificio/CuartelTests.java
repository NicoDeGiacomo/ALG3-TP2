package unidades.edificio;

import main.Jugador;
import main.Mapa;
import org.junit.Assert;
import org.junit.Test;

public class CuartelTests {

    @Test
    public void cuartelSonCreadosCorrectamente() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico", new Mapa()));
        Assert.assertEquals(250, cuartel.verVida());
        Assert.assertEquals(4, cuartel.verTamanio());
    }

    @Test
    public void cuartelCreaUnidad() {
        //ToDo
    }

    @Test
    public void cuartelSonDaniadas() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico", new Mapa()));
        Assert.assertEquals(250, cuartel.verVida());
        cuartel.recibirDanio(20);
        Assert.assertEquals(230, cuartel.verVida());
    }
}

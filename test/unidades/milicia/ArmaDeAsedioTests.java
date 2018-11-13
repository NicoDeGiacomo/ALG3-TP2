package unidades.milicia;

import main.Jugador;
import main.Mapa;
import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.PlazaCentral;

public class ArmaDeAsedioTests {

    @Test
    public void armaDeAsedioSonCreadosCorrectamente() {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico", new Mapa()));
        Assert.assertEquals(150, armaDeAsedio.verVida());
        Assert.assertEquals(1, armaDeAsedio.verTamanio());
    }

    @Test
    public void armaDeAsedioNoProbocaDanioAMilicias() {
        ArmaDeAsedio armaDeAsedioHaceDanio = new ArmaDeAsedio(new Jugador("Nico", new Mapa()));
        ArmaDeAsedio armaDeAsedioRecibeDanio = new ArmaDeAsedio(new Jugador("Nico", new Mapa()));
        Aldeano aldeano = new Aldeano(new Jugador("Nico", new Mapa()));
        armaDeAsedioHaceDanio.montarArma(aldeano);
        armaDeAsedioHaceDanio.provocarDanio(armaDeAsedioRecibeDanio);
        Assert.assertEquals(150, armaDeAsedioHaceDanio.verVida());
        Assert.assertEquals(150, armaDeAsedioRecibeDanio.verVida());
    }

    @Test
    public void armaDeAsedioProbocaDanioAEdificios() {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico", new Mapa()));
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Nico", new Mapa()));
        Aldeano aldeano = new Aldeano(new Jugador("Nico", new Mapa()));
        armaDeAsedio.montarArma(aldeano);
        armaDeAsedio.provocarDanio(plazaCentral);
        Assert.assertEquals(150, armaDeAsedio.verVida());
        Assert.assertEquals(375, plazaCentral.verVida());
    }

    @Test
    public void armaDeAsedioEsDaniada() {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico", new Mapa()));
        Assert.assertEquals(150, armaDeAsedio.verVida());
        armaDeAsedio.recibirDanio(20);
        Assert.assertEquals(130, armaDeAsedio.verVida());
    }

    @Test
    public void armaDeAsedioNoEstaMontada() {
        //ToDo
    }


}

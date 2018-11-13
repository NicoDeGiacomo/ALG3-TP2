package unidades.milicia;

import main.Jugador;
import main.Mapa;
import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.Castillo;
import unidades.edificio.Cuartel;
import unidades.edificio.PlazaCentral;

public class AldeanoTests {

    @Test
    public void aldeanoSonCreadosCorrectamente() {
        Aldeano aldeano = new Aldeano(new Jugador("Nico"));
        Assert.assertEquals(50, aldeano.verVida());
        Assert.assertEquals(1, aldeano.verTamanio());
    }

    @Test
    public void aldeanoNoHaceDanio() {
        Aldeano aldeanoHaceDanio = new Aldeano(new Jugador("Nico"));
        Aldeano aldeanoRecibeDanio = new Aldeano(new Jugador("Nico"));
        aldeanoHaceDanio.provocarDanio(aldeanoRecibeDanio);
        Assert.assertEquals(50, aldeanoHaceDanio.verVida());
        Assert.assertEquals(50, aldeanoRecibeDanio.verVida());
    }

    @Test
    public void aldeanoSonDaniadas() {
        Aldeano aldeano = new Aldeano(new Jugador("Nico"));
        Assert.assertEquals(50, aldeano.verVida());
        aldeano.recibirDanio(20);
        Assert.assertEquals(30, aldeano.verVida());
    }

    @Test
    public void aldeanoArreglarNoHaceNada() {
        Aldeano aldeano = new Aldeano(new Jugador("Nico"));
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Nico"));
        Castillo castillo = new Castillo(new Jugador("Nico"));
        aldeano.arreglar(cuartel);
        aldeano.arreglar(plazaCentral);
        aldeano.arreglar(castillo);
        Assert.assertEquals(250, cuartel.verVida());
        Assert.assertEquals(450, plazaCentral.verVida());
        Assert.assertEquals(1000, castillo.verVida());
    }

    /*@Test TODO: NICO. ESTO FALLA PORQUE SE TIENE QUE PASAR EL TURNO PARA QUE EL ALDEANO REPARE EL EDIFICIO
    public void aldeanoArreglar() {
        Aldeano aldeano = new Aldeano(new Jugador("Nico"));
        Cuartel cuartel = new Cuartel();
        PlazaCentral plazaCentral = new PlazaCentral();
        Castillo castillo = new Castillo();
        cuartel.recibirDanio(100);
        plazaCentral.recibirDanio(100);
        castillo.recibirDanio(100);
        aldeano.arreglar(cuartel);
        aldeano.arreglar(plazaCentral);
        aldeano.arreglar(castillo);
        Assert.assertEquals(cuartel.verVida(), 200);
        Assert.assertEquals(plazaCentral.verVida(), 375);
        Assert.assertEquals(castillo.verVida(), 915);
    }*/

    @Test
    public void aldeanoArreglandoTieneVidaMaximaYCambiaDeEstado() {
        //ToDo

    }

    @Test
    public void aldeanoConstruyendoPlaza() {
        //ToDo

    }

    @Test
    public void aldeanoConstruyendoCuartel() {
        //ToDo

    }

    @Test
    public void aldeanoObtieneOro() {
        //ToDo

    }

    @Test
    public void aldeanoEstaOcupado() {
        //ToDo

    }

}

package unidades.milicia;

import main.Jugador;
import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.PlazaCentral;

public class ArqueroTests {

    @Test
    public void arqueroSonCreadosCorrectamente(){
        Arquero arquero = new Arquero(new Jugador("Nico")) ;
        Assert.assertEquals( arquero.verVida() , 75 );
        Assert.assertEquals( arquero.verTamanio() , 1 );
    }

    @Test
    public void arqueroProbocanDanioAMilicias(){
        Arquero arqueroHaceDanio = new Arquero(new Jugador("Nico")) ;
        Arquero arqueroRecibeDanio = new Arquero(new Jugador("Nico")) ;
        arqueroHaceDanio.provocarDanio(arqueroRecibeDanio);
        Assert.assertEquals( arqueroHaceDanio.verVida() , 75 );
        Assert.assertEquals( arqueroRecibeDanio.verVida() , 60 );
    }

    @Test
    public void arqueroProbocanDanioAEdificios(){
        Arquero arqueroHaceDanio = new Arquero(new Jugador("Nico")) ;
        PlazaCentral plaza = new PlazaCentral(new Jugador("Nico")) ;
        arqueroHaceDanio.provocarDanio(plaza);
        Assert.assertEquals( arqueroHaceDanio.verVida() , 75 );
        Assert.assertEquals( plaza.verVida() , 440 );
    }

    @Test
    public void arqueroSonDaniadas(){
        Arquero arquero = new Arquero(new Jugador("Nico")) ;
        Assert.assertEquals( arquero.verVida() , 75 );
        arquero.recibirDanio(20);
        Assert.assertEquals( arquero.verVida()   , 55 );
    }
}

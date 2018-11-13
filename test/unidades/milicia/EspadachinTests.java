package unidades.milicia;

import main.Jugador;
import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.PlazaCentral;
import unidades.milicia.Espadachin;

public class EspadachinTests {

    @Test
    public void espadachinSonCreadosCorrectamente(){
        Espadachin espadachin = new Espadachin(new Jugador("Nico")) ;
        Assert.assertEquals( espadachin.verVida() , 100 );
        Assert.assertEquals( espadachin.verTamanio() , 1 );
    }

    @Test
    public void espadachinProbocaDanioAMilicias(){
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico")) ;
        Espadachin espadachinRecibeDanio = new Espadachin(new Jugador("Nico")) ;
        espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        Assert.assertEquals( espadachinHaceDanio.verVida() , 100 );
        Assert.assertEquals( espadachinRecibeDanio.verVida() , 75 );
    }

    @Test
    public void espadachinProbocanDanioAEdificios(){
        Espadachin espadachinHaceDanio = new Espadachin(new Jugador("Nico")) ;
        PlazaCentral plaza = new PlazaCentral(new Jugador("Nico")) ;
        espadachinHaceDanio.provocarDanio(plaza);
        Assert.assertEquals( 100 , espadachinHaceDanio.verVida());
        Assert.assertEquals( plaza.verVida() , 435 );
    }

    @Test
    public void espadachinEsDaniado(){
        Espadachin espadachin = new Espadachin(new Jugador("Nico")) ;
        Assert.assertEquals( espadachin.verVida() , 100 );
        espadachin.recibirDanio(20);
        Assert.assertEquals( espadachin.verVida()   , 80 );
    }
}

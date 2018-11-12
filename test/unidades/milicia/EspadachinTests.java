package unidades.milicia;

import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.PlazaCentral;
import unidades.milicia.Espadachin;

public class EspadachinTests {

    @Test
    public void espadachinSonCreadosCorrectamente(){
        Espadachin espadachin = new Espadachin() ;
        Assert.assertEquals( espadachin.verVida() , 100 );
        Assert.assertEquals( espadachin.verTamanio() , 1 );
    }

    @Test
    public void espadachinProbocaDanioAMilicias(){
        Espadachin espadachinHaceDanio = new Espadachin() ;
        Espadachin espadachinRecibeDanio = new Espadachin() ;
        espadachinHaceDanio.provocarDanio(espadachinRecibeDanio);
        Assert.assertEquals( espadachinHaceDanio.verVida() , 100 );
        Assert.assertEquals( espadachinRecibeDanio.verVida() , 75 );
    }

    @Test
    public void arqueroProbocanDanioAEdificios(){
        Espadachin espadachinHaceDanio = new Espadachin() ;
        PlazaCentral plaza = new PlazaCentral() ;
        espadachinHaceDanio.provocarDanio(plaza);
        Assert.assertEquals( 100 , espadachinHaceDanio.verVida());
        Assert.assertEquals( plaza.verVida() , 435 );
    }

    @Test
    public void unidadesSonDaniadas(){
        Espadachin espadachin = new Espadachin() ;
        Assert.assertEquals( espadachin.verVida() , 100 );
        espadachin.recibirDanio(20);
        Assert.assertEquals( espadachin.verVida()   , 80 );
    }
}

package unidades.edificio;

import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.Castillo;

public class CastilloTests {

    @Test
    public void castilloSonCreadosCorrectamente(){
        Castillo castillo = new Castillo() ;
        Assert.assertEquals( castillo.verVida() , 1000 );
        Assert.assertEquals( castillo.verTamanio() , 8 );
    }

    @Test
    public void castilloCreaUnidad(){
        //ToDo
            }

    @Test
    public void castilloSonDaniadas(){
        Castillo castillo = new Castillo() ;
        Assert.assertEquals( castillo.verVida() , 1000);
        castillo.recibirDanio(20);
        Assert.assertEquals( castillo.verVida()   , 980 );
    }
}

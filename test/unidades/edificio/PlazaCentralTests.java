package unidades.edificio;

import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.PlazaCentral;

public class PlazaCentralTests {

    @Test
    public void plazaCentralSonCreadosCorrectamente(){
        PlazaCentral plazaCentral = new PlazaCentral() ;
        Assert.assertEquals( plazaCentral.verVida() , 450 );
        Assert.assertEquals( plazaCentral.verTamanio() , 4 );
    }

    @Test
    public void plazaCentralCreaUnidad(){
        //ToDo
            }

    @Test
    public void plazaCentralSonDaniadas(){
        PlazaCentral plazaCentral = new PlazaCentral() ;
        Assert.assertEquals( plazaCentral.verVida() , 450 );
        plazaCentral.recibirDanio(20);
        Assert.assertEquals( plazaCentral.verVida()   , 430 );
    }
}
package unidades.edificio;

import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.Cuartel;

public class CuartelTests {

    @Test
    public void cuartelSonCreadosCorrectamente(){
        Cuartel cuartel = new Cuartel() ;
        Assert.assertEquals( cuartel.verVida() , 250 );
        Assert.assertEquals( cuartel.verTamanio() , 4 );
    }

    @Test
    public void cuartelCreaUnidad(){
        //ToDo
            }

    @Test
    public void cuartelSonDaniadas(){
        Cuartel cuartel = new Cuartel() ;
        Assert.assertEquals( cuartel.verVida() , 250 );
        cuartel.recibirDanio(20);
        Assert.assertEquals( cuartel.verVida()   , 230 );
    }
}

package unidades.milicia;

import main.Jugador;
import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.Castillo;
import unidades.edificio.Cuartel;
import unidades.edificio.PlazaCentral;

public class AldeanoTests {

    @Test
    public void aldeanoSonCreadosCorrectamente(){
        Aldeano aldeano = new Aldeano(new Jugador("Nico")) ;
        Assert.assertEquals( aldeano.verVida() , 50 );
        Assert.assertEquals( aldeano.verTamanio() , 1 );
    }

    @Test
    public void aldeanoNoHaceDanio(){
        Aldeano aldeanoHaceDanio = new Aldeano(new Jugador("Nico")) ;
        Aldeano aldeanoRecibeDanio = new Aldeano(new Jugador("Nico")) ;
        aldeanoHaceDanio.provocarDanio(aldeanoRecibeDanio);
        Assert.assertEquals( aldeanoHaceDanio.verVida() , 50 );
        Assert.assertEquals( aldeanoRecibeDanio.verVida() , 50 );
    }

    @Test
    public void aldeanoSonDaniadas(){
        Aldeano aldeano = new Aldeano(new Jugador("Nico")) ;
        Assert.assertEquals( aldeano.verVida() , 50 );
        aldeano.recibirDanio(20);
        Assert.assertEquals( aldeano.verVida()   , 30 );
    }

    @Test
    public void aldeanoArreglarNoHaceNada(){
        Aldeano aldeano = new Aldeano(new Jugador("Nico")) ;
        Cuartel cuartel = new Cuartel() ;
        PlazaCentral plazaCentral = new PlazaCentral() ;
        Castillo castillo = new Castillo() ;
        aldeano.arreglar(cuartel) ;
        aldeano.arreglar(plazaCentral) ;
        aldeano.arreglar(castillo) ;
        Assert.assertEquals( cuartel.verVida()   , 250 );
        Assert.assertEquals( plazaCentral.verVida()   , 450 );
        Assert.assertEquals( castillo.verVida()   , 1000 );
    }

    @Test
    public void aldeanoArreglar(){
        Aldeano aldeano = new Aldeano(new Jugador("Nico")) ;
        Cuartel cuartel = new Cuartel() ;
        PlazaCentral plazaCentral = new PlazaCentral() ;
        Castillo castillo = new Castillo() ;
        cuartel.recibirDanio(100);
        plazaCentral.recibirDanio(100);
        castillo.recibirDanio(100);
        aldeano.arreglar(cuartel) ;
        aldeano.arreglar(plazaCentral) ;
        aldeano.arreglar(castillo) ;
        Assert.assertEquals( cuartel.verVida()   , 200 );
        Assert.assertEquals( plazaCentral.verVida()   , 375 );
        Assert.assertEquals( castillo.verVida()   , 915 );
    }

    @Test
    public void aldeanoArreglandoTieneVidaMaximaYCambiaDeEstado(){
        //ToDo

    }

    @Test
    public void aldeanoConstruyendoPlaza(){
        //ToDo

    }

    @Test
    public void aldeanoConstruyendoCuartel(){
        //ToDo

    }

    @Test
    public void aldeanoObtieneOro(){
        //ToDo

    }

    @Test
    public void aldeanoEstaOcupado(){
        //ToDo

    }

}

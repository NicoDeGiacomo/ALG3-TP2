package unidades.milicia;

import main.Jugador;
import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.PlazaCentral;

public class ArmaDeAsedioTests {

    @Test
    public void armaDeAsedioSonCreadosCorrectamente(){
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico")) ;
        Assert.assertEquals( armaDeAsedio.verVida() , 150 );
        Assert.assertEquals( armaDeAsedio.verTamanio() , 1 );
    }

    @Test
    public void armaDeAsedioNoProbocaDanioAMilicias(){
        ArmaDeAsedio armaDeAsedioHaceDanio = new ArmaDeAsedio(new Jugador("Nico")) ;
        ArmaDeAsedio armaDeAsedioRecibeDanio = new ArmaDeAsedio(new Jugador("Nico")) ;
        Aldeano aldeano = new Aldeano(new Jugador("Nico"));
        armaDeAsedioHaceDanio.montarArma(aldeano);
        armaDeAsedioHaceDanio.provocarDanio(armaDeAsedioRecibeDanio);
        Assert.assertEquals( armaDeAsedioHaceDanio.verVida() , 150 );
        Assert.assertEquals( armaDeAsedioRecibeDanio.verVida() , 150 );
    }

    @Test
    public void armaDeAsedioProbocaDanioAEdificios(){
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico")) ;
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Nico")) ;
        Aldeano aldeano = new Aldeano(new Jugador("Nico"));
        armaDeAsedio.montarArma(aldeano);
        armaDeAsedio.provocarDanio(plazaCentral);
        Assert.assertEquals( armaDeAsedio.verVida() , 150 );
        Assert.assertEquals( plazaCentral.verVida() , 375 );
    }

    @Test
    public void armaDeAsedioEsDaniada(){
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico")) ;
        Assert.assertEquals( armaDeAsedio.verVida() , 150 );
        armaDeAsedio.recibirDanio(20);
        Assert.assertEquals( armaDeAsedio.verVida()   , 130 );
    }

    @Test
    public void armaDeAsedioNoEstaMontada(){
        //ToDo
    }


}

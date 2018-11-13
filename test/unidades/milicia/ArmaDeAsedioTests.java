package unidades.milicia;

import excepciones.unidades.ArmaDeAsedioYaDesmontadaException;
import excepciones.unidades.ArmaDeAsedioYaMontadaException;
import main.Jugador;
import main.Mapa;
import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.PlazaCentral;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArmaDeAsedioTests {

    @Test
    public void armaDeAsedioSonCreadosCorrectamente() throws ArmaDeAsedioYaMontadaException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico"));
        Assert.assertEquals(true, armaDeAsedio.esMovible());
        Assert.assertEquals(150, armaDeAsedio.verVida());
        Assert.assertEquals(1, armaDeAsedio.verTamanio());
        armaDeAsedio.montarArma();
        Assert.assertEquals(false, armaDeAsedio.esMovible());
        armaDeAsedio.ejecutarTareas();
    }

    @Test
    public void test01armaDeAsedioNoProbocaDanioAMilicias() throws ArmaDeAsedioYaMontadaException {
        ArmaDeAsedio armaDeAsedioHaceDanio = new ArmaDeAsedio(new Jugador("Nico"));
        ArmaDeAsedio armaDeAsedioRecibeDanio = new ArmaDeAsedio(new Jugador("Peter"));
        armaDeAsedioHaceDanio.montarArma();
        armaDeAsedioHaceDanio.provocarDanio(armaDeAsedioRecibeDanio);
        Assert.assertEquals(150, armaDeAsedioHaceDanio.verVida());
        Assert.assertEquals(150, armaDeAsedioRecibeDanio.verVida());
    }

    @Test
    public void test02armaDeAsedioProbocaDanioAEdificios() throws ArmaDeAsedioYaMontadaException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico"));
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Peter"));
        armaDeAsedio.montarArma();
        armaDeAsedio.provocarDanio(plazaCentral);
        Assert.assertEquals(150, armaDeAsedio.verVida());
        Assert.assertEquals(375, plazaCentral.verVida());
    }

    @Test
    public void test03armaDeAsedioEsDaniada() {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico"));
        Assert.assertEquals(150, armaDeAsedio.verVida());
        armaDeAsedio.recibirDanio(20);
        Assert.assertEquals(130, armaDeAsedio.verVida());
    }

    @Test
    public void test04armaDeAsedioNoEstaMontada() throws ArmaDeAsedioYaMontadaException, ArmaDeAsedioYaDesmontadaException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico"));
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Peter"));
        armaDeAsedio.montarArma();
        armaDeAsedio.desmontarArma();
        armaDeAsedio.provocarDanio(plazaCentral);
        Assert.assertEquals(150, armaDeAsedio.verVida());
        Assert.assertEquals(450, plazaCentral.verVida());
    }

    @Test
    public void test05armaDeAsedioYaEstaDesmontada() {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico"));
        try {
            armaDeAsedio.desmontarArma();
        } catch (ArmaDeAsedioYaDesmontadaException e) {
            assertEquals("El arma de asedio se encuentra desmontada.", e.getMessage());
        }
    }

    @Test
    public void test06armaDeAsedioYaEstaMontada() throws ArmaDeAsedioYaMontadaException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico"));
        armaDeAsedio.montarArma();
        try {
            armaDeAsedio.montarArma();
        } catch (ArmaDeAsedioYaMontadaException e) {
            assertEquals("El arma de asedio se encuentra montada.", e.getMessage());
        }
    }


}

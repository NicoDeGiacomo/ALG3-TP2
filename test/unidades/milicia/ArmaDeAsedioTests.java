package unidades.milicia;

import excepciones.unidades.ArmaDeAsedioYaDesmontadaException;
import excepciones.unidades.ArmaDeAsedioYaMontadaException;
import excepciones.unidades.AtaqueIncorrectoException;
import main.Jugador;
import unidades.edificio.PlazaCentral;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArmaDeAsedioTests {

    @Test
    public void armaDeAsedioSonCreadosCorrectamente() throws ArmaDeAsedioYaMontadaException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico"));
        assertTrue(armaDeAsedio.esMovible());
        assertEquals(150, armaDeAsedio.verVida());
        assertEquals(1, armaDeAsedio.verTamanio());

        armaDeAsedio.montarArma();

        assertFalse(armaDeAsedio.esMovible());
    }

    @Test
    public void test01armaDeAsedioNoProbocaDanioAMilicias() throws ArmaDeAsedioYaMontadaException {
        ArmaDeAsedio armaDeAsedioHaceDanio = new ArmaDeAsedio(new Jugador("Nico"));
        ArmaDeAsedio armaDeAsedioRecibeDanio = new ArmaDeAsedio(new Jugador("Peter"));
        armaDeAsedioHaceDanio.montarArma();

        try {
            armaDeAsedioHaceDanio.provocarDanio(armaDeAsedioRecibeDanio);
        } catch (AtaqueIncorrectoException e) {
            assertEquals("El arma de asedio solo puede atacar edificios", e.getMessage());
        }

        assertEquals(150, armaDeAsedioHaceDanio.verVida());
        assertEquals(150, armaDeAsedioRecibeDanio.verVida());
    }

    @Test
    public void test02armaDeAsedioProbocaDanioAEdificios() throws ArmaDeAsedioYaMontadaException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico"));
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Peter"));
        armaDeAsedio.montarArma();

        try {
            armaDeAsedio.provocarDanio(plazaCentral);
        } catch (AtaqueIncorrectoException e) {
            fail("Error inesperado");
        }

        assertEquals(150, armaDeAsedio.verVida());
        assertEquals(375, plazaCentral.verVida());
    }

    @Test
    public void test03armaDeAsedioEsDaniada() {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico"));
        assertEquals(150, armaDeAsedio.verVida());
        armaDeAsedio.recibirDanio(20);
        assertEquals(130, armaDeAsedio.verVida());
    }

    @Test
    public void test04armaDeAsedioNoEstaMontada() throws ArmaDeAsedioYaMontadaException, ArmaDeAsedioYaDesmontadaException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico"));
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Peter"));
        armaDeAsedio.montarArma();
        armaDeAsedio.desmontarArma();

        try {
            armaDeAsedio.provocarDanio(plazaCentral);
        } catch (AtaqueIncorrectoException e) {
            fail("Error inesperado");
        }

        assertEquals(150, armaDeAsedio.verVida());
        assertEquals(450, plazaCentral.verVida());
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

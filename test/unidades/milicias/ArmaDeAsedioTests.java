package unidades.milicias;

import excepciones.unidades.ArmaDeAsedioYaDesmontadaException;
import excepciones.unidades.ArmaDeAsedioYaMontadaException;
import excepciones.unidades.AtaqueIncorrectoException;
import main.Jugador;
import main.Mapa;
import unidades.edificios.PlazaCentral;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ArmaDeAsedioTests {

    @Test
    public void test01armaDeAsedioSonCreadosCorrectamente() throws ArmaDeAsedioYaMontadaException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico", mock(Mapa.class)));
        assertTrue(armaDeAsedio.esMovible());
        assertEquals(150, armaDeAsedio.verVida());
        assertEquals(1, armaDeAsedio.verTamanio());
        armaDeAsedio.montarArma();
        assertFalse(armaDeAsedio.esMovible());
        armaDeAsedio.ejecutarTareas();
    }

    @Test
    public void test02armaDeAsedioNoProbocaDanioAMilicias() throws ArmaDeAsedioYaMontadaException {
        ArmaDeAsedio armaDeAsedioHaceDanio = new ArmaDeAsedio(new Jugador("Nico", mock(Mapa.class)));
        ArmaDeAsedio armaDeAsedioRecibeDanio = new ArmaDeAsedio(new Jugador("Peter", mock(Mapa.class)));
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
    public void test03armaDeAsedioProbocaDanioAEdificios() throws ArmaDeAsedioYaMontadaException, AtaqueIncorrectoException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico", mock(Mapa.class)));
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Peter", mock(Mapa.class)));
        armaDeAsedio.montarArma();

        armaDeAsedio.provocarDanio(plazaCentral);

        assertEquals(150, armaDeAsedio.verVida());
        assertEquals(375, plazaCentral.verVida());
    }

    @Test
    public void test04armaDeAsedioEsDaniada() {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico", mock(Mapa.class)));
        assertEquals(150, armaDeAsedio.verVida());
        armaDeAsedio.recibirDanio(20);
        assertEquals(130, armaDeAsedio.verVida());
    }

    @Test
    public void test05armaDeAsedioNoEstaMontada() throws ArmaDeAsedioYaMontadaException, ArmaDeAsedioYaDesmontadaException, AtaqueIncorrectoException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico", mock(Mapa.class)));
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Peter", mock(Mapa.class)));
        armaDeAsedio.montarArma();
        armaDeAsedio.desmontarArma();

        armaDeAsedio.provocarDanio(plazaCentral);

        assertEquals(150, armaDeAsedio.verVida());
        assertEquals(450, plazaCentral.verVida());
    }

    @Test
    public void test06armaDeAsedioYaEstaDesmontada() {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico", mock(Mapa.class)));

        try {
            armaDeAsedio.desmontarArma();
        } catch (ArmaDeAsedioYaDesmontadaException e) {
            assertEquals("El arma de asedio se encuentra desmontada.", e.getMessage());
        }
    }

    @Test
    public void test07armaDeAsedioYaEstaMontada() throws ArmaDeAsedioYaMontadaException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico", mock(Mapa.class)));
        armaDeAsedio.montarArma();

        try {
            armaDeAsedio.montarArma();
        } catch (ArmaDeAsedioYaMontadaException e) {
            assertEquals("El arma de asedio se encuentra montada.", e.getMessage());
        }
    }


}

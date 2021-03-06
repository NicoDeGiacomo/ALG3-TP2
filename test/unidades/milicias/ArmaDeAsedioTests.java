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
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(mock(Jugador.class));
        assertTrue(armaDeAsedio.esMovible());
        assertEquals(150, armaDeAsedio.verVida());
        assertEquals(1, armaDeAsedio.verTamanio());
        armaDeAsedio.montarArma();
        assertFalse(armaDeAsedio.esMovible());
        armaDeAsedio.ejecutarTareas();
    }

    @Test
    public void test02armaDeAsedioNoProbocaDanioAMilicias() throws ArmaDeAsedioYaMontadaException {
        ArmaDeAsedio armaDeAsedioHaceDanio = new ArmaDeAsedio(mock(Jugador.class));
        ArmaDeAsedio armaDeAsedioRecibeDanio = new ArmaDeAsedio(mock(Jugador.class));
        armaDeAsedioHaceDanio.montarArma();

        try {
            armaDeAsedioHaceDanio.provocarDanio(armaDeAsedioRecibeDanio);
        } catch (AtaqueIncorrectoException e) {
            assertEquals("El Arma de Asedio SOLO puede atacar Edificios!", e.getMessage());
        }

        assertEquals(150, armaDeAsedioHaceDanio.verVida());
        assertEquals(150, armaDeAsedioRecibeDanio.verVida());
    }

    @Test
    public void test03armaDeAsedioProbocaDanioAEdificios() throws ArmaDeAsedioYaMontadaException, AtaqueIncorrectoException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(mock(Jugador.class));
        PlazaCentral plazaCentral = new PlazaCentral(mock(Jugador.class));
        armaDeAsedio.montarArma();

        armaDeAsedio.provocarDanio(plazaCentral);

        assertEquals(150, armaDeAsedio.verVida());
        assertEquals(375, plazaCentral.verVida());
    }

    @Test
    public void test04armaDeAsedioEsDaniada() {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(mock(Jugador.class));
        assertEquals(150, armaDeAsedio.verVida());
        armaDeAsedio.recibirDanio(20);
        assertEquals(130, armaDeAsedio.verVida());
    }

    @Test
    public void test05armaDeAsedioNoEstaMontada() throws ArmaDeAsedioYaMontadaException, ArmaDeAsedioYaDesmontadaException, AtaqueIncorrectoException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(mock(Jugador.class));
        PlazaCentral plazaCentral = new PlazaCentral(mock(Jugador.class));
        armaDeAsedio.montarArma();
        armaDeAsedio.desmontarArma();

        armaDeAsedio.provocarDanio(plazaCentral);

        assertEquals(150, armaDeAsedio.verVida());
        assertEquals(450, plazaCentral.verVida());
    }

    @Test
    public void test06armaDeAsedioYaEstaDesmontada() {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(mock(Jugador.class));

        try {
            armaDeAsedio.desmontarArma();
        } catch (ArmaDeAsedioYaDesmontadaException e) {
            assertEquals("El Arma de Asedio se encuentra Desmontada.", e.getMessage());
        }
    }

    @Test
    public void test07armaDeAsedioYaEstaMontada() throws ArmaDeAsedioYaMontadaException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(mock(Jugador.class));
        armaDeAsedio.montarArma();

        try {
            armaDeAsedio.montarArma();
        } catch (ArmaDeAsedioYaMontadaException e) {
            assertEquals("El Arma de Asedio se encuentra Montada.", e.getMessage());
        }
    }


}

package unidades.edificios;

import excepciones.unidades.AtaqueIncorrectoException;
import excepciones.unidades.ErrorDeConstruccionException;
import main.Jugador;
import main.Mapa;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import unidades.milicias.Espadachin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

public class PlazaCentralTests {

    @Test
    public void test01plazaCentralSonCreadosCorrectamente() {
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Nico"));
        plazaCentral.ejecutarTareas();
        assertEquals(450, plazaCentral.verVida());
        assertEquals(4, plazaCentral.verTamanio());
        assertEquals(1, plazaCentral.verAlcance());
        assertFalse(plazaCentral.esMovible());
    }
    
    @Test
    public void test02plazaCentralNoHaceDanio() {
        PlazaCentral plazaCentral= new PlazaCentral(mock(Jugador.class));
        Espadachin armaDeAsedio = new Espadachin(mock(Jugador.class));
        try {
            plazaCentral.provocarDanio(armaDeAsedio);
        } catch (AtaqueIncorrectoException e) {
            assertEquals(        "Los edificios no pueden atacar.", e.getMessage());
        }
    }

    @Test
    public void test03plazaCentralAldeanoConOro() throws ErrorDeConstruccionException {
        Jugador jugador = new Jugador("Nico");
        Whitebox.setInternalState(jugador, "mapa", mock(Mapa.class));
        Whitebox.setInternalState(jugador, "oro", 200);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        plazaCentral.terminarConstruccion();
        plazaCentral.crearUnidad();
    }

    @Test
    public void test04plazaCentralCreaAldeanoSinOro() {
        PlazaCentral plazaCentral = new PlazaCentral(mock(Jugador.class));
        plazaCentral.terminarConstruccion();
        try {
            plazaCentral.crearUnidad();
        } catch (ErrorDeConstruccionException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void test05plazaCentralSonDaniadas() {
        PlazaCentral plazaCentral = new PlazaCentral(mock(Jugador.class));
        assertEquals(450, plazaCentral.verVida());
        plazaCentral.recibirDanio(20);
        assertEquals(430, plazaCentral.verVida());
    }

    @Test
    public void test06plazaCentralEsArregladaYNoLlegaAVidaMaxima() {
        PlazaCentral plazaCentral = new PlazaCentral(mock(Jugador.class));
        assertEquals(450, plazaCentral.verVida());
        plazaCentral.recibirDanio(30);
        assertEquals(420, plazaCentral.verVida());
        plazaCentral.arreglar();
        assertEquals(445, plazaCentral.verVida());
    }

    @Test
    public void test07plazaCentralEsArregladaYLlegaAVidaMaxima() {
        PlazaCentral plazaCentral = new PlazaCentral(mock(Jugador.class));
        assertEquals(450, plazaCentral.verVida());
        plazaCentral.recibirDanio(1);
        assertEquals(449, plazaCentral.verVida());
        plazaCentral.arreglar();
        assertEquals(450, plazaCentral.verVida());
    }
}

package unidades.edificio;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.AtaqueIncorrectoException;
import main.Jugador;

import main.Mapa;
import org.junit.Test;
import org.mockito.stubbing.Answer;
import unidades.milicia.Espadachin;

import static main.AlgoEmpiresTests.agregarCienDeOroAJugador;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class PlazaCentralTests {

    @Test
    public void test01plazaCentralSonCreadosCorrectamente() {
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        plazaCentral.ejecutarTareas();
        assertEquals(450, plazaCentral.verVida());
        assertEquals(4, plazaCentral.verTamanio());
        assertEquals(1, plazaCentral.verAlcance());
        assertFalse(plazaCentral.esMovible());
    }
    
    @Test
    public void test02plazaCentralNoHaceDanio() {
        PlazaCentral plazaCentral= new PlazaCentral(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        Espadachin armaDeAsedio = new Espadachin(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        try {
            plazaCentral.provocarDanio(armaDeAsedio);
        } catch (AtaqueIncorrectoException e) {
            assertEquals(        "Los edificios no pueden atacar.", e.getMessage());
        }
    }

    @Test
    public void test03plazaCentralAldeanoConOro() throws OroInsuficienteException, LimiteDePoblacionException, CoordenadaInvalidaException {
        Jugador jugador = new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null));
        agregarCienDeOroAJugador(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        plazaCentral.crearUnidad();
    }

    @Test
    public void test04plazaCentralCreaAldeanoSinOro() throws LimiteDePoblacionException, CoordenadaInvalidaException {
        Jugador jugador = new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null));
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        try {
            plazaCentral.crearUnidad();
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void test05plazaCentralSonDaniadas() {
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        assertEquals(450, plazaCentral.verVida());
        plazaCentral.recibirDanio(20);
        assertEquals(430, plazaCentral.verVida());
    }

    @Test
    public void test06plazaCentralEsArregladaYNoLlegaAVidaMaxima() {
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        assertEquals(450, plazaCentral.verVida());
        plazaCentral.recibirDanio(30);
        assertEquals(420, plazaCentral.verVida());
        plazaCentral.arreglar();
        assertEquals(445, plazaCentral.verVida());
    }

    @Test
    public void test07plazaCentralEsArregladaYLlegaAVidaMaxima() {
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        assertEquals(450, plazaCentral.verVida());
        plazaCentral.recibirDanio(1);
        assertEquals(449, plazaCentral.verVida());
        plazaCentral.arreglar();
        assertEquals(450, plazaCentral.verVida());
    }
}

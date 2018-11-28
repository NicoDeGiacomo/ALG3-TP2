package unidades.edificios;

import excepciones.unidades.AtaqueIncorrectoException;
import excepciones.unidades.ErrorDeConstruccionException;
import excepciones.unidades.UnidadNoEspecificadaException;
import main.Jugador;
import main.Mapa;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.stubbing.Answer;
import unidades.milicias.Espadachin;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

public class CuartelTests {

    @Test
    public void test01cuartelSonCreadosCorrectamente() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        cuartel.ejecutarTareas();
        assertEquals(250, cuartel.verVida());
        assertEquals(4, cuartel.verTamanio());
        assertEquals(1, cuartel.verAlcance());
        assertFalse(cuartel.esMovible());
    }

    @Test
    public void test02cuartelCreaArqueroYEspadachinConOro() throws ErrorDeConstruccionException {
        Jugador jugador = new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null));
        Cuartel cuartel = new Cuartel(jugador);
        Whitebox.setInternalState(jugador, "oro", 200);
        cuartel.terminarConstruccion();
        cuartel.crearEspadachin();
        cuartel.crearArquero();
    }


    @Test
    public void test03cuartelNoHaceDanio() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        Espadachin armaDeAsedio = new Espadachin(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        try {
            cuartel.provocarDanio(armaDeAsedio);
        } catch (AtaqueIncorrectoException e) {
            assertEquals("Los edificios no pueden atacar.", e.getMessage());
        }
    }

    @Test
    public void test04cuartelCreaArqueroSinOro() {
        Jugador jugador = new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null));
        Cuartel cuartel = new Cuartel(jugador);
        cuartel.terminarConstruccion();
        try {
            cuartel.crearArquero();
        } catch (ErrorDeConstruccionException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void test05cuartelCreaEspadachinSinOro() {
        Jugador jugador = new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null));
        Cuartel cuartel = new Cuartel(jugador);
        cuartel.terminarConstruccion();
        try {
            cuartel.crearEspadachin();
        } catch (ErrorDeConstruccionException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void test06cuartelCreaUnidadSinExpecificar() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        try {
            cuartel.crearUnidad();
        } catch (UnidadNoEspecificadaException e) {
            assertEquals("El cuartel puede crear mas de una unidad. Utilizar metodos: crearEspadachin / crearArquero", e.getMessage());
        }
    }

    @Test
    public void test07cuartelSonDaniados() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        assertEquals(250, cuartel.verVida());
        cuartel.recibirDanio(20);
        assertEquals(230, cuartel.verVida());
    }

    @Test
    public void test08cuartelEsArregladoYNoLlegaAVidaMaxima() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        assertEquals(250, cuartel.verVida());
        cuartel.recibirDanio(60);
        assertEquals(190, cuartel.verVida());
        cuartel.arreglar();
        assertEquals(240, cuartel.verVida());
    }

    @Test
    public void test05cuartelEsArregladoYLlegaAVidaMaxima() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        assertEquals(250, cuartel.verVida());
        cuartel.recibirDanio(1);
        assertEquals(249, cuartel.verVida());
        cuartel.arreglar();
        assertEquals(250, cuartel.verVida());
    }
}

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
        Cuartel cuartel = new Cuartel(mock(Jugador.class));
        cuartel.ejecutarTareas();
        assertEquals(250, cuartel.verVida());
        assertEquals(4, cuartel.verTamanio());
        assertEquals(1, cuartel.verAlcance());
        assertFalse(cuartel.esMovible());
    }

    @Test
    public void test02cuartelCreaArqueroYEspadachinConOro() throws ErrorDeConstruccionException {
        Cuartel cuartel = new Cuartel(mock(Jugador.class));
        cuartel.terminarConstruccion();
        cuartel.crearEspadachin();
        cuartel.crearArquero();
    }


    @Test
    public void test03cuartelNoHaceDanio() {
        Cuartel cuartel = new Cuartel(mock(Jugador.class));
        Espadachin armaDeAsedio = new Espadachin(mock(Jugador.class));
        try {
            cuartel.provocarDanio(armaDeAsedio);
        } catch (AtaqueIncorrectoException e) {
            assertEquals("Los edificios no pueden atacar.", e.getMessage());
        }
    }

    @Test
    public void test04cuartelCreaArqueroSinOro() {
        Cuartel cuartel = new Cuartel(mock(Jugador.class));
        cuartel.terminarConstruccion();
        try {
            cuartel.crearArquero();
        } catch (ErrorDeConstruccionException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void test05cuartelCreaEspadachinSinOro() {
        Cuartel cuartel = new Cuartel(mock(Jugador.class));
        cuartel.terminarConstruccion();
        try {
            cuartel.crearEspadachin();
        } catch (ErrorDeConstruccionException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void test06cuartelCreaUnidadSinExpecificar() {
        Cuartel cuartel = new Cuartel(mock(Jugador.class));
        try {
            cuartel.crearUnidad();
        } catch (UnidadNoEspecificadaException e) {
            assertEquals("El Cuartel puede crear más de una Unidad. Utilizar metodos: crearEspadachin / crearArquero", e.getMessage());
        }
    }

    @Test
    public void test07cuartelSonDaniados() {
        Cuartel cuartel = new Cuartel(mock(Jugador.class));
        assertEquals(250, cuartel.verVida());
        cuartel.recibirDanio(20);
        assertEquals(230, cuartel.verVida());
    }

    @Test
    public void test08cuartelEsArregladoYNoLlegaAVidaMaxima() {
        Cuartel cuartel = new Cuartel(mock(Jugador.class));
        assertEquals(250, cuartel.verVida());
        cuartel.recibirDanio(60);
        assertEquals(190, cuartel.verVida());
        cuartel.arreglar();
        assertEquals(240, cuartel.verVida());
    }

    @Test
    public void test05cuartelEsArregladoYLlegaAVidaMaxima() {
        Cuartel cuartel = new Cuartel(mock(Jugador.class));
        assertEquals(250, cuartel.verVida());
        cuartel.recibirDanio(1);
        assertEquals(249, cuartel.verVida());
        cuartel.arreglar();
        assertEquals(250, cuartel.verVida());
    }
}

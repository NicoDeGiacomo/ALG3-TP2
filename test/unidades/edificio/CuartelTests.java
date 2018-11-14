package unidades.edificio;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.EspacioInsuficienteException;
import excepciones.unidades.UnidadNoEspecificadaException;
import main.Jugador;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CuartelTests {

    @Test
    public void test01cuartelSonCreadosCorrectamente() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        cuartel.ejecutarTareas();
        assertEquals(250, cuartel.verVida());
        assertEquals(4, cuartel.verTamanio());
        assertEquals(1, cuartel.verAlcance());
        assertFalse(cuartel.esMovible());
    }

    @Test
    public void test02cuartelCreaArqueroYEspadachinConOro() throws OroInsuficienteException, EspacioInsuficienteException {
        Jugador jugador = new Jugador("Nico");
        jugador.recolectarOro(1000);
        Cuartel cuartel = new Cuartel(jugador);
        cuartel.crearEspadachin();
        cuartel.crearArquero();
    }

    @Test
    public void test02cuartelCreaArqueroSinOro() {
        Jugador jugador = new Jugador("Nico");
        Cuartel cuartel = new Cuartel(jugador);
        try {
            cuartel.crearArquero();
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        } catch (EspacioInsuficienteException e) {
            fail("Error inesperado.");
        }
    }

    @Test
    public void test02cuartelCreaEspadachinSinOro() {
        Jugador jugador = new Jugador("Nico");
        Cuartel cuartel = new Cuartel(jugador);
        try {
            cuartel.crearEspadachin();
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        } catch (EspacioInsuficienteException e) {
            fail("Error inesperado.");
        }
    }

    @Test
    public void test02cuartelCreaUnidadSinExpecificar() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        try {
            cuartel.crearUnidad();
        } catch (UnidadNoEspecificadaException e) {
            //ToDo:
            //assertEquals(,);
        }
    }

    @Test
    public void test03cuartelSonDaniadas() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        assertEquals(250, cuartel.verVida());
        cuartel.recibirDanio(20);
        assertEquals(230, cuartel.verVida());
    }

    @Test
    public void test04cuartelSonArregladasYNoLlegaAVidaMaxima() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        assertEquals(250, cuartel.verVida());
        cuartel.recibirDanio(60);
        assertEquals(190, cuartel.verVida());
        cuartel.arreglar();
        assertEquals(240, cuartel.verVida());
    }

    @Test
    public void test05cuartelSonArregladasYLlegaAVidaMaxima() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        assertEquals(250, cuartel.verVida());
        cuartel.recibirDanio(1);
        assertEquals(249, cuartel.verVida());
        cuartel.arreglar();
        assertEquals(250, cuartel.verVida());
    }
}

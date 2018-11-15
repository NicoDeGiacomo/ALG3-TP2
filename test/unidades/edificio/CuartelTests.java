package unidades.edificio;

import excepciones.main.OroInsuficienteException;
import excepciones.unidades.AtaqueIncorrectoException;
import excepciones.unidades.UnidadNoEspecificadaException;
import main.Jugador;

import org.junit.Test;
import unidades.milicia.Espadachin;

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
    public void test02cuartelCreaArqueroYEspadachinConOro() throws OroInsuficienteException {
        Jugador jugador = new Jugador("Nico");
        jugador.recolectarOro(1000);
        Cuartel cuartel = new Cuartel(jugador);
        cuartel.crearEspadachin();
        cuartel.crearArquero();
    }


    @Test
    public void test03cuartelNoHaceDanio() {
        Cuartel cuartel= new Cuartel(new Jugador("Nico"));
        Espadachin armaDeAsedio = new Espadachin(new Jugador("Nico"));
        try {
            cuartel.provocarDanio(armaDeAsedio);
        } catch (AtaqueIncorrectoException e) {
            assertEquals(        "Los edificios no pueden atacar.", e.getMessage());
        }         
    }

    @Test
    public void test04cuartelCreaArqueroSinOro() {
        Jugador jugador = new Jugador("Nico");
        Cuartel cuartel = new Cuartel(jugador);
        try {
            cuartel.crearArquero();
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void test05cuartelCreaEspadachinSinOro() {
        Jugador jugador = new Jugador("Nico");
        Cuartel cuartel = new Cuartel(jugador);
        try {
            cuartel.crearEspadachin();
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void test06cuartelCreaUnidadSinExpecificar() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        try {
            cuartel.crearUnidad();
        } catch (UnidadNoEspecificadaException e) {
            assertEquals("El cuartel puede crear mas de una unidad. Utilizar metodos: crearEspadachin / crearArquero", e.getMessage());
        }
    }

    @Test
    public void test07cuartelSonDaniados() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        assertEquals(250, cuartel.verVida());
        cuartel.recibirDanio(20);
        assertEquals(230, cuartel.verVida());
    }

    @Test
    public void test08cuartelEsArregladoYNoLlegaAVidaMaxima() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        assertEquals(250, cuartel.verVida());
        cuartel.recibirDanio(60);
        assertEquals(190, cuartel.verVida());
        cuartel.arreglar();
        assertEquals(240, cuartel.verVida());
    }

    @Test
    public void test05cuartelEsArregladoYLlegaAVidaMaxima() {
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        assertEquals(250, cuartel.verVida());
        cuartel.recibirDanio(1);
        assertEquals(249, cuartel.verVida());
        cuartel.arreglar();
        assertEquals(250, cuartel.verVida());
    }
}

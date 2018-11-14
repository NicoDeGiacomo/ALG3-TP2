package unidades.edificio;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.EspacioInsuficienteException;
import main.Jugador;
import unidades.milicia.ArmaDeAsedio;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CastilloTests {

    @Test
    public void test01castilloSonCreadosCorrectamente() {
        Castillo castillo = new Castillo(new Jugador("Nico"));
        assertEquals(1000, castillo.verVida());
        assertEquals(16, castillo.verTamanio());
        castillo.cobrarCostoDeCreacion();
        assertFalse(castillo.esMovible());
        assertEquals(3, castillo.verAlcance());

    }

    @Test
    public void test02castilloCreaArmaDeAsedioConOro() throws OroInsuficienteException, EspacioInsuficienteException {
        Jugador jugador = new Jugador("Nico");
        jugador.recolectarOro(1000);
        Castillo castillo = new Castillo(jugador);
        castillo.crearUnidad();
    }

    @Test
    public void test02castilloCreaArmaDeAsedioSinOro() {
        Jugador jugador = new Jugador("Nico");
        Castillo castillo = new Castillo(jugador);
        try {
            castillo.crearUnidad();
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        } catch (EspacioInsuficienteException e) {
            fail("Error inesperado.");
        }
    }

    @Test
    public void test03castilloSonDaniadas() {
        Castillo castillo = new Castillo(new Jugador("Nico"));
        assertEquals(1000, castillo.verVida());
        castillo.recibirDanio(20);
        assertEquals(980, castillo.verVida());
    }

    @Test
    public void test04castilloHaceDanio() {
        Castillo castillo = new Castillo(new Jugador("Nico"));
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico"));
        assertEquals(150, armaDeAsedio.verVida());
        castillo.provocarDanio(armaDeAsedio);
        assertEquals(130, armaDeAsedio.verVida());
    }

    @Test
    public void test05castilloSonArregladasYNoLlegaAVidaMaxima() {
        Castillo castillo = new Castillo(new Jugador("Nico"));
        assertEquals(1000, castillo.verVida());
        castillo.recibirDanio(20);
        assertEquals(980, castillo.verVida());
        castillo.arreglar();
        assertEquals(995, castillo.verVida());
    }

    @Test
    public void test06castilloSonArregladasYLlegaAVidaMaxima() {
        Castillo castillo = new Castillo(new Jugador("Nico"));
        assertEquals(1000, castillo.verVida());
        castillo.recibirDanio(1);
        assertEquals(999, castillo.verVida());
        castillo.arreglar();
        assertEquals(1000, castillo.verVida());
    }
}

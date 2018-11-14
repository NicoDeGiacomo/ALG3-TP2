package unidades.edificio;

import excepciones.main.OroInsuficienteException;
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
    public void test02castilloCreaArmaDeAsedioConOro() throws OroInsuficienteException {
        Jugador jugador = new Jugador("Nico");
        jugador.recolectarOro(1000);
        Castillo castillo = new Castillo(jugador);
        castillo.crearUnidad();
    }

    @Test
    public void test03castilloCreaArmaDeAsedioSinOro() {
        Jugador jugador = new Jugador("Nico");
        Castillo castillo = new Castillo(jugador);
        try {
            castillo.crearUnidad();
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void test04castilloSonDaniadas() {
        Castillo castillo = new Castillo(new Jugador("Nico"));
        assertEquals(1000, castillo.verVida());
        castillo.recibirDanio(20);
        assertEquals(980, castillo.verVida());
    }

    @Test
    public void test05castilloHaceDanio() {
        Castillo castillo = new Castillo(new Jugador("Nico"));
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Peter"));
        assertEquals(150, armaDeAsedio.verVida());
        castillo.provocarDanio(armaDeAsedio);
        assertEquals(130, armaDeAsedio.verVida());
    }

    @Test
    public void test06castilloEsArregladoYNoLlegaAVidaMaxima() {
        Castillo castillo = new Castillo(new Jugador("Nico"));
        assertEquals(1000, castillo.verVida());
        castillo.recibirDanio(20);
        assertEquals(980, castillo.verVida());
        castillo.arreglar();
        assertEquals(995, castillo.verVida());
    }

    @Test
    public void test07castilloEsArregladoYLlegaAVidaMaxima() {
        Castillo castillo = new Castillo(new Jugador("Nico"));
        assertEquals(1000, castillo.verVida());
        castillo.recibirDanio(1);
        assertEquals(999, castillo.verVida());
        castillo.arreglar();
        assertEquals(1000, castillo.verVida());
    }
}

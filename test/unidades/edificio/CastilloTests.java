package unidades.edificio;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import main.Jugador;
import main.Mapa;
import unidades.milicia.ArmaDeAsedio;

import org.junit.Test;

import static main.AlgoEmpiresTests.agregarCienDeOroAJugador;
import static org.junit.jupiter.api.Assertions.*;

public class CastilloTests {

    @Test
    public void test01castilloSonCreadosCorrectamente() {
        Castillo castillo = new Castillo(new Jugador("Nico", new Mapa()));
        assertEquals(1000, castillo.verVida());
        assertEquals(16, castillo.verTamanio());
        assertFalse(castillo.esMovible());
        assertEquals(3, castillo.verAlcance());
    }

    @Test
    public void test02castilloCreaArmaDeAsedioConOro() throws OroInsuficienteException, LimiteDePoblacionException {
        Jugador jugador = new Jugador("Nico", new Mapa());
        agregarCienDeOroAJugador(jugador);
        Castillo castillo = new Castillo(jugador);
        castillo.crearUnidad();
    }

    @Test
    public void test03castilloCreaArmaDeAsedioSinOro() throws LimiteDePoblacionException {
        Jugador jugador = new Jugador("Nico", new Mapa());
        Castillo castillo = new Castillo(jugador);
        try {
            castillo.crearUnidad();
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void test04castilloSonDaniadas() {
        Castillo castillo = new Castillo(new Jugador("Nico", new Mapa()));
        assertEquals(1000, castillo.verVida());
        castillo.recibirDanio(20);
        assertEquals(980, castillo.verVida());
    }

    @Test
    public void test05castilloHaceDanio() {
        Castillo castillo = new Castillo(new Jugador("Nico", new Mapa()));
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(new Jugador("Nico", new Mapa()));
        assertEquals(150, armaDeAsedio.verVida());
        castillo.provocarDanio(armaDeAsedio);
        assertEquals(130, armaDeAsedio.verVida());
    }

    @Test
    public void test06castilloNoHaceDanioAALiado() {
        Jugador jugador = new Jugador("Nico", new Mapa());
        Castillo castillo = new Castillo(jugador);
        ArmaDeAsedio armaDeAsedioPropia = new ArmaDeAsedio(jugador);
        assertEquals(150, armaDeAsedioPropia.verVida());
        castillo.provocarDanio(armaDeAsedioPropia);
        assertEquals(150, armaDeAsedioPropia.verVida());
    }



    @Test
    public void test07castilloEsArregladoYNoLlegaAVidaMaxima() {
        Castillo castillo = new Castillo(new Jugador("Nico", new Mapa()));
        assertEquals(1000, castillo.verVida());
        castillo.recibirDanio(20);
        assertEquals(980, castillo.verVida());
        castillo.arreglar();
        assertEquals(995, castillo.verVida());
    }

    @Test
    public void test08castilloEsArregladoYLlegaAVidaMaxima() {
        Castillo castillo = new Castillo(new Jugador("Nico", new Mapa()));
        assertEquals(1000, castillo.verVida());
        castillo.recibirDanio(1);
        assertEquals(999, castillo.verVida());
        castillo.arreglar();
        assertEquals(1000, castillo.verVida());
    }
}

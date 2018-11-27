package unidades.edificio;

import excepciones.unidades.ErrorDeConstruccionException;
import main.Jugador;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import unidades.milicia.ArmaDeAsedio;
import unidades.milicia.Arquero;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

public class CastilloTests {

    @Test
    public void test01castilloSonCreadosCorrectamente() {
        Castillo castillo = new Castillo(mock(Jugador.class));
        assertEquals(1000, castillo.verVida());
        assertEquals(16, castillo.verTamanio());
        assertFalse(castillo.esMovible());
        assertEquals(3, castillo.verAlcance());
    }

    @Test
    public void test02castilloCreaArmaDeAsedioConOro() throws ErrorDeConstruccionException {
        Jugador jugador = mock(Jugador.class);
        Whitebox.setInternalState(jugador, "oro", 200);
        Castillo castillo = new Castillo(jugador);
        castillo.crearUnidad();
    }

    @Test
    public void test03castilloCreaArmaDeAsedioSinOro() {
        Jugador jugador = mock(Jugador.class);
        Whitebox.setInternalState(jugador, "oro", 0);
        Castillo castillo = new Castillo(jugador);

        try {
            castillo.crearUnidad();
        } catch (ErrorDeConstruccionException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }

    @Test
    public void test04castilloSonDaniadas() {
        Castillo castillo = new Castillo(mock(Jugador.class));
        assertEquals(1000, castillo.verVida());
        castillo.recibirDanio(20);
        assertEquals(980, castillo.verVida());
    }

    @Test
    public void test05castilloHaceDanio() {
        Castillo castillo = new Castillo(mock(Jugador.class));
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(mock(Jugador.class));

        assertEquals(150, armaDeAsedio.verVida());
        castillo.provocarDanio(armaDeAsedio);
        assertEquals(130, armaDeAsedio.verVida());
    }

    @Test
    public void test06castilloNoHaceDanioAALiado() {
        Jugador jugador = mock(Jugador.class);
        Castillo castillo = new Castillo(jugador);
        ArmaDeAsedio armaDeAsedioPropia = new ArmaDeAsedio(jugador);

        assertEquals(150, armaDeAsedioPropia.verVida());
        castillo.provocarDanio(armaDeAsedioPropia);
        assertEquals(150, armaDeAsedioPropia.verVida());
    }



    @Test
    public void test07castilloEsArregladoYNoLlegaAVidaMaxima() {
        Castillo castillo = new Castillo(mock(Jugador.class));
        assertEquals(1000, castillo.verVida());
        castillo.recibirDanio(20);
        assertEquals(980, castillo.verVida());
        castillo.arreglar();
        assertEquals(995, castillo.verVida());
    }

    @Test
    public void test08castilloEsArregladoYLlegaAVidaMaxima() {
        Castillo castillo = new Castillo(mock(Jugador.class));
        assertEquals(1000, castillo.verVida());
        castillo.recibirDanio(1);
        assertEquals(999, castillo.verVida());
        castillo.arreglar();
        assertEquals(1000, castillo.verVida());
    }

    @Test
    public void Test09CastilloAtacaAutomaticamenteALasUnidadesEnemigasAlAlcanceYNoALasAliadas() {
        Jugador jugador1 = mock(Jugador.class);

        Arquero arqueroEnemigo = new Arquero(mock(Jugador.class));
        Arquero arqueroAliado = new Arquero(jugador1);
        Castillo castillo = new Castillo(jugador1);

        Mockito.when(jugador1.unidadesCercanas(castillo)).thenReturn(Arrays.asList(arqueroAliado, arqueroEnemigo));
        castillo.ejecutarTareas();

        assertEquals(75, arqueroAliado.verVida());
        assertEquals(55, arqueroEnemigo.verVida());
    }
}

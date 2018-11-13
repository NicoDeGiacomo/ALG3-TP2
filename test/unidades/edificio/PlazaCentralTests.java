package unidades.edificio;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.EspacioInsuficienteException;
import main.Jugador;
import main.Mapa;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PlazaCentralTests {

    @Test
    public void test01plazaCentralSonCreadosCorrectamente() {
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Nico"));
        Assert.assertEquals(450, plazaCentral.verVida());
        Assert.assertEquals(4, plazaCentral.verTamanio());
        Assert.assertEquals(1, plazaCentral.verAlcance());
        Assert.assertEquals(false, plazaCentral.esMovible());
    }

    @Test
    public void test02plazaCentralCreaArmaDeAsedioConOro() throws OroInsuficienteException, EspacioInsuficienteException {
        Jugador jugador = new Jugador("Nico");
        jugador.recolectarOro(1000);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        plazaCentral.crearUnidad();
    }

    @Test
    public void test02plazaCentralCreaArmaDeAsedioSinOro() {
        Jugador jugador = new Jugador("Nico");
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        try {
            plazaCentral.crearUnidad();
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        } catch (EspacioInsuficienteException e) {
            fail("Error inesperado.");
        }
    }

    @Test
    public void test03plazaCentralSonDaniadas() {
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Nico"));
        Assert.assertEquals(450, plazaCentral.verVida());
        plazaCentral.recibirDanio(20);
        Assert.assertEquals(430, plazaCentral.verVida());
    }

    @Test
    public void test04plazaCentralSonArregladasYNoLlegaAVidaMaxima() {
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Nico"));
        Assert.assertEquals(450, plazaCentral.verVida());
        plazaCentral.recibirDanio(30);
        Assert.assertEquals(420, plazaCentral.verVida());
        plazaCentral.arreglar();
        Assert.assertEquals(445, plazaCentral.verVida());
    }

    @Test
    public void test05plazaCentralSonArregladasYLlegaAVidaMaxima() {
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Nico"));
        Assert.assertEquals(450, plazaCentral.verVida());
        plazaCentral.recibirDanio(1);
        Assert.assertEquals(449, plazaCentral.verVida());
        plazaCentral.arreglar();
        Assert.assertEquals(450, plazaCentral.verVida());
    }
}

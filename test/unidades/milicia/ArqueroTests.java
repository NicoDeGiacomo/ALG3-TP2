package unidades.milicia;

import excepciones.unidades.AtaqueIncorrectoException;
import main.Jugador;
import main.Mapa;
import unidades.edificio.PlazaCentral;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArqueroTests {

    @Test
    public void test01arqueroSonCreadosCorrectamente() {
        Arquero arquero = new Arquero(new Jugador("Nico", new Mapa()));
        assertEquals(75, arquero.verVida());
        assertEquals(1, arquero.verTamanio());
        arquero.ejecutarTareas();
    }

    @Test
    public void test02arqueroProbocanDanioAMilicias() throws AtaqueIncorrectoException {
        Arquero arqueroHaceDanio = new Arquero(new Jugador("Nico", new Mapa()));
        Arquero arqueroRecibeDanio = new Arquero(new Jugador("Nico", new Mapa()));
        arqueroHaceDanio.provocarDanio(arqueroRecibeDanio);
        assertEquals(75, arqueroHaceDanio.verVida());
        assertEquals(60, arqueroRecibeDanio.verVida());
    }

    @Test
    public void test03arqueroProbocanDanioAEdificios() throws AtaqueIncorrectoException {
        Arquero arqueroHaceDanio = new Arquero(new Jugador("Nico", new Mapa()));
        PlazaCentral plaza = new PlazaCentral(new Jugador("Nico", new Mapa()));
        arqueroHaceDanio.provocarDanio(plaza);
        assertEquals(75, arqueroHaceDanio.verVida());
        assertEquals(440, plaza.verVida());
    }

    @Test
    public void test04arqueroSonDaniadas() {
        Arquero arquero = new Arquero(new Jugador("Nico", new Mapa()));
        assertEquals(75, arquero.verVida());
        arquero.recibirDanio(20);
        assertEquals(55, arquero.verVida());
    }
}

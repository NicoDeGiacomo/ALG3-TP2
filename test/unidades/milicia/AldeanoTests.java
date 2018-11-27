package unidades.milicia;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.AldeanoOcupadoException;
import excepciones.unidades.AtaqueIncorrectoException;
import excepciones.unidades.CreacionDeCastilloException;
import main.Jugador;
import main.Mapa;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.stubbing.Answer;
import unidades.edificio.Cuartel;
import unidades.edificio.PlazaCentral;
import unidades.estados.unidades.EnConstruccion;
import unidades.estados.unidades.Vivo;
import java.awt.geom.Point2D;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class AldeanoTests {

    @Test
    public void test01aldeanoSonCreadosCorrectamente() {
        Aldeano aldeano = new Aldeano(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        assertEquals(50, aldeano.verVida());
        assertEquals(1, aldeano.verTamanio());
        assertTrue(aldeano.esMovible());
    }

    @Test
    public void test02aldeanoNoHaceDanio() {
        Aldeano aldeanoHaceDanio = new Aldeano(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        Aldeano aldeanoRecibeDanio = new Aldeano(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));

        try {
            aldeanoHaceDanio.provocarDanio(aldeanoRecibeDanio);
        } catch (AtaqueIncorrectoException e) {
            assertEquals("El aldeano no puede atacar.", e.getMessage());
        }

        assertEquals(50, aldeanoHaceDanio.verVida());
        assertEquals(50, aldeanoRecibeDanio.verVida());
    }

    @Test
    public void test03aldeanoSonDaniadas() {
        Aldeano aldeano = new Aldeano(new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null)));
        assertEquals(50, aldeano.verVida());
        aldeano.recibirDanio(20);
        assertEquals(30, aldeano.verVida());
    }


    @Test
    public void test05aldeanoReparandoEdificioTieneVidaMaximaYAldeanoCambiaDeEstado() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null));
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);

        aldeano.reparar(plazaCentral);
        aldeano.ejecutarTareas();
        assertEquals(450, plazaCentral.verVida());

        aldeano.reparar(plazaCentral);

        try {
            aldeano.reparar(plazaCentral);
        } catch (AldeanoOcupadoException e) {
            assertEquals("El aldeano se encuentra reparando.", e.getMessage());
        }
    }

    @Test
    public void test06aldeanoConstruyendoPlaza() throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        Jugador jugador = mock(Jugador.class);
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        Whitebox.setInternalState(jugador, "oro", 100);

        aldeano.construir(plazaCentral, new Point2D.Double(1, 1));
        assertEquals(EnConstruccion.class, plazaCentral.verEstadoDeUnidad().getClass());
        aldeano.ejecutarTareas();
        assertTrue(plazaCentral.esMapeable());
    }

    @Test
    public void test07aldeanoConstruyendoCuartel() throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        Jugador jugador = new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null));
        Aldeano aldeano = new Aldeano(jugador);
        Cuartel cuartel = new Cuartel(jugador);
        Whitebox.setInternalState(jugador, "oro", 200);
        aldeano.construir(cuartel, new Point2D.Double(1, 1));
        assertEquals(EnConstruccion.class, cuartel.verEstadoDeUnidad().getClass());
        assertTrue(cuartel.esMapeable());

    }

    @Test
    public void test06aldeanoConstruyePlazaYPasanTresTurnos() throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        Jugador jugador = new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null));
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        Whitebox.setInternalState(jugador, "oro", 200);
        aldeano.construir(plazaCentral, new Point2D.Double(1, 1));
        assertEquals(EnConstruccion.class, plazaCentral.verEstadoDeUnidad().getClass());
        assertTrue(plazaCentral.esMapeable());
        assertFalse(plazaCentral.estaHabilitado());
        aldeano.ejecutarTareas();
        aldeano.ejecutarTareas();
        aldeano.ejecutarTareas();
        assertEquals(Vivo.class, plazaCentral.verEstadoDeUnidad().getClass());
        assertTrue(plazaCentral.esMapeable());
        assertTrue(plazaCentral.estaHabilitado());
    }

    @Test
    public void test08aldeanoObtieneOro() throws LimiteDePoblacionException, CoordenadaInvalidaException {
        Jugador jugador = new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null));
        ArmaDeAsedio espadachin = new ArmaDeAsedio(jugador);
        Cuartel cuartel = new Cuartel(jugador);
        try {
            jugador.agregarUnidad(espadachin,cuartel);
        } catch (OroInsuficienteException e) {
            assertEquals("El oro del jugador es insuficiente.", e.getMessage());
        }
    }
    @Test
    public void test09aldeanoObtieneOro() throws LimiteDePoblacionException, CoordenadaInvalidaException {
        Mapa mapa = mock(Mapa.class, (Answer) invocation -> null);
        Jugador jugador = new Jugador("Nico", mapa);
        Aldeano aldeano = new Aldeano(jugador);
        ArmaDeAsedio espadachin = new ArmaDeAsedio(jugador);
        Cuartel cuartel = new Cuartel(jugador);

        try {
            mapa.colocarDibujable(cuartel, new Point2D.Double(1,1));
        } catch (CoordenadaInvalidaException ignored) {
            fail("Error Inesperado");
        }

        for (int i = 0 ; i < 5; i ++ ){
            aldeano.ejecutarTareas();
        }
        try {
            jugador.agregarUnidad(espadachin,cuartel);
        } catch (OroInsuficienteException e) {
            fail("El oro del jugador es insuficiente.");
        }
    }

    @Test
    public void test09aldeanoReparando() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null));
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        plazaCentral.recibirDanio(100);
        aldeano.reparar(plazaCentral);
        aldeano.ejecutarTareas();
        assertEquals(375, plazaCentral.verVida());
    }

    @Test
    public void test10aldeanoEstaOcupadoReparando() throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        Jugador jugador = new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null));
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        plazaCentral.recibirDanio(100);
        aldeano.reparar(plazaCentral);
        try {
            aldeano.construir(plazaCentral, new Point2D.Double(1, 1));
        } catch (AldeanoOcupadoException e) {
            assertEquals("El aldeano se encuentra reparando.", e.getMessage());
        }
        aldeano.ejecutarTareas();
    }

    @Test
    public void test11aldeanoEstaOcupadoConstruyendo() throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        Jugador jugador = new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null));
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        Whitebox.setInternalState(jugador, "oro", 200);
        aldeano.construir(plazaCentral, new Point2D.Double(1, 1));
        try {
            aldeano.reparar(plazaCentral);
        } catch (AldeanoOcupadoException e) {
            assertEquals("El aldeano se encuentra construyendo.", e.getMessage());
        }
        aldeano.ejecutarTareas();
    }

    @Test
    public void test12aldeanoEstaOcupadoConstruyendo() throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        Jugador jugador = new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null));
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        Whitebox.setInternalState(jugador, "oro", 200);
        aldeano.construir(plazaCentral, new Point2D.Double(1, 1));
        try {
            aldeano.construir(plazaCentral, new Point2D.Double(1, 1));
        } catch (AldeanoOcupadoException e) {
            assertEquals("El aldeano se encuentra construyendo.", e.getMessage());
        }
        aldeano.ejecutarTareas();
    }

    @Test
    public void test14aldeanoEstaOcupadoReparando() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico", mock(Mapa.class, (Answer) invocation -> null));
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        plazaCentral.recibirDanio(100);
        aldeano.reparar(plazaCentral);
        try {
            aldeano.reparar(plazaCentral);
        } catch (AldeanoOcupadoException e) {
            assertEquals("El aldeano se encuentra reparando.", e.getMessage());
        }
        aldeano.ejecutarTareas();
    }
}

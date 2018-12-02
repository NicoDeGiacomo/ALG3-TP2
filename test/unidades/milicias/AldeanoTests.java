package unidades.milicias;

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
import unidades.edificios.Cuartel;
import unidades.edificios.PlazaCentral;
import unidades.estados.aldeano.EstadoDeAldeano;
import unidades.estados.aldeano.Ocioso;
import unidades.estados.unidades.EnConstruccion;
import unidades.estados.unidades.Vivo;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AldeanoTests {

    @Test
    public void test01aldeanoSonCreadosCorrectamente() {
        Aldeano aldeano = new Aldeano(mock(Jugador.class));
        assertEquals(50, aldeano.verVida());
        assertEquals(1, aldeano.verTamanio());
        assertTrue(aldeano.esMovible());
    }

    @Test
    public void test02aldeanoNoHaceDanio() {
        Aldeano aldeanoHaceDanio = new Aldeano(mock(Jugador.class));
        Aldeano aldeanoRecibeDanio = new Aldeano(mock(Jugador.class));

        try {
            aldeanoHaceDanio.provocarDanio(aldeanoRecibeDanio);
        } catch (AtaqueIncorrectoException e) {
            assertEquals("El Aldeano NO puede Atacar!", e.getMessage());
        }

        assertEquals(50, aldeanoHaceDanio.verVida());
        assertEquals(50, aldeanoRecibeDanio.verVida());
    }

    @Test
    public void test03aldeanoSonDaniadas() {
        Aldeano aldeano = new Aldeano(mock(Jugador.class));
        assertEquals(50, aldeano.verVida());
        aldeano.recibirDanio(20);
        assertEquals(30, aldeano.verVida());
    }


    @Test
    public void test05aldeanoReparandoEdificioTieneVidaMaximaYAldeanoCambiaDeEstado() throws AldeanoOcupadoException {
        Aldeano aldeano = new Aldeano(mock(Jugador.class));
        PlazaCentral plazaCentral = new PlazaCentral(mock(Jugador.class));

        aldeano.reparar(plazaCentral);
        aldeano.ejecutarTareas();
        assertEquals(450, plazaCentral.verVida());

        aldeano.reparar(plazaCentral);

        try {
            aldeano.reparar(plazaCentral);
        } catch (AldeanoOcupadoException e) {
            assertEquals("El Aldeano se encuentra Reparando.", e.getMessage());
        }
    }

    @Test
    public void test06aldeanoConstruyendoPlaza() throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        Aldeano aldeano = new Aldeano(mock(Jugador.class));
        PlazaCentral plazaCentral = new PlazaCentral(mock(Jugador.class));

        aldeano.construir(plazaCentral, new Point2D.Double(1, 1));
        assertEquals(EnConstruccion.class, plazaCentral.verEstadoDeUnidad().getClass());
        aldeano.ejecutarTareas();
        assertTrue(plazaCentral.esMapeable());
    }

    @Test
    public void test07aldeanoConstruyendoCuartel() throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        Aldeano aldeano = new Aldeano(mock(Jugador.class));
        Cuartel cuartel = new Cuartel(mock(Jugador.class));
        aldeano.construir(cuartel, new Point2D.Double(1, 1));
        assertEquals(EnConstruccion.class, cuartel.verEstadoDeUnidad().getClass());
        assertTrue(cuartel.esMapeable());

    }

    @Test
    public void test06aldeanoConstruyePlazaYPasanTresTurnos() throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        Aldeano aldeano = new Aldeano(mock(Jugador.class));
        PlazaCentral plazaCentral = new PlazaCentral(mock(Jugador.class));
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
    public void test09aldeanoObtieneOro() {
        Mapa.destruir();
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);

        Whitebox.setInternalState(jugador, "oro", 0);
        assertEquals(0, Whitebox.getInternalState(jugador, "oro"));
        aldeano.ejecutarTareas();
        assertEquals(20, Whitebox.getInternalState(jugador, "oro"));
    }

    @Test
    public void test09aldeanoReparando() throws AldeanoOcupadoException {
        Aldeano aldeano = new Aldeano(mock(Jugador.class));
        PlazaCentral plazaCentral = new PlazaCentral(mock(Jugador.class));
        plazaCentral.recibirDanio(100);
        aldeano.reparar(plazaCentral);
        aldeano.ejecutarTareas();
        assertEquals(375, plazaCentral.verVida());
    }

    @Test
    public void test10aldeanoEstaOcupadoReparando() throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        Aldeano aldeano = new Aldeano(mock(Jugador.class));
        PlazaCentral plazaCentral = new PlazaCentral(mock(Jugador.class));
        plazaCentral.recibirDanio(100);
        aldeano.reparar(plazaCentral);
        try {
            aldeano.construir(plazaCentral, new Point2D.Double(1, 1));
        } catch (AldeanoOcupadoException e) {
            assertEquals("El Aldeano se encuentra Reparando.", e.getMessage());
        }
        aldeano.ejecutarTareas();
    }

    @Test
    public void test11aldeanoEstaOcupadoConstruyendo() throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        Aldeano aldeano = new Aldeano(mock(Jugador.class));
        PlazaCentral plazaCentral = new PlazaCentral(mock(Jugador.class));
        aldeano.construir(plazaCentral, new Point2D.Double(1, 1));
        try {
            aldeano.reparar(plazaCentral);
        } catch (AldeanoOcupadoException e) {
            assertEquals("El Aldeano se encuentra Construyendo.", e.getMessage());
        }
        aldeano.ejecutarTareas();
    }

    @Test
    public void test12aldeanoEstaOcupadoConstruyendo() throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        Aldeano aldeano = new Aldeano(mock(Jugador.class));
        PlazaCentral plazaCentral = new PlazaCentral(mock(Jugador.class));
        aldeano.construir(plazaCentral, new Point2D.Double(1, 1));
        try {
            aldeano.construir(plazaCentral, new Point2D.Double(1, 1));
        } catch (AldeanoOcupadoException e) {
            assertEquals("El Aldeano se encuentra Construyendo.", e.getMessage());
        }
        aldeano.ejecutarTareas();
    }

    @Test
    public void test14aldeanoEstaOcupadoReparando() throws AldeanoOcupadoException {
        Aldeano aldeano = new Aldeano(mock(Jugador.class));
        PlazaCentral plazaCentral = new PlazaCentral(mock(Jugador.class));
        plazaCentral.recibirDanio(100);
        aldeano.reparar(plazaCentral);
        try {
            aldeano.reparar(plazaCentral);
        } catch (AldeanoOcupadoException e) {
            assertEquals("El Aldeano se encuentra Reparando.", e.getMessage());
        }
        aldeano.ejecutarTareas();
    }
}

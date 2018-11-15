package unidades.milicia;

import excepciones.main.OroInsuficienteException;
import excepciones.unidades.AldeanoOcupadoException;
import excepciones.unidades.AtaqueIncorrectoException;
import main.Jugador;
import org.junit.Test;
import unidades.edificio.Castillo;
import unidades.edificio.Cuartel;
import unidades.edificio.PlazaCentral;
import unidades.estados.unidades.EnConstruccion;
import unidades.estados.unidades.Vivo;

import static org.junit.jupiter.api.Assertions.*;

public class AldeanoTests {

    @Test
    public void test01aldeanoSonCreadosCorrectamente() {
        Aldeano aldeano = new Aldeano(new Jugador("Nico"));
        assertEquals(50, aldeano.verVida());
        assertEquals(1, aldeano.verTamanio());
        assertTrue(aldeano.esMovible());
    }

    @Test
    public void test02aldeanoNoHaceDanio() {
        Aldeano aldeanoHaceDanio = new Aldeano(new Jugador("Nico"));
        Aldeano aldeanoRecibeDanio = new Aldeano(new Jugador("Nico"));

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
        Aldeano aldeano = new Aldeano(new Jugador("Nico"));
        assertEquals(50, aldeano.verVida());
        aldeano.recibirDanio(20);
        assertEquals(30, aldeano.verVida());
    }


    @Test
    public void test05aldeanoReparandoEdificioTieneVidaMaximaYAldeanoCambiaDeEstado() {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);

        try {
            aldeano.reparar(plazaCentral);
        } catch (AldeanoOcupadoException e) {
            fail("Error inesperado");
        }
        aldeano.ejecutarTareas();
        assertEquals(450, plazaCentral.verVida());

        try {
            aldeano.reparar(plazaCentral);
        } catch (AldeanoOcupadoException e) {
            fail("Error inesperado");
        }

        try {
            aldeano.reparar(plazaCentral);
        } catch (AldeanoOcupadoException e) {
            assertEquals("El aldeano se encuentra reparando.", e.getMessage());
        }
    }

    @Test
    public void test06aldeanoConstruyendoPlaza() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        jugador.recolectarOro(1000);
        try {
            aldeano.construir(plazaCentral);
        } catch (OroInsuficienteException e) {
            fail("Error inesperado.");
        }
        assertEquals(EnConstruccion.class, plazaCentral.verEstadoDeUnidad().getClass());
        aldeano.ejecutarTareas();
        assertTrue(plazaCentral.esMapeable());
    }

    @Test
    public void test07aldeanoConstruyendoCuartel() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        Cuartel cuartel = new Cuartel(jugador);
        jugador.recolectarOro(1000);
        try {
            aldeano.construir(cuartel);
        } catch (OroInsuficienteException e) {
            fail("Error inesperado.");
        }
        assertEquals(EnConstruccion.class, cuartel.verEstadoDeUnidad().getClass());
        assertTrue(cuartel.esMapeable());

    }

    @Test
    public void test06aldeanoConstruyePlazaYPasanTresTurnos() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        jugador.recolectarOro(1000);
        try {
            aldeano.construir(plazaCentral);
        } catch (OroInsuficienteException e) {
            fail("Error inesperado.");
        }
        assertEquals(EnConstruccion.class, plazaCentral.verEstadoDeUnidad().getClass());
        assertTrue(plazaCentral.esMapeable());
        aldeano.ejecutarTareas();
        aldeano.ejecutarTareas();
        aldeano.ejecutarTareas();
        assertEquals(Vivo.class, plazaCentral.verEstadoDeUnidad().getClass());
        assertTrue(plazaCentral.esMapeable());
    }

    @Test
    public void test08aldeanoObtieneOro() throws OroInsuficienteException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        aldeano.ejecutarTareas();
        jugador.cobrarOro(20);

    }

    @Test
    public void test09aldeanoReparando() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        plazaCentral.recibirDanio(100);
        aldeano.reparar(plazaCentral);
        aldeano.ejecutarTareas();
        assertEquals(375, plazaCentral.verVida());
    }

    @Test
    public void test10aldeanoEstaOcupadoReparando() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        plazaCentral.recibirDanio(100);
        aldeano.reparar(plazaCentral);
        try {
            aldeano.construir(plazaCentral);
        } catch (AldeanoOcupadoException e) {
            assertEquals("El aldeano se encuentra reparando.", e.getMessage());
        } catch (OroInsuficienteException e) {
            fail("Error inesperado.");
        }
        aldeano.ejecutarTareas();
    }

    @Test
    public void test11aldeanoEstaOcupadoConstruyendo() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        jugador.recolectarOro(1000);
        try {
            aldeano.construir(plazaCentral);
        } catch (OroInsuficienteException e) {
            fail("Error inesperado.");
        }
        try {
            aldeano.reparar(plazaCentral);
        } catch (AldeanoOcupadoException e) {
            assertEquals("El aldeano se encuentra construyendo.", e.getMessage());
        }
        aldeano.ejecutarTareas();
    }

    @Test
    public void test12aldeanoEstaOcupadoConstruyendo() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        jugador.recolectarOro(1000);
        try {
            aldeano.construir(plazaCentral);
        } catch (OroInsuficienteException e) {
            fail("Error inesperado.");
        }
        try {
            aldeano.construir(plazaCentral);
        } catch (AldeanoOcupadoException e) {
            assertEquals("El aldeano se encuentra construyendo.", e.getMessage());
        } catch (OroInsuficienteException e) {
            fail("Error inesperado.");
        }
        aldeano.ejecutarTareas();
    }

    @Test
    public void test14aldeanoEstaOcupadoReparando() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico");
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

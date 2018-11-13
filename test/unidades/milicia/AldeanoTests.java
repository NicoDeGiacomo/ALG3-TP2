package unidades.milicia;

import excepciones.unidades.AldeanoOcupadoException;
import main.Jugador;
import org.junit.Assert;
import org.junit.Test;
import unidades.edificio.Castillo;
import unidades.edificio.Cuartel;
import unidades.edificio.PlazaCentral;
import unidades.estados.aldeano.Construyendo;
import unidades.estados.aldeano.Ocioso;
import unidades.estados.aldeano.Reparando;
import unidades.estados.unidades.EnConstruccion;
import unidades.estados.unidades.Vivo;

public class AldeanoTests {

    @Test
    public void test01aldeanoSonCreadosCorrectamente() {
        Aldeano aldeano = new Aldeano(new Jugador("Nico"));
        Assert.assertEquals(50, aldeano.verVida());
        Assert.assertEquals(1, aldeano.verTamanio());
        Assert.assertTrue(aldeano.esMovible());

    }

    @Test
    public void test02aldeanoNoHaceDanio() {
        Aldeano aldeanoHaceDanio = new Aldeano(new Jugador("Nico"));
        Aldeano aldeanoRecibeDanio = new Aldeano(new Jugador("Nico"));
        aldeanoHaceDanio.provocarDanio(aldeanoRecibeDanio);
        Assert.assertEquals(50, aldeanoHaceDanio.verVida());
        Assert.assertEquals(50, aldeanoRecibeDanio.verVida());
    }

    @Test
    public void test03aldeanoSonDaniadas() {
        Aldeano aldeano = new Aldeano(new Jugador("Nico"));
        Assert.assertEquals(50, aldeano.verVida());
        aldeano.recibirDanio(20);
        Assert.assertEquals(30, aldeano.verVida());
    }

    @Test
    public void test04aldeanoArreglarNoHaceNada() {
        Aldeano aldeano = new Aldeano(new Jugador("Nico"));
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        PlazaCentral plazaCentral = new PlazaCentral(new Jugador("Nico"));
        Castillo castillo = new Castillo(new Jugador("Nico"));
        aldeano.arreglar(cuartel);
        aldeano.arreglar(plazaCentral);
        aldeano.arreglar(castillo);
        Assert.assertEquals(250, cuartel.verVida());
        Assert.assertEquals(450, plazaCentral.verVida());
        Assert.assertEquals(1000, castillo.verVida());
    }

    @Test
    public void test05aldeanoReparandoEdificioTieneVidaMaximaYAldeanoCambiaDeEstado() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        aldeano.reparar(plazaCentral);
        Assert.assertEquals(Reparando.class, aldeano.verEstadoDeAldeano().getClass() );
        aldeano.ejecutarTareas();
        Assert.assertEquals( 450, plazaCentral.verVida() );
        //ToDo: NICO
        //Assert.assertEquals( 20, jugador.verOro() );
        Assert.assertEquals(Ocioso.class, aldeano.verEstadoDeAldeano().getClass() );
        aldeano.construir(plazaCentral);
        Assert.assertEquals(Construyendo.class, aldeano.verEstadoDeAldeano().getClass() );
    }

    @Test
    public void test06aldeanoConstruyendoPlaza() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        aldeano.construir(plazaCentral);
        Assert.assertEquals(EnConstruccion.class, plazaCentral.verEstadoDeUnidad().getClass() );
        aldeano.ejecutarTareas();
        Assert.assertTrue(plazaCentral.esMapeable());
    }

    @Test
    public void test07aldeanoConstruyendoCuartel() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        Cuartel cuartel= new Cuartel(jugador);
        aldeano.construir(cuartel);
        Assert.assertEquals(EnConstruccion.class, cuartel.verEstadoDeUnidad().getClass() );
        Assert.assertTrue(cuartel.esMapeable());

    }

    @Test
    public void test06aldeanoConstruyePlazaYPasanTresTurnos() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        aldeano.construir(plazaCentral);
        Assert.assertEquals(EnConstruccion.class, plazaCentral.verEstadoDeUnidad().getClass() );
        Assert.assertTrue(plazaCentral.esMapeable());
        aldeano.ejecutarTareas();
        aldeano.ejecutarTareas();
        aldeano.ejecutarTareas();
        Assert.assertEquals(Vivo.class, plazaCentral.verEstadoDeUnidad().getClass() );
        Assert.assertTrue(plazaCentral.esMapeable());
    }

    @Test
    public void test08aldeanoObtieneOro() {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        aldeano.ejecutarTareas();
        //ToDo: NICO
        //Assert.assertEquals( 20, jugador.verOro() );
    }

    @Test
    public void test09aldeanoReparando() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        plazaCentral.recibirDanio(100);
        aldeano.reparar(plazaCentral);
        aldeano.ejecutarTareas();
        Assert.assertEquals( 375, plazaCentral.verVida() );
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
            Assert.assertEquals("El aldeano se encuentra reparando.", e.getMessage() );
        }
        aldeano.ejecutarTareas();
    }

    @Test
    public void test11aldeanoEstaOcupadoConstruyendo() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        aldeano.construir(plazaCentral);
        try {
            aldeano.reparar(plazaCentral);
        } catch (AldeanoOcupadoException e) {
            Assert.assertEquals("El aldeano se encuentra construyendo.", e.getMessage() );
        }
        aldeano.ejecutarTareas();
    }

    @Test
    public void test12aldeanoEstaOcupadoConstruyendo() throws AldeanoOcupadoException {
        Jugador jugador = new Jugador("Nico");
        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        aldeano.construir(plazaCentral);
        try {
            aldeano.construir(plazaCentral);
        } catch (AldeanoOcupadoException e) {
            Assert.assertEquals("El aldeano se encuentra construyendo.", e.getMessage() );
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
            Assert.assertEquals("El aldeano se encuentra reparando.", e.getMessage() );
        }
        aldeano.ejecutarTareas();
    }
}

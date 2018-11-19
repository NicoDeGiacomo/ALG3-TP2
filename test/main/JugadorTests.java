package main;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.FueraDeRangoException;
import excepciones.mapa.PosicionOcupadaException;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import unidades.edificio.PlazaCentral;
import unidades.milicia.Aldeano;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JugadorTests {

    @Test
    public void noSePuedeAgregarUnidadConOroInsuficiente() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico", mapa);
        Whitebox.setInternalState(jugador, "oro", 0);

        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        mapa.colocarUnidad(plazaCentral, new Point2D.Double(1, 1));

        assertThrows(OroInsuficienteException.class, () -> jugador.agregarUnidad(new Aldeano(jugador), plazaCentral), "El oro del jugador es insuficiente.");
    }

    @Test
    public void agregarUnidadConOroSuficiente() throws LimiteDePoblacionException, FueraDeRangoException, PosicionOcupadaException, OroInsuficienteException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico", mapa);
        Whitebox.setInternalState(jugador, "oro", new Aldeano(jugador).verPrecio());

        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        mapa.colocarUnidad(plazaCentral, new Point2D.Double(1, 1));

        jugador.agregarUnidad(new Aldeano(jugador), plazaCentral);
    }

    @Test
    public void noSePuedeSuperarElLimiteDePoblacion() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico", mapa);
        Whitebox.setInternalState(jugador, "poblacion", 50);
        Whitebox.setInternalState(jugador, "oro", 1000000);

        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        mapa.colocarUnidad(plazaCentral, new Point2D.Double(1, 1));

        assertThrows(LimiteDePoblacionException.class, () -> jugador.agregarUnidad(new Aldeano(jugador), plazaCentral), "El limite de población llegó al máximo.");
    }

    @Test
    public void lasMiliciasMuertasBajanElLimiteDePoblacion() throws OroInsuficienteException, FueraDeRangoException, PosicionOcupadaException, LimiteDePoblacionException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico", mapa);
        Whitebox.setInternalState(jugador, "poblacion", 49);
        Whitebox.setInternalState(jugador, "oro", 1000000);

        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        mapa.colocarUnidad(plazaCentral, new Point2D.Double(1, 1));

        Aldeano aldeano = new Aldeano(jugador);
        jugador.agregarUnidad(aldeano, plazaCentral);
        aldeano.recibirDanio(1000000);
        jugador.agregarUnidad(new Aldeano(jugador), plazaCentral);
    }

    @Test
    public void losEdificiosMuertosNoBajanElLimiteDePoblacion() throws OroInsuficienteException, FueraDeRangoException, PosicionOcupadaException, LimiteDePoblacionException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico", mapa);
        Whitebox.setInternalState(jugador, "poblacion", 49);
        Whitebox.setInternalState(jugador, "oro", 1000000);

        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        mapa.colocarUnidad(plazaCentral, new Point2D.Double(1, 1));
        jugador.agregarUnidad(plazaCentral, new Aldeano(jugador), new Point2D.Double(50, 50));

        Aldeano aldeano = new Aldeano(jugador);
        jugador.agregarUnidad(aldeano, plazaCentral);
        plazaCentral.recibirDanio(1000000);
        assertThrows(LimiteDePoblacionException.class, () -> jugador.agregarUnidad(new Aldeano(jugador), plazaCentral), "El limite de población llegó al máximo.");
    }

}

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
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

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
        jugador.agregarUnidad(plazaCentral, new Aldeano(jugador), new Point2D.Double(50, 50));

        Aldeano aldeano = new Aldeano(jugador);
        jugador.agregarUnidad(aldeano, plazaCentral);
        plazaCentral.recibirDanio(1000000);
        assertThrows(LimiteDePoblacionException.class, () -> jugador.agregarUnidad(new Aldeano(jugador), plazaCentral), "El limite de población llegó al máximo.");
    }

    @Test
    public void siFallaElMapaLaUnidadNoSeAgrega() throws OroInsuficienteException, LimiteDePoblacionException {
        Mapa mapa = mock(Mapa.class);
        Jugador jugador = new Jugador("Nico", mapa);
        Whitebox.setInternalState(jugador, "poblacion", 49);
        Whitebox.setInternalState(jugador, "oro", 1000000);

        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        doAnswer(invocation -> {
            throw new FueraDeRangoException("");
        }).when(mapa).agregarUnidadCercana(aldeano, plazaCentral);

        //Falla por error en el mapa y no deberia agregar la unidad
        assertThrows(FueraDeRangoException.class, () -> jugador.agregarUnidad(aldeano, plazaCentral));

        //Si la unidad no se agrego, deberia poder agregar una nueva
        jugador.agregarUnidad(new Aldeano(jugador), new PlazaCentral(jugador));

        //La anterior se agrego correctamente y ahora la poblacion esta en el limite
        assertThrows(LimiteDePoblacionException.class, () -> jugador.agregarUnidad(new Aldeano(jugador), plazaCentral), "El limite de población llegó al máximo.");
    }

}

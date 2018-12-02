package main;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.stubbing.Answer;
import unidades.edificios.PlazaCentral;
import unidades.milicias.Aldeano;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class JugadorTests {

    @Before
    public void before() {
        Mapa.destruir();
    }

    @Test
    public void noSePuedeAgregarUnidadConOroInsuficiente() {
        Jugador jugador = new Jugador("Nico");
        Whitebox.setInternalState(jugador, "mapa", mock(Mapa.class));
        Whitebox.setInternalState(jugador, "oro", 0);

        assertThrows(OroInsuficienteException.class, () -> jugador.agregarUnidad(new Aldeano(jugador), new PlazaCentral(jugador)), "El oro del jugador es insuficiente.");
    }

    @Test
    public void agregarUnidadConOroSuficiente() throws LimiteDePoblacionException, CoordenadaInvalidaException, OroInsuficienteException {
        Jugador jugador = new Jugador("Nico");
        Whitebox.setInternalState(jugador, "mapa", mock(Mapa.class));
        Whitebox.setInternalState(jugador, "mapa", mock(Mapa.class));
        Whitebox.setInternalState(jugador, "oro", new Aldeano(jugador).verPrecio());

        PlazaCentral plazaCentral = new PlazaCentral(jugador);

        jugador.agregarUnidad(new Aldeano(jugador), plazaCentral);
    }

    @Test
    public void noSePuedeSuperarElLimiteDePoblacion() {
        Jugador jugador = new Jugador("Nico");
        Whitebox.setInternalState(jugador, "mapa", mock(Mapa.class));
        Whitebox.setInternalState(jugador, "poblacion", 50);
        Whitebox.setInternalState(jugador, "oro", 1000000);

        assertThrows(LimiteDePoblacionException.class, () -> jugador.agregarUnidad(new Aldeano(jugador), new PlazaCentral(jugador)), "El limite de población llegó al máximo.");
    }

    @Test
    public void lasMiliciasMuertasBajanElLimiteDePoblacion() throws OroInsuficienteException, CoordenadaInvalidaException, LimiteDePoblacionException {
        Jugador jugador = new Jugador("Nico");
        Whitebox.setInternalState(jugador, "mapa", mock(Mapa.class));
        Whitebox.setInternalState(jugador, "poblacion", 49);
        Whitebox.setInternalState(jugador, "oro", 1000000);

        Aldeano aldeano = new Aldeano(jugador);
        jugador.agregarUnidad(aldeano, new PlazaCentral(jugador));
        aldeano.recibirDanio(1000000);
        jugador.agregarUnidad(new Aldeano(jugador), new PlazaCentral(jugador));
    }

    @Test
    public void losEdificiosMuertosNoBajanElLimiteDePoblacion() throws OroInsuficienteException, CoordenadaInvalidaException, LimiteDePoblacionException {
        Jugador jugador = new Jugador("Nico");
        Whitebox.setInternalState(jugador, "mapa", mock(Mapa.class));
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
    public void siFallaElMapaLaUnidadNoSeAgrega() throws OroInsuficienteException, LimiteDePoblacionException, CoordenadaInvalidaException {
        Mapa mapa = mock(Mapa.class);
        Jugador jugador = new Jugador("Nico");
        Whitebox.setInternalState(jugador, "mapa", mapa);
        Whitebox.setInternalState(jugador, "poblacion", 49);
        Whitebox.setInternalState(jugador, "oro", 1000000);

        Aldeano aldeano = new Aldeano(jugador);
        PlazaCentral plazaCentral = new PlazaCentral(jugador);
        doThrow(new CoordenadaInvalidaException("")).when(mapa).agregarUnidadCercana(aldeano, plazaCentral);

        //Falla por error en el mapa y no deberia agregar la unidad
        assertThrows(CoordenadaInvalidaException.class, () -> jugador.agregarUnidad(aldeano, plazaCentral));

        //Si la unidad no se agrego, deberia poder agregar una nueva
        jugador.agregarUnidad(new Aldeano(jugador), new PlazaCentral(jugador));

        //La anterior se agrego correctamente y ahora la poblacion esta en el limite
        assertThrows(LimiteDePoblacionException.class, () -> jugador.agregarUnidad(new Aldeano(jugador), plazaCentral), "El limite de población llegó al máximo.");
    }

}

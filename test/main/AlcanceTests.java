package main;

import excepciones.mapa.CoordenadaInvalidaException;
import org.junit.Test;
import unidades.edificio.Castillo;
import unidades.milicia.ArmaDeAsedio;
import unidades.milicia.Arquero;
import unidades.milicia.Espadachin;

import java.awt.geom.Point2D;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class AlcanceTests {
    @Test
    public void Test01UnidadEstaAlAlcanceDeArqueroEnOrigen() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero(mock(Jugador.class));
        Point2D coordenada1 = new Point2D.Double(0, 0),
                coordenada2 = new Point2D.Double(3, 0),
                coordenada3 = new Point2D.Double(3, 1),
                coordenada4 = new Point2D.Double(3, 3),
                coordenada5 = new Point2D.Double(4, 3),
                coordenada6 = new Point2D.Double(4, 4),
                coordenada7 = new Point2D.Double(10, 10);

        mapa.colocarDibujable(arquero, coordenada1);

        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada2));
        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada3));
        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada4));
        assertFalse(mapa.estaAlAlcance(coordenada1, coordenada5));
        assertFalse(mapa.estaAlAlcance(coordenada1, coordenada6));
        assertFalse(mapa.estaAlAlcance(coordenada1, coordenada7));
    }

    @Test
    public void Test01UnidadEstaAlAlcanceDeArmaDeAsedio() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        ArmaDeAsedio arma = new ArmaDeAsedio(mock(Jugador.class));
        Point2D coordenada1 = new Point2D.Double(0, 0),
                coordenada2 = new Point2D.Double(5, 0),
                coordenada3 = new Point2D.Double(5, 5),
                coordenada5 = new Point2D.Double(5, 6),
                coordenada6 = new Point2D.Double(6, 5),
                coordenada7 = new Point2D.Double(0, 6);

        mapa.colocarDibujable(arma, coordenada1);

        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada2));
        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada3));
        assertFalse(mapa.estaAlAlcance(coordenada1, coordenada5));
        assertFalse(mapa.estaAlAlcance(coordenada1, coordenada6));
        assertFalse(mapa.estaAlAlcance(coordenada1, coordenada7));
    }

    @Test
    public void Test02UnidadEstaAlAlcanceDeEspadachin() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Espadachin espadachin = new Espadachin(mock(Jugador.class));
        Point2D coordenada1 = new Point2D.Double(0, 0),
                coordenada2 = new Point2D.Double(1, 0),
                coordenada3 = new Point2D.Double(0, 1),
                coordenada5 = new Point2D.Double(1, 1),
                coordenada6 = new Point2D.Double(1, 2),
                coordenada4 = new Point2D.Double(10, 10);

        mapa.colocarDibujable(espadachin, coordenada1);

        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada2));
        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada3));
        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada5));
        assertFalse(mapa.estaAlAlcance(coordenada1, coordenada6));
        assertFalse(mapa.estaAlAlcance(coordenada1, coordenada4));
    }

    @Test
    public void Test03UnidadEstaAlAlcanceDeCastillo() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Castillo castillo = new Castillo(mock(Jugador.class));
        Point2D coordenada1 = new Point2D.Double(0, 0),
                coordenada2 = new Point2D.Double(0, 4),
                coordenada3 = new Point2D.Double(5, 5),
                coordenada4 = new Point2D.Double(4, 5);

        mapa.colocarDibujable(castillo, coordenada1);

        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada2));
        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada3));
        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada4));
    }

    @Test
    public void Test04UnidadEstaAlAlcanceDeArqueroDesplazado() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero(mock(Jugador.class));
        Point2D coordenada1 = new Point2D.Double(3, 3),
                coordenada2 = new Point2D.Double(0, 0),
                coordenada3 = new Point2D.Double(3, 6),
                coordenada4 = new Point2D.Double(6, 6),
                coordenada5 = new Point2D.Double(3, 7),
                coordenada6 = new Point2D.Double(7, 3),
                coordenada7 = new Point2D.Double(10, 10);

        mapa.colocarDibujable(arquero, coordenada1);

        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada2));
        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada3));
        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada4));
        assertFalse(mapa.estaAlAlcance(coordenada1, coordenada5));
        assertFalse(mapa.estaAlAlcance(coordenada1, coordenada6));
        assertFalse(mapa.estaAlAlcance(coordenada1, coordenada7));
    }

    @Test
    public void Test05CastilloAtacaALasUnidadesEnemicasAlAlcance() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Jugador jugador1 = new Jugador("Nico", mapa);

        Arquero arqueroEnemigo = new Arquero(mock(Jugador.class));
        Arquero arqueroAliado = new Arquero(jugador1);
        Castillo castillo = new Castillo(jugador1);

        mapa.colocarDibujable(castillo, new Point2D.Double(10, 10));
        mapa.colocarDibujable(arqueroAliado, new Point2D.Double(9, 10));
        mapa.colocarDibujable(arqueroEnemigo, new Point2D.Double(10, 9));

        castillo.ejecutarTareas();

        assertEquals(75, arqueroAliado.verVida());
        assertEquals(55, arqueroEnemigo.verVida());
    }

}
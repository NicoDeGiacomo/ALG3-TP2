package main;

import excepciones.mapa.FueraDeRangoException;
import excepciones.mapa.PosicionOcupadaException;
import excepciones.unidades.AtaqueIncorrectoException;
import org.junit.Assert;
import org.junit.Test;
import unidades.Unidad;
import unidades.edificio.Castillo;
import unidades.milicia.ArmaDeAsedio;
import unidades.milicia.Arquero;
import unidades.milicia.Espadachin;

import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlcanceTests {
    @Test
    public void Test01UnidadEstaAlAlcanceDeArqueroEnOrigen() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero(new Jugador("Nico", new Mapa()));
        Point2D coordenada1 = new Point2D.Double(0, 0),
                coordenada2 = new Point2D.Double(3, 0),
                coordenada3 = new Point2D.Double(3, 1),
                coordenada4 = new Point2D.Double(3, 3),
                coordenada5 = new Point2D.Double(4, 3),
                coordenada6 = new Point2D.Double(4,4),
                coordenada7 = new Point2D.Double(10, 10);

        mapa.colocarUnidad(arquero, coordenada1);

        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada2));
        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada3));
        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada4));
        Assert.assertFalse(mapa.estaAlAlcance(coordenada1, coordenada5));
        Assert.assertFalse(mapa.estaAlAlcance(coordenada1, coordenada6));
        Assert.assertFalse(mapa.estaAlAlcance(coordenada1, coordenada7));
    }

    @Test
    public void Test01UnidadEstaAlAlcanceDeArmaDeAsedio() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        ArmaDeAsedio arma = new ArmaDeAsedio(new Jugador("Nico", new Mapa()));
        Point2D coordenada1 = new Point2D.Double(0, 0),
                coordenada2 = new Point2D.Double(5, 0),
                coordenada3 = new Point2D.Double(5, 5),
                coordenada5 = new Point2D.Double(5, 6),
                coordenada6 = new Point2D.Double(6,5),
                coordenada7 = new Point2D.Double(0, 6);

        mapa.colocarUnidad(arma, coordenada1);

        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada2));
        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada3));
        Assert.assertFalse(mapa.estaAlAlcance(coordenada1, coordenada5));
        Assert.assertFalse(mapa.estaAlAlcance(coordenada1, coordenada6));
        Assert.assertFalse(mapa.estaAlAlcance(coordenada1, coordenada7));
    }

    @Test
    public void Test02UnidadEstaAlAlcanceDeEspadachin() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Espadachin espadachin = new Espadachin(new Jugador("Nico", new Mapa()));
        Point2D coordenada1 = new Point2D.Double(0, 0),
                coordenada2 = new Point2D.Double(1, 0),
                coordenada3 = new Point2D.Double(0, 1),
                coordenada5 = new Point2D.Double(1, 1),
                coordenada6 = new Point2D.Double(1, 2),
                coordenada4 = new Point2D.Double(10, 10);

        mapa.colocarUnidad(espadachin, coordenada1);

        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada2));
        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada3));
        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada5));
        Assert.assertFalse(mapa.estaAlAlcance(coordenada1, coordenada6));
        Assert.assertFalse(mapa.estaAlAlcance(coordenada1, coordenada4));
    }

    @Test
    public void Test03UnidadEstaAlAlcanceDeCastillo() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Castillo castillo = new Castillo(new Jugador("Nico", new Mapa()));
        Point2D coordenada1 = new Point2D.Double(0, 0),
                coordenada2 = new Point2D.Double(0, 4),
                coordenada3 = new Point2D.Double(5, 5),
                coordenada4 = new Point2D.Double(4, 5);

        mapa.colocarUnidad(castillo, coordenada1);

        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada2));
        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada3));
        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada4));
    }

    @Test
    public void Test04UnidadEstaAlAlcanceDeArqueroDesplazado() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero(new Jugador("Nico", new Mapa()));
        Point2D coordenada1 = new Point2D.Double(3, 3),
                coordenada2 = new Point2D.Double(0, 0),
                coordenada3 = new Point2D.Double(3, 6),
                coordenada4 = new Point2D.Double(6, 6),
                coordenada5 = new Point2D.Double(3, 7),
                coordenada6 = new Point2D.Double(7,3),
                coordenada7 = new Point2D.Double(10, 10);

        mapa.colocarUnidad(arquero, coordenada1);

        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada2));
        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada3));
        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada4));
        Assert.assertFalse(mapa.estaAlAlcance(coordenada1, coordenada5));
        Assert.assertFalse(mapa.estaAlAlcance(coordenada1, coordenada6));
        Assert.assertFalse(mapa.estaAlAlcance(coordenada1, coordenada7));
    }

    @Test
    public void Test05CastilloEstaAlAlcanceDeArquero() throws FueraDeRangoException, PosicionOcupadaException, AtaqueIncorrectoException {
        //TODO: Prueba ultra cuestionable...
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Peter", new Mapa());
        Arquero arquero = new Arquero(new Jugador("Nico", new Mapa()));
        Arquero arqueroAliado = new Arquero(jugador);
        Castillo castillo = new Castillo(jugador);
        Point2D coordenada1 = new Point2D.Double(3, 3),
                coordenada2 = new Point2D.Double(6, 6),
                coordenada3 = new Point2D.Double(4, 4);

        mapa.colocarUnidad(arquero , coordenada1);
        mapa.colocarUnidad(arqueroAliado , coordenada3);
        mapa.colocarUnidad(castillo, coordenada2);

        if ( mapa.estaAlAlcance(coordenada1, coordenada2) ) arquero.provocarDanio(castillo);

        assertEquals(990, castillo.verVida());

        List<Point2D> coordenadasCercana = mapa.obtenerCoordenadasCercanas(castillo);

        for(Point2D coordenada : coordenadasCercana) {
            Unidad atacado = (Unidad) mapa.obtenerDibujable(coordenada);
            if (atacado == null) continue;
            castillo.provocarDanio(atacado);
        }

        assertEquals(55, arquero.verVida());
        assertEquals(75, arqueroAliado.verVida());

    }

}
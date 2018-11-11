package main;

import exceptions.FueraDeRangoException;
import exceptions.PosicionOcupadaException;
import org.junit.Assert;
import org.junit.Test;

import unidades.milicia.Arquero;

import java.awt.geom.Point2D;

public class MapaTests {

    @Test
    public void MapaSeCreaDesocupado(){
        Mapa mapa = new Mapa();
        boolean estaOcupado = false;

        for (int i = 0; i < 100; i++)
        {
            for (int j = 0; j < 100; j++)
            {
                try {
                    estaOcupado = mapa.estaOcupado(new Point2D.Double(i,j));
                } catch (FueraDeRangoException e) {
                    e.printStackTrace();
                }
            }
        }

        Assert.assertEquals(false, estaOcupado);
    }

    @Test
    public void MapaColocaUnidadEnCoordenadaProvista() {
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero();
        Point2D coordenada1 = new Point2D.Double(1,1);
        Point2D coordenada2 = new Point2D.Double(1,2);

        try {
            mapa.colocarUnidad(arquero, coordenada1);
        }
        catch (PosicionOcupadaException e) {}
        catch (FueraDeRangoException e) {}

        try {
            Assert.assertEquals(true, mapa.estaOcupado(coordenada1));
        } catch (FueraDeRangoException e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals(false, mapa.estaOcupado(coordenada2));
        } catch (FueraDeRangoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void MapaNoPuedeColocarUnidadFueraDeRango(){
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero();
        Point2D coordenada1 = new Point2D.Double(200,200);
        boolean rompio = false;

        try {
            mapa.colocarUnidad(arquero, coordenada1);
        }
        catch (PosicionOcupadaException e) {}
        catch (FueraDeRangoException e) { rompio = true; }

        Assert.assertEquals(true, rompio);
    }

    @Test
    public void MapaNoPuedeColocarUnidadEncimaDeOtra(){
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero();
        Point2D coordenada1 = new Point2D.Double(1,1);
        boolean rompio = false;

        try {
            mapa.colocarUnidad(arquero, coordenada1);
            mapa.colocarUnidad(arquero, coordenada1);
        }
        catch (PosicionOcupadaException e) { rompio = true; }
        catch (FueraDeRangoException e) {}

        Assert.assertEquals(true, rompio);
    }

    @Test
    public void MapaDevuelveSiCeldaEstaAlAlcanceDeUnidad(){
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero();

        Point2D coordenada1 = new Point2D.Double(1,1);
        Point2D coordenada2 = new Point2D.Double(1,2);
        Point2D coordenada3 = new Point2D.Double(10,10);

        try {
            mapa.colocarUnidad(arquero, coordenada1);
        }
        catch (PosicionOcupadaException e) {}
        catch (FueraDeRangoException e) {}

        try {
            Assert.assertEquals(true , mapa.estaAlAlcance(coordenada1,coordenada2));
        } catch (FueraDeRangoException e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals(false , mapa.estaAlAlcance(coordenada1,coordenada3));
        } catch (FueraDeRangoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void MapaDevuelveReferenciaAUnidad(){
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero();

        Point2D coordenada1 = new Point2D.Double(1,1);

        try {
            mapa.colocarUnidad(arquero, coordenada1);
        }
        catch (PosicionOcupadaException e) {}
        catch (FueraDeRangoException e) {}

        Arquero chequeo = null;
        try {
            chequeo = (Arquero) mapa.obtenerDibujable(coordenada1);
        } catch (FueraDeRangoException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(arquero , chequeo);
    }

}

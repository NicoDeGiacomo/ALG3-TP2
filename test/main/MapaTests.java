package main;

import exceptions.FueraDeRangoException;
import exceptions.PosicionOcupadaException;
import org.junit.Assert;
import org.junit.Test;

import unidades.edificio.Cuartel;
import unidades.milicia.Arquero;

import java.awt.geom.Point2D;

public class MapaTests {

    @Test
    public void Test01MapaSeCreaDesocupado(){
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
    public void Test02MapaColocaMiliciaEnCoordenadaProvista() {
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero();
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2);

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
    public void Test03MapaColocaEdificioEnCoordenadaProvista() {
        Mapa mapa = new Mapa();
        Cuartel cuartel = new Cuartel();
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2),
                coordenada3 = new Point2D.Double(2,1),
                coordenada4 = new Point2D.Double(2,2);

        try {
            mapa.colocarUnidad(cuartel, coordenada1);
        }
        catch (PosicionOcupadaException e) {}
        catch (FueraDeRangoException e) {}

        try {
            Assert.assertEquals(true, mapa.estaOcupado(coordenada1));
            Assert.assertEquals(true, mapa.estaOcupado(coordenada2));
            Assert.assertEquals(true, mapa.estaOcupado(coordenada3));
            Assert.assertEquals(true, mapa.estaOcupado(coordenada4));
        } catch (FueraDeRangoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test04MapaNoPuedeColocarUnidadFueraDeRango(){
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero();
        boolean rompio = false;

        try {
            mapa.colocarUnidad(arquero, new Point2D.Double(200,200));
        }
        catch (PosicionOcupadaException e) {}
        catch (FueraDeRangoException e) { rompio = true; }

        Assert.assertEquals(true, rompio);
    }

    @Test
    public void Test05MapaNoPuedeColocarMiliciaEncimaDeOtra(){
        Mapa mapa = new Mapa();
        Arquero arquero1 = new Arquero(),
                arquero2 = new Arquero();
        Point2D coordenada = new Point2D.Double(1,1);
        boolean rompio = false;

        try {
            mapa.colocarUnidad(arquero1, coordenada);
            mapa.colocarUnidad(arquero2, coordenada);
        }
        catch (PosicionOcupadaException e) { rompio = true; }
        catch (FueraDeRangoException e) {}

        Assert.assertEquals(true, rompio);
    }

    @Test
    public void Test06MapaNoPuedeColocarEdificioEncimaDeMilicia(){
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero();
        Cuartel cuartel = new Cuartel();
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(0,0);
        boolean rompio = false;

        try {
            mapa.colocarUnidad(arquero, coordenada1);
            mapa.colocarUnidad(cuartel, coordenada2);
        }
        catch (PosicionOcupadaException e) { rompio = true; }
        catch (FueraDeRangoException e) {}

        Assert.assertEquals(true, rompio);
    }

    @Test
    public void Test07MapaDevuelveSiCeldaEstaAlAlcanceDeUnidad(){
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero();
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2),
                coordenada3 = new Point2D.Double(2,2),
                coordenada4 = new Point2D.Double(10,10);

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
            Assert.assertEquals(true , mapa.estaAlAlcance(coordenada1,coordenada3));
        } catch (FueraDeRangoException e) {
            e.printStackTrace();
        }
        try {
            Assert.assertEquals(false , mapa.estaAlAlcance(coordenada1,coordenada4));
        } catch (FueraDeRangoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test08MapaDevuelveReferenciaAMilicia(){
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

    @Test
    public void Test09MapaDevuelveReferenciaAEdificio(){
        Mapa mapa = new Mapa();
        Cuartel cuartel = new Cuartel();
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2),
                coordenada3 = new Point2D.Double(2,1),
                coordenada4 = new Point2D.Double(2,2);

        try {
            mapa.colocarUnidad(cuartel, coordenada1);
        }
        catch (PosicionOcupadaException e) {}
        catch (FueraDeRangoException e) {}

        Cuartel chequeo1 = null,
                chequeo2 = null,
                chequeo3 = null,
                chequeo4 = null;
        try {
            chequeo1 = (Cuartel) mapa.obtenerDibujable(coordenada1);
            chequeo2 = (Cuartel) mapa.obtenerDibujable(coordenada2);
            chequeo3 = (Cuartel) mapa.obtenerDibujable(coordenada3);
            chequeo4 = (Cuartel) mapa.obtenerDibujable(coordenada4);
        } catch (FueraDeRangoException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(cuartel , chequeo1);
        Assert.assertEquals(cuartel , chequeo2);
        Assert.assertEquals(cuartel , chequeo3);
        Assert.assertEquals(cuartel , chequeo4);
    }

}

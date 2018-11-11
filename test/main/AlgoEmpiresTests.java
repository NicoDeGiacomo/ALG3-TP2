package main;

import exceptions.FueraDeRangoException;
import exceptions.PosicionOcupadaException;
import org.junit.Assert;
import org.junit.Test;
import unidades.Unidad;
import unidades.milicia.Arquero;

import java.awt.geom.Point2D;

public class AlgoEmpiresTests {

    @Test
    public void doFooShouldReturnFoo(){
        AlgoEmpires algoEmpires = new AlgoEmpires();

        Assert.assertEquals("AlgoEmpires", algoEmpires.doFoo());
    }

    @Test
    public void MapaSeCreaDesocupado(){
        Mapa mapa = new Mapa();
        boolean estaOcupado = false;

        for (int i = 0; i < 100; i++)
        {
            for (int j = 0; j < 100; j++)
            {
                estaOcupado = mapa.estaOcupado(new Point2D.Double(i,j));
            }
        }

        Assert.assertEquals(false, estaOcupado);
    }

    @Test
    public void MapaColocaUnidadEnCoordenadaProvista(){
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero();
        Point2D coordenada1 = new Point2D.Double(1,1);
        Point2D coordenada2 = new Point2D.Double(1,2);

        try {
            mapa.colocarUnidad(arquero, coordenada1);
        }
        catch (PosicionOcupadaException e) {}
        catch (FueraDeRangoException e) {}

        Assert.assertEquals(true, mapa.estaOcupado(coordenada1));
        Assert.assertEquals(false, mapa.estaOcupado(coordenada2));
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

}

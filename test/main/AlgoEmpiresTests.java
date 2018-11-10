package main;

import org.junit.Assert;
import org.junit.Test;
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

}

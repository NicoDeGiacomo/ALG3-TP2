package main;

import excepciones.mapa.EspacioInsuficienteException;
import excepciones.mapa.FueraDeRangoException;
import excepciones.mapa.NoEsMovibleException;
import excepciones.mapa.PosicionOcupadaException;
import org.junit.Assert;
import org.junit.Test;

import org.junit.jupiter.api.Assertions;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.edificio.Cuartel;
import unidades.milicia.Arquero;

import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MapaTests {

    /*1 - Tests Iniciales*/

    @Test
    public void Test101MapaRecienCreadoObtenerDibujableDaNull(){
        Mapa mapa = new Mapa();
        Dibujable dibujable = null;
        try {
            dibujable = mapa.obtenerDibujable(new Point2D.Double(0,0));
        } catch (FueraDeRangoException e) {}

        Assert.assertEquals(null, dibujable);
    }

    @Test
    public void Test102MapaSeCreaDesocupado(){
        Mapa mapa = new Mapa();
        boolean estaOcupado = false;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                try {
                    estaOcupado = mapa.estaOcupado(new Point2D.Double(i,j));
                } catch (FueraDeRangoException e) {
                    e.printStackTrace();
                }
            }
        }

        Assert.assertFalse(estaOcupado);
    }

    @Test
    public void Test103QuitarUnidadDeMapaVacioNoHaceNada(){
        Mapa mapa = new Mapa();
        Unidad unidad = null;

        try {
            unidad = (Unidad) mapa.obtenerDibujable(new Point2D.Double(0,0));
        } catch (FueraDeRangoException e) {}

        Assert.assertEquals(null, unidad);

        mapa.quitarUnidad(unidad);

        try {
            unidad = (Unidad) mapa.obtenerDibujable(new Point2D.Double(0,0));
        } catch (FueraDeRangoException e) {}

        Assert.assertEquals(null, unidad);
    }

    @Test
    public void Test104ColocarUnidadNullNoHaceNada(){
        Mapa mapa = new Mapa();
        Unidad unidad = null;

        try {
            mapa.colocarUnidad(null, new Point2D.Double(0,0));
        } catch (FueraDeRangoException | PosicionOcupadaException e) {}

        try {
            unidad = (Unidad) mapa.obtenerDibujable(new Point2D.Double(0,0));
        } catch (FueraDeRangoException e) {}

        Assert.assertEquals(null, unidad);
    }

    @Test
    public void Test105ObtenerCoordenadasDeNullTraeConjuntoVacio(){
        Mapa mapa = new Mapa();

        List<Point2D> coordenadas = mapa.obtenerCoordenadas(null);

        Assert.assertEquals(0, coordenadas.size());
    }

    @Test
    public void Test106ObtenerUnidadesAlAlcanceDeNullTraeConjuntoVacio(){
        Mapa mapa = new Mapa();

        List<Dibujable> unidades = mapa.unidadesAlAlcance(null);

        Assert.assertEquals(0, unidades.size());
    }

    /*2 - Tests con Unidades*/

    /*
    @Test
    public void Test02MapaColocaMiliciaEnCoordenadaProvista() {
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero(new Jugador("Nico"));
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2);

        try {
            mapa.colocarUnidad(arquero, coordenada1);
        }
        catch (PosicionOcupadaException | FueraDeRangoException ignored) {
            fail("Error inesperado");
        }

        try {
            Assert.assertTrue(mapa.estaOcupado(coordenada1));
        } catch (FueraDeRangoException e) {
            e.printStackTrace();
        }
        try {
            Assert.assertFalse(mapa.estaOcupado(coordenada2));
        } catch (FueraDeRangoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test03MapaColocaEdificioEnCoordenadaProvista() {
        Mapa mapa = new Mapa();
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2),
                coordenada3 = new Point2D.Double(2,1),
                coordenada4 = new Point2D.Double(2,2);

        try {
            mapa.colocarUnidad(cuartel, coordenada1);
        }
        catch (PosicionOcupadaException | FueraDeRangoException ignored) {
            fail("Error inesperado");
        }

        try {
            Assert.assertTrue(mapa.estaOcupado(coordenada1));
            Assert.assertTrue(mapa.estaOcupado(coordenada2));
            Assert.assertTrue(mapa.estaOcupado(coordenada3));
            Assert.assertTrue(mapa.estaOcupado(coordenada4));
        } catch (FueraDeRangoException e) {
            fail("Error inesperado");
        }
    }

    @Test
    public void Test04MapaNoPuedeColocarUnidadFueraDeRango(){
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero(new Jugador("Nico"));

        try {
            mapa.colocarUnidad(arquero, new Point2D.Double(200,200));
        }
        catch (PosicionOcupadaException ignored) {
            fail("Error inesperado");
        }
        catch (FueraDeRangoException e) {
            assertEquals("Posición (200.0, 200.0) fuera del Margen del Mapa!", e.getMessage());
        }
    }

    @Test
    public void Test05MapaNoPuedeColocarMiliciaEncimaDeOtra(){
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico");
        Arquero arquero1 = new Arquero(jugador),
                arquero2 = new Arquero(jugador);
        Point2D coordenada = new Point2D.Double(1,1);

        try {
            mapa.colocarUnidad(arquero1, coordenada);
            mapa.colocarUnidad(arquero2, coordenada);
        }
        catch (PosicionOcupadaException e) {
            assertEquals("Ya existe una Unidad en (1.0, 1.0)", e.getMessage());
        }
        catch (FueraDeRangoException ignored) {
            fail("Error inesperado");
        }

    }

    @Test
    public void Test06MapaNoPuedeColocarEdificioEncimaDeMilicia(){
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero(new Jugador("Nico"));
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(0,0);
        boolean rompio = false;

        try {
            mapa.colocarUnidad(arquero, coordenada1);
            mapa.colocarUnidad(cuartel, coordenada2);
        }
        catch (PosicionOcupadaException e) { rompio = true; }
        catch (FueraDeRangoException ignored) {}

        Assert.assertTrue(rompio);
    }

    @Test
    public void Test07MapaDevuelveSiCeldaEstaAlAlcanceDeUnidad(){
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero(new Jugador("Nico"));
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2),
                coordenada3 = new Point2D.Double(2,2),
                coordenada4 = new Point2D.Double(10,10);

        try {
            mapa.colocarUnidad(arquero, coordenada1);
        }
        catch (PosicionOcupadaException | FueraDeRangoException e) {
            fail("Error inesperado");
        }

        try {
            Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada2));
        } catch (FueraDeRangoException e) {
            e.printStackTrace();
        }
        try {
            Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada3));
        } catch (FueraDeRangoException e) {
            e.printStackTrace();
        }
        try {
            Assert.assertFalse(mapa.estaAlAlcance(coordenada1, coordenada4));
        } catch (FueraDeRangoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test08MapaDevuelveReferenciaAMilicia(){
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero(new Jugador("Nico"));
        Point2D coordenada1 = new Point2D.Double(1,1);

        try {
            mapa.colocarUnidad(arquero, coordenada1);
        }
        catch (PosicionOcupadaException | FueraDeRangoException e) {
            fail("Error inesperado");
        }

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
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2),
                coordenada3 = new Point2D.Double(2,1),
                coordenada4 = new Point2D.Double(2,2);

        try {
            mapa.colocarUnidad(cuartel, coordenada1);
        }
        catch (PosicionOcupadaException | FueraDeRangoException ignored) {
            fail("Error inesperado");
        }

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
    }*/

    /*3 - Tests de Excepciones*/

    @Test
    public void Test301ObtenerDibujableConCoordenadaFueraDeRangoDaError(){
        Mapa mapa = new Mapa();
        Unidad unidad = null;
        boolean rompio = false;

        try {
            unidad = (Unidad) mapa.obtenerDibujable(new Point2D.Double(200,200));
        }
        catch (FueraDeRangoException e) { rompio = true; }

        Assert.assertTrue(rompio);

        rompio = false;

        try {
            unidad = (Unidad) mapa.obtenerDibujable(new Point2D.Double(-200,-200));
        }
        catch (FueraDeRangoException e) { rompio = true; }

        Assert.assertTrue(rompio);
    }

    @Test
    public void Test302ColocarMiliciaEncimaDeOtraDaError(){
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Arquero arquero1 = new Arquero(jugador),
                arquero2 = new Arquero(jugador);
        Point2D coordenada = new Point2D.Double(1,1);

        boolean rompio = false;

        try {
            mapa.colocarUnidad(arquero1, coordenada);
            mapa.colocarUnidad(arquero2, coordenada);
        }
        catch (FueraDeRangoException e) {
            fail("Error inesperado");
        }
        catch (PosicionOcupadaException e) {
            rompio = true;
        }

        Assert.assertTrue(rompio);
    }

    @Test
    public void Test303ColocarEdificioEncimaDeOtroDaError(){
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Cuartel cuartel1 = new Cuartel(jugador),
                cuartel2 = new Cuartel(jugador);
        Point2D coordenada1 = new Point2D.Double(5,5);
        Point2D coordenada2 = new Point2D.Double(4,4);

        boolean rompio = false;

        //En la misma coordenada.
        try {
            mapa.colocarUnidad(cuartel1, coordenada1);
            mapa.colocarUnidad(cuartel2, coordenada1);
        }
        catch (FueraDeRangoException e) {
            fail("Error inesperado");
        }
        catch (PosicionOcupadaException e) {
            rompio = true;
        }

        Assert.assertTrue(rompio);

        rompio = false;

        //En una coordenada que lo pisa al cuartel anterior solo en una 'esquina'.
        try {
            mapa.colocarUnidad(cuartel2, coordenada2);
        }
        catch (FueraDeRangoException e) {
            fail("Error inesperado");
        }
        catch (PosicionOcupadaException e) {
            rompio = true;
        }

        Assert.assertTrue(rompio);
    }

    public void Test304ColocarUnidadFueraDeRangoDaError(){
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Arquero arquero = new Arquero(jugador);
        boolean rompio = false;

        try {
            mapa.colocarUnidad(arquero, new Point2D.Double(200,200));
        }
        catch (FueraDeRangoException e) {
            rompio = true;
        }
        catch (PosicionOcupadaException e) {}

        Assert.assertTrue(rompio);

        rompio = false;

        try {
            mapa.colocarUnidad(arquero, new Point2D.Double(-200,-200));
        }
        catch (FueraDeRangoException e) {
            rompio = true;
        }
        catch (PosicionOcupadaException e) {}

        Assert.assertTrue(rompio);
    }

    @Test
    public void Test305MoverUnidadNoMovibleDaError() {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Cuartel cuartel = new Cuartel(jugador);
        Point2D origen = new Point2D.Double(1,1),
                destino = new Point2D.Double( 5,5);
        boolean rompio = false;


        try {
            mapa.colocarUnidad(cuartel, origen);
        } catch (FueraDeRangoException e) {
            fail("Error inesperado");
        } catch (PosicionOcupadaException e) {
            fail("Error inesperado");
        }

        try {
            mapa.moverUnidad(cuartel, destino);
        } catch (NoEsMovibleException e) {
            rompio = true;
        } catch (FueraDeRangoException e) {
            fail("Error inesperado");
        } catch (PosicionOcupadaException e) {
            fail("Error inesperado");
        }

        Assert.assertTrue(rompio);
    }

    @Test
    public void Test306ColocarUnidadCercanaSinEspacioDaError() {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Cuartel cuartel = new Cuartel(jugador);
        Arquero arquero = new Arquero(jugador);
        Point2D coordenada = new Point2D.Double(5,5);
        boolean rompio = false;


        try {
            mapa.colocarUnidad(cuartel, coordenada);
        } catch (FueraDeRangoException e) {
            fail("Error inesperado");
        } catch (PosicionOcupadaException e) {
            fail("Error inesperado");
        }

        for (int i = 0; i < 12; i++) {
            try {
                mapa.agregarUnidadCercana(cuartel, arquero);
            } catch (EspacioInsuficienteException e) {
                fail("Error inesperado");
            }
        }

        try {
            mapa.agregarUnidadCercana(cuartel, arquero);
        } catch (EspacioInsuficienteException e) {
            rompio = true;
        }

        Assert.assertTrue(rompio);
    }
}

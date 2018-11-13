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
import unidades.edificio.Castillo;
import unidades.edificio.Cuartel;
import unidades.milicia.Arquero;

import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MapaTests {

    private static final int TAMANIO = 100;

    /*1 - Tests Iniciales*/

    @Test
    public void Test101MapaRecienCreadoObtenerDibujableDaNull(){
        Mapa mapa = new Mapa();
        Dibujable dibujable = null;
        try {
            dibujable = mapa.obtenerDibujable(new Point2D.Double(0,0));
        } catch (FueraDeRangoException e) {
            fail("Error inesperado");
        }

        Assert.assertEquals(null, dibujable);
    }

    @Test
    public void Test102MapaSeCreaDesocupado(){
        Mapa mapa = new Mapa();
        boolean estaOcupado = false;

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                try {
                    estaOcupado = mapa.estaOcupado(new Point2D.Double(i,j));
                } catch (FueraDeRangoException e) {
                    fail("Error inesperado");
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
        } catch (FueraDeRangoException e) {
            fail("Error inesperado");
        }

        Assert.assertEquals(null, unidad);

        mapa.quitarUnidad(unidad);

        try {
            unidad = (Unidad) mapa.obtenerDibujable(new Point2D.Double(0,0));
        } catch (FueraDeRangoException e) {
            fail("Error inesperado");
        }

        Assert.assertEquals(null, unidad);
    }

    @Test
    public void Test104ColocarUnidadNullNoHaceNada(){
        Mapa mapa = new Mapa();
        Unidad unidad = null;

        try {
            mapa.colocarUnidad(null, new Point2D.Double(0,0));
        } catch (FueraDeRangoException | PosicionOcupadaException e) {
            fail("Error inesperado");
        }

        try {
            unidad = (Unidad) mapa.obtenerDibujable(new Point2D.Double(0,0));
        } catch (FueraDeRangoException e) {
            fail("Error inesperado");
        }

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

    @Test
    public void Test201MapaColocaMiliciaEnCoordenadaProvista() {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico");
        Arquero arquero = new Arquero(jugador);
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2);

        try {
            mapa.colocarUnidad(arquero, coordenada1);
        }
        catch (PosicionOcupadaException | FueraDeRangoException e) {
            fail("Error inesperado");
        }

        try {
            Assert.assertTrue(mapa.estaOcupado(coordenada1));
            Assert.assertFalse(mapa.estaOcupado(coordenada2));
        } catch (FueraDeRangoException e) {
            fail("Error inesperado");
        }
    }

    @Test
    public void Test202MapaColocaEdificioEnCoordenadaProvista() {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico");
        Cuartel cuartel = new Cuartel(jugador);
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
    public void Test203MapaDevuelveReferenciaAMilicia(){
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
            fail("Error inesperado");
        }

        Assert.assertEquals(arquero , chequeo);
    }

    @Test
    public void Test204MapaDevuelveReferenciaAEdificio(){
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
            fail("Error inesperado");
        }

        Assert.assertEquals(cuartel , chequeo1);
        Assert.assertEquals(cuartel , chequeo2);
        Assert.assertEquals(cuartel , chequeo3);
        Assert.assertEquals(cuartel , chequeo4);
    }

    @Test
    public void Test205MapaDevuelveSiCeldaEstaAlAlcanceDeUnidad(){
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
            Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada3));
            Assert.assertFalse(mapa.estaAlAlcance(coordenada1, coordenada4));
        } catch (FueraDeRangoException e) {
            fail("Error inesperado");
        }
    }

    @Test
    public void Test206MapaDevuelveReferenciaAUnidadesCercanas(){
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Arquero arquero1 = new Arquero(jugador),
                arquero2 = new Arquero(jugador),
                arquero3 = new Arquero(jugador),
                arquero4 = new Arquero(jugador),
                arquero5 = new Arquero(jugador),
                arquero6 = new Arquero(jugador),
                arquero7 = new Arquero(jugador),
                arquero8 = new Arquero(jugador),
                arquero9 = new Arquero(jugador),
                arquero10 = new Arquero(jugador),
                arquero11 = new Arquero(jugador),
                arquero12 = new Arquero(jugador);
        Cuartel cuartel = new Cuartel(jugador);

        try {
            mapa.colocarUnidad(cuartel, new Point2D.Double(5,5));
        } catch (FueraDeRangoException | PosicionOcupadaException e) {
            fail("Error inesperado");
        }

        try {
            mapa.agregarUnidadCercana(cuartel,arquero1);
            mapa.agregarUnidadCercana(cuartel,arquero2);
            mapa.agregarUnidadCercana(cuartel,arquero3);
            mapa.agregarUnidadCercana(cuartel,arquero4);
            mapa.agregarUnidadCercana(cuartel,arquero5);
            mapa.agregarUnidadCercana(cuartel,arquero6);
            mapa.agregarUnidadCercana(cuartel,arquero7);
            mapa.agregarUnidadCercana(cuartel,arquero8);
            mapa.agregarUnidadCercana(cuartel,arquero9);
            mapa.agregarUnidadCercana(cuartel,arquero10);
            mapa.agregarUnidadCercana(cuartel,arquero11);
            mapa.agregarUnidadCercana(cuartel,arquero12);
        }
        catch (EspacioInsuficienteException e) {
            fail("Error inesperado");
        }

        List<Dibujable> unidades = mapa.unidadesAlAlcance(cuartel);

        Assert.assertEquals(12, unidades.size());
    }

    @Test
    public void Test207MapaDevuelveReferenciaAUnaSolaUnidadCercanaSiSeCreaMuchasVeces(){
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Arquero arquero = new Arquero(jugador);
        Cuartel cuartel = new Cuartel(jugador);

        try {
            mapa.colocarUnidad(cuartel, new Point2D.Double(5,5));
        } catch (FueraDeRangoException | PosicionOcupadaException e) {
            fail("Error inesperado");
        }

        for (int i = 0; i < 12; i++) {
            try {
                mapa.agregarUnidadCercana(cuartel,arquero);
            }
            catch (EspacioInsuficienteException e) {
                fail("Error inesperado");
            }
        }

        List<Dibujable> unidades = mapa.unidadesAlAlcance(cuartel);

        Assert.assertEquals(1, unidades.size());
    }

    @Test
    public void Test208UnidadSeMueveUnCasillero(){
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Arquero arquero = new Arquero(jugador);
        Point2D origen = new Point2D.Double(5,4),
                destino1 = new Point2D.Double(5,5),
                destino2 = new Point2D.Double(6,6);

        try {
            mapa.colocarUnidad(arquero, origen);
        } catch (FueraDeRangoException | PosicionOcupadaException e) {
            fail("Error inesperado");
        }

        Assert.assertEquals(origen, mapa.obtenerCoordenadas(arquero).get(0));

        //Arquero se mueve en Y. Distancia es 1.
        try {
            mapa.moverUnidad(arquero, destino1);
        } catch (NoEsMovibleException | FueraDeRangoException | PosicionOcupadaException e) {
            fail("Error inesperado");
        }

        Assert.assertEquals(destino1, mapa.obtenerCoordenadas(arquero).get(0));

        //Arquero se mueve en diagonal. Distancia es sqrt(2).
        try {
            mapa.moverUnidad(arquero, destino2);
        } catch (NoEsMovibleException | FueraDeRangoException | PosicionOcupadaException e) {
            fail("Error inesperado");
        }

        Assert.assertEquals(destino2, mapa.obtenerCoordenadas(arquero).get(0));
    }

    @Test
    public void Test209MapaCreaCastilloEnCuadranteOpuestoAlYaCreado() {
        Mapa mapa = new Mapa();
        Jugador jugador1 = new Jugador("Nico"),
                jugador2 = new Jugador("Piter");
        Castillo castillo1 = new Castillo(jugador1),
                 castillo2 = new Castillo(jugador2);

        mapa.colocarUnidadEnExtremo(castillo1);
        mapa.colocarUnidadEnExtremo(castillo2);

        Point2D coordenadaCastillo1 = mapa.obtenerCoordenadas(castillo1).get(0),
                coordenadaCastillo2 = mapa.obtenerCoordenadas(castillo2).get(0);

        boolean comprobarX = (coordenadaCastillo1.getX() < (TAMANIO/2) && coordenadaCastillo2.getX() < (TAMANIO/2)) ||
                             (coordenadaCastillo1.getX() >= (TAMANIO/2) && coordenadaCastillo2.getX() >= (TAMANIO/2)) ||
                             (coordenadaCastillo1.getX() < (TAMANIO/2) && coordenadaCastillo2.getX() >= (TAMANIO/2)) ||
                             (coordenadaCastillo1.getX() >= (TAMANIO/2) && coordenadaCastillo2.getX() < (TAMANIO/2));

        boolean comprobarY = (coordenadaCastillo1.getY() < (TAMANIO/2) && coordenadaCastillo2.getY() < (TAMANIO/2)) ||
                (coordenadaCastillo1.getY() >= (TAMANIO/2) && coordenadaCastillo2.getY() >= (TAMANIO/2)) ||
                (coordenadaCastillo1.getY() < (TAMANIO/2) && coordenadaCastillo2.getY() >= (TAMANIO/2)) ||
                (coordenadaCastillo1.getY() >= (TAMANIO/2) && coordenadaCastillo2.getY() < (TAMANIO/2));

        Assert.assertTrue(comprobarX && comprobarY);
    }

    @Test
    public void Test210MapaPuedeCrearSoloDosCastillos() {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico");
        Castillo castillo1 = new Castillo(jugador),
                 castillo2 = new Castillo(jugador),
                 castillo3 = new Castillo(jugador);
        int count = 0;

        mapa.colocarUnidadEnExtremo(castillo1);
        mapa.colocarUnidadEnExtremo(castillo2);
        mapa.colocarUnidadEnExtremo(castillo3);

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                try {
                    if(mapa.estaOcupado(new Point2D.Double(i,j))) count++;
                } catch (FueraDeRangoException e) {
                    fail("Error inesperado");
                }
            }
        }

        //Cantidad de casilleros ocupados por los castillos. 2 castillos x (4x4 casilleros) = 32.
        Assert.assertEquals(32, count);
    }

    /*3 - Tests de Excepciones*/

    @Test
    public void Test301ObtenerDibujableConCoordenadaFueraDeRangoDaError(){
        Mapa mapa = new Mapa();
        Unidad unidad = null;

        try {
            unidad = (Unidad) mapa.obtenerDibujable(new Point2D.Double(200,200));
        }
        catch (FueraDeRangoException e) {
            assertEquals("Posición (200.0, 200.0) fuera del Margen del Mapa!", e.getMessage());
        }

        try {
            unidad = (Unidad) mapa.obtenerDibujable(new Point2D.Double(-200,-200));
        }
        catch (FueraDeRangoException e) {
            assertEquals("Posición (-200.0, -200.0) fuera del Margen del Mapa!", e.getMessage());
        }
    }

    @Test
    public void Test302ColocarMiliciaEncimaDeOtraDaError(){
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Arquero arquero1 = new Arquero(jugador),
                arquero2 = new Arquero(jugador);
        Point2D coordenada = new Point2D.Double(1,1);

        try {
            mapa.colocarUnidad(arquero1, coordenada);
            mapa.colocarUnidad(arquero2, coordenada);
        }
        catch (FueraDeRangoException e) {
            fail("Error inesperado");
        }
        catch (PosicionOcupadaException e) {
            assertEquals("Ya existe una Unidad en (1.0, 1.0)", e.getMessage());
        }
    }

    @Test
    public void Test303ColocarEdificioEncimaDeOtroDaError(){
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Cuartel cuartel1 = new Cuartel(jugador),
                cuartel2 = new Cuartel(jugador);
        Point2D coordenada1 = new Point2D.Double(5,5);
        Point2D coordenada2 = new Point2D.Double(4,4);

        //En la misma coordenada.
        try {
            mapa.colocarUnidad(cuartel1, coordenada1);
            mapa.colocarUnidad(cuartel2, coordenada1);
        }
        catch (FueraDeRangoException e) {
            fail("Error inesperado");
        }
        catch (PosicionOcupadaException e) {
            assertEquals("Ya existe una Unidad en (5.0, 5.0)", e.getMessage());
        }

        //En una coordenada que lo pisa al cuartel anterior solo en una 'esquina'.
        try {
            mapa.colocarUnidad(cuartel2, coordenada2);
        }
        catch (FueraDeRangoException e) {
            fail("Error inesperado");
        }
        catch (PosicionOcupadaException e) {
            assertEquals("Ya existe una Unidad en (5.0, 5.0)", e.getMessage());
        }
    }

    @Test
    public void Test304ColocarUnidadFueraDeRangoDaError(){
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Arquero arquero = new Arquero(jugador);

        try {
            mapa.colocarUnidad(arquero, new Point2D.Double(200,200));
        }
        catch (FueraDeRangoException e) {
            assertEquals("Posición (200.0, 200.0) fuera del Margen del Mapa!", e.getMessage());
        }
        catch (PosicionOcupadaException e) {}

        try {
            mapa.colocarUnidad(arquero, new Point2D.Double(-200,-200));
        }
        catch (FueraDeRangoException e) {
            assertEquals("Posición (-200.0, -200.0) fuera del Margen del Mapa!", e.getMessage());
        }
        catch (PosicionOcupadaException e) {

        }
    }

    @Test
    public void Test305MoverUnidadNoMovibleDaError() {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Cuartel cuartel = new Cuartel(jugador);
        Point2D origen = new Point2D.Double(1, 1),
                destino = new Point2D.Double(5, 5);

        try {
            mapa.colocarUnidad(cuartel, origen);
        } catch (FueraDeRangoException | PosicionOcupadaException ignored) {
            fail("Error inesperado");
        }

        try {
            mapa.moverUnidad(cuartel, destino);
        } catch (NoEsMovibleException e) {
            assertEquals("La Unidad que se trata de mover no es Movible!", e.getMessage());
        } catch (FueraDeRangoException | PosicionOcupadaException e) {
            fail("Error inesperado");
        }
    }

    @Test
    public void Test306MoverUnidadMasDeUnCasilleroDaError() {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Arquero arquero = new Arquero(jugador);
        Point2D origen = new Point2D.Double(1, 1),
                destino = new Point2D.Double(5, 5);

        try {
            mapa.colocarUnidad(arquero, origen);
        } catch (FueraDeRangoException | PosicionOcupadaException ignored) {
            fail("Error inesperado");
        }

        try {
            mapa.moverUnidad(arquero, destino);
        } catch (NoEsMovibleException | PosicionOcupadaException ignored) {
            fail("Error inesperado");
        } catch (FueraDeRangoException e) {
            assertEquals("Las Unidades se pueden mover a lo sumo 1 casillero por turno!", e.getMessage());
        }
    }

    @Test
    public void Test307MoverUnidadEncimaDeOtraDaError() {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Arquero arquero1 = new Arquero(jugador),
                arquero2 = new Arquero(jugador);
        Point2D origen = new Point2D.Double(1, 1),
                destino = new Point2D.Double(2, 2);

        try {
            mapa.colocarUnidad(arquero1, origen);
            mapa.colocarUnidad(arquero2, destino);
        } catch (FueraDeRangoException | PosicionOcupadaException ignored) {
            fail("Error inesperado");
        }

        try {
            mapa.moverUnidad(arquero1, destino);
        } catch (NoEsMovibleException | FueraDeRangoException ignored) {
            fail("Error inesperado");
        } catch (PosicionOcupadaException e) {
            assertEquals("La coordenada de Destino ya se encuentra ocupada!", e.getMessage());
        }
    }

    @Test
    public void Test308ColocarUnidadCercanaSinEspacioDaError () {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Cuartel cuartel = new Cuartel(jugador);
        Arquero arquero = new Arquero(jugador);
        Point2D coordenada = new Point2D.Double(5, 5);

        try {
            mapa.colocarUnidad(cuartel, coordenada);
        } catch (FueraDeRangoException | PosicionOcupadaException e) {
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
            assertEquals("No hay espacio para colocar una nueva Unidad!", e.getMessage());
        }
    }
}
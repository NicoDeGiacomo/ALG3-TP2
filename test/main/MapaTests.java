package main;

import excepciones.mapa.FueraDeRangoException;
import excepciones.mapa.NoEsMovibleException;
import excepciones.mapa.PosicionOcupadaException;
import org.junit.Assert;
import org.junit.Test;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.edificio.Castillo;
import unidades.edificio.Cuartel;
import unidades.milicia.Arquero;

import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
            fail("Error inesperado", e);
        }

        Assert.assertNull(dibujable);
    }

    @Test
    public void Test102MapaSeCreaDesocupado(){
        Mapa mapa = new Mapa();
        boolean estaOcupado = false;

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                try {
                    estaOcupado = mapa.obtenerDibujable(new Point2D.Double(i,j)) != null;
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

        Assert.assertNull(unidad);

        mapa.quitarUnidad(unidad);

        try {
            unidad = (Unidad) mapa.obtenerDibujable(new Point2D.Double(0,0));
        } catch (FueraDeRangoException e) {
            fail("Error inesperado", e);
        }

        Assert.assertNull(unidad);
    }

    @Test
    public void Test104ColocarUnidadNullNoHaceNada() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Unidad unidad = null;

        mapa.colocarUnidad(null, new Point2D.Double(0,0));

        try {
            unidad = (Unidad) mapa.obtenerDibujable(new Point2D.Double(0,0));
        } catch (FueraDeRangoException e) {
            fail("Error inesperado", e);
        }

        Assert.assertNull(unidad);
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

        List<Dibujable> unidades = mapa.dibujablesAlAlcance(null);

        Assert.assertEquals(0, unidades.size());
    }

    /*2 - Tests con Unidades*/

    @Test
    public void Test201MapaColocaMiliciaEnCoordenadaProvista() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico");
        Arquero arquero = new Arquero(jugador);
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2);

        mapa.colocarUnidad(arquero, coordenada1);
        Assert.assertNotNull(mapa.obtenerDibujable(coordenada1));
        Assert.assertNull(mapa.obtenerDibujable(coordenada2));
    }

    @Test
    public void Test202MapaColocaEdificioEnCoordenadaProvista() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico");
        Cuartel cuartel = new Cuartel(jugador);
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2),
                coordenada3 = new Point2D.Double(2,1),
                coordenada4 = new Point2D.Double(2,2);

        mapa.colocarUnidad(cuartel, coordenada1);
        Assert.assertNotNull(mapa.obtenerDibujable(coordenada1));
        Assert.assertNotNull(mapa.obtenerDibujable(coordenada2));
        Assert.assertNotNull(mapa.obtenerDibujable(coordenada3));
        Assert.assertNotNull(mapa.obtenerDibujable(coordenada4));
    }

    @Test
    public void Test203MapaDevuelveReferenciaAMilicia() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero(new Jugador("Nico"));
        Point2D coordenada1 = new Point2D.Double(1,1);

        mapa.colocarUnidad(arquero, coordenada1);

        Arquero chequeo = null;
        try {
            chequeo = (Arquero) mapa.obtenerDibujable(coordenada1);
        } catch (FueraDeRangoException e) {
            fail("Error inesperado", e);
        }

        Assert.assertEquals(arquero , chequeo);
    }

    @Test
    public void Test204MapaDevuelveReferenciaAEdificio() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Cuartel cuartel = new Cuartel(new Jugador("Nico"));
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2),
                coordenada3 = new Point2D.Double(2,1),
                coordenada4 = new Point2D.Double(2,2);

        mapa.colocarUnidad(cuartel, coordenada1);

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
    public void Test205MapaDevuelveSiCeldaEstaAlAlcanceDeUnidad() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero(new Jugador("Nico"));
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2),
                coordenada3 = new Point2D.Double(2,2),
                coordenada4 = new Point2D.Double(10,10);

        mapa.colocarUnidad(arquero, coordenada1);

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
        Arquero arquero = new Arquero(jugador);
        Cuartel cuartel = new Cuartel(jugador);

        try {
            mapa.colocarUnidad(cuartel, new Point2D.Double(5,5));
        } catch (FueraDeRangoException | PosicionOcupadaException e) {
            fail("Error inesperado");
        }

        try {
            mapa.agregarUnidadCercana(cuartel,arquero, new Point2D.Double(4,5));
        } catch (FueraDeRangoException | PosicionOcupadaException e) {
            fail("Error inesperado");
        }

        List<Dibujable> unidades = mapa.dibujablesAlAlcance(cuartel);

        Assert.assertEquals(1, unidades.size());
        Assert.assertEquals( arquero, unidades.get(0));
    }

    @Test
    public void Test207UnidadSeMueveUnCasillero(){
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
    public void Test208MapaCreaCastilloEnCuadranteOpuestoAlYaCreado() {
        Mapa mapa = new Mapa();
        Jugador jugador1 = new Jugador("Nico"),
                jugador2 = new Jugador("Piter");
        Castillo castillo1 = new Castillo(jugador1),
                 castillo2 = new Castillo(jugador2);

        mapa.colocarUnidadesEnExtremo(castillo1, null);
        mapa.colocarUnidadesEnExtremo(castillo2, null);

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
    public void Test209MapaPuedeCrearSoloDosCastillos() {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico");
        Castillo castillo1 = new Castillo(jugador),
                 castillo2 = new Castillo(jugador),
                 castillo3 = new Castillo(jugador);
        int count = 0;

        mapa.colocarUnidadesEnExtremo(castillo1, null);
        mapa.colocarUnidadesEnExtremo(castillo2, null);
        mapa.colocarUnidadesEnExtremo(castillo3, null);

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                try {
                    if(mapa.obtenerDibujable(new Point2D.Double(i,j)) != null) count++;
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

        try {
            mapa.obtenerDibujable(new Point2D.Double(200,200));
        }
        catch (FueraDeRangoException e) {
            assertEquals("Posición (200.0, 200.0) fuera del Margen del Mapa!", e.getMessage());
        }

        try {
            mapa.obtenerDibujable(new Point2D.Double(-200,-200));
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
        catch (PosicionOcupadaException e) {
            fail("Error inesperado");
        }

        try {
            mapa.colocarUnidad(arquero, new Point2D.Double(-200,-200));
        }
        catch (FueraDeRangoException e) {
            assertEquals("Posición (-200.0, -200.0) fuera del Margen del Mapa!", e.getMessage());
        }
        catch (PosicionOcupadaException e) {
            fail("Error inesperado");
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
    public void Test306MoverUnidadNullDaError() {
        Mapa mapa = new Mapa();
        Point2D destino = new Point2D.Double(5, 5);

        try {
            mapa.moverUnidad(null, destino);
        } catch (NoEsMovibleException e) {
            assertEquals("La Unidad que se trata de mover no es Movible!", e.getMessage());
        } catch (FueraDeRangoException | PosicionOcupadaException e) {
            fail("Error inesperado");
        }
    }

    @Test
    public void Test307MoverUnidadMasDeUnCasilleroDaError() {
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
    public void Test308MoverUnidadEncimaDeOtraDaError() {
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
    public void Test309ColocarUnidadCercanaEnLugarOcupadoDaError() {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter");
        Cuartel cuartel = new Cuartel(jugador);
        Arquero arquero = new Arquero(jugador);
        Point2D coordenada1 = new Point2D.Double(5, 5),
                coordenada2 = new Point2D.Double(4, 5);

        try {
            mapa.colocarUnidad(cuartel, coordenada1);
        } catch (FueraDeRangoException | PosicionOcupadaException e) {
            fail("Error inesperado");
        }

        try {
            mapa.agregarUnidadCercana(cuartel, arquero, coordenada2);
        } catch (FueraDeRangoException | PosicionOcupadaException e) {
            fail("Error inesperado");
        }

        try {
            mapa.agregarUnidadCercana(cuartel, arquero, coordenada2);
        } catch (PosicionOcupadaException e) {
            assertEquals("Ya existe una Unidad en (4.0, 5.0)", e.getMessage());
        } catch (FueraDeRangoException e) {
            fail("Error inesperado");
        }
    }

    @Test
    public void Test310ObtenerDibujableConCoordenadaNullDaError(){
        Mapa mapa = new Mapa();

        try {
            mapa.obtenerDibujable(null);
        } catch (FueraDeRangoException e) {
            Assert.assertEquals("No se puede validar una coordenada NULL!", e.getMessage());
        }
    }

    @Test
    public void Test311SaberSiEstaAlAlcanceConCoordenadaNullDaError(){
        Mapa mapa = new Mapa();
        Point2D origen = new Point2D.Double(1,1),
                destino = new Point2D.Double( 2,2);

        try {
            mapa.estaAlAlcance(null, destino);
        } catch (FueraDeRangoException e) {
            Assert.assertEquals("No se puede validar una coordenada NULL!", e.getMessage());
        }

        try {
            mapa.estaAlAlcance(origen, null);
        } catch (FueraDeRangoException e) {
            Assert.assertEquals("No se puede validar una coordenada NULL!", e.getMessage());
        }
    }

    @Test
    public void Test311SaberSiEstaAlAlcanceConCoordenadaFueraDeRangoDaError(){
        Mapa mapa = new Mapa();
        Point2D origen = new Point2D.Double(200,200);

        try {
            mapa.estaAlAlcance(origen, null);
        } catch (FueraDeRangoException e) {
            Assert.assertEquals("Posición (200.0, 200.0) fuera del Margen del Mapa!", e.getMessage());
        }
    }
}
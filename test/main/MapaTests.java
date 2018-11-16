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

public class MapaTests {

    private static final int TAMANIO = 100;

    /*1 - Tests Iniciales*/

    @Test
    public void Test101MapaRecienCreadoObtenerDibujableDaNull() throws FueraDeRangoException {
        Mapa mapa = new Mapa();
        Dibujable dibujable;
        dibujable = mapa.obtenerDibujable(new Point2D.Double(0,0));

        Assert.assertNull(dibujable);
    }

    @Test
    public void Test102MapaSeCreaDesocupado() throws FueraDeRangoException {
        Mapa mapa = new Mapa();
        boolean estaOcupado = false;

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                estaOcupado = mapa.obtenerDibujable(new Point2D.Double(i,j)) != null;
            }
        }

        Assert.assertFalse(estaOcupado);
    }

    @Test
    public void Test103QuitarUnidadDeMapaVacioNoHaceNada() throws FueraDeRangoException {
        Mapa mapa = new Mapa();

        Assert.assertNull(mapa.obtenerDibujable(new Point2D.Double(0,0)));

        mapa.quitarUnidad(null);

        Assert.assertNull(mapa.obtenerDibujable(new Point2D.Double(0,0)));
    }

    @Test
    public void Test104ColocarUnidadNullNoHaceNada() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Unidad unidad;

        mapa.colocarUnidad(null, new Point2D.Double(0,0));

        unidad = (Unidad) mapa.obtenerDibujable(new Point2D.Double(0,0));

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
        Jugador jugador = new Jugador("Nico", new Mapa());
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
        Jugador jugador = new Jugador("Nico", new Mapa());
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
        Arquero arquero = new Arquero(new Jugador("Nico", new Mapa()));
        Point2D coordenada1 = new Point2D.Double(1,1);

        mapa.colocarUnidad(arquero, coordenada1);

        Arquero chequeo;
        chequeo = (Arquero) mapa.obtenerDibujable(coordenada1);

        Assert.assertEquals(arquero , chequeo);
    }

    @Test
    public void Test204MapaDevuelveReferenciaAEdificio() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Cuartel cuartel = new Cuartel(new Jugador("Nico", new Mapa()));
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2),
                coordenada3 = new Point2D.Double(2,1),
                coordenada4 = new Point2D.Double(2,2);

        mapa.colocarUnidad(cuartel, coordenada1);

        Cuartel chequeo1,
                chequeo2,
                chequeo3,
                chequeo4;
        chequeo1 = (Cuartel) mapa.obtenerDibujable(coordenada1);
        chequeo2 = (Cuartel) mapa.obtenerDibujable(coordenada2);
        chequeo3 = (Cuartel) mapa.obtenerDibujable(coordenada3);
        chequeo4 = (Cuartel) mapa.obtenerDibujable(coordenada4);

        Assert.assertEquals(cuartel , chequeo1);
        Assert.assertEquals(cuartel , chequeo2);
        Assert.assertEquals(cuartel , chequeo3);
        Assert.assertEquals(cuartel , chequeo4);
    }

    @Test
    public void Test205MapaDevuelveSiCeldaEstaAlAlcanceDeUnidad() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero(new Jugador("Nico", new Mapa()));
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2),
                coordenada3 = new Point2D.Double(2,2),
                coordenada4 = new Point2D.Double(10,10);

        mapa.colocarUnidad(arquero, coordenada1);

        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada2));
        Assert.assertTrue(mapa.estaAlAlcance(coordenada1, coordenada3));
        Assert.assertFalse(mapa.estaAlAlcance(coordenada1, coordenada4));
    }

    @Test
    public void Test206MapaDevuelveReferenciaAUnidadesCercanas() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", new Mapa());
        Arquero arquero = new Arquero(jugador);
        Cuartel cuartel = new Cuartel(jugador);

        mapa.colocarUnidad(cuartel, new Point2D.Double(5,5));

        mapa.agregarUnidadCercana(cuartel,arquero, new Point2D.Double(4,5));

        List<Dibujable> unidades = mapa.dibujablesAlAlcance(cuartel);

        Assert.assertEquals(1, unidades.size());
        Assert.assertEquals( arquero, unidades.get(0));
    }

    @Test
    public void Test207UnidadSeMueveUnCasillero() throws FueraDeRangoException, PosicionOcupadaException, NoEsMovibleException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", new Mapa());
        Arquero arquero = new Arquero(jugador);
        Point2D origen = new Point2D.Double(5,4),
                destino1 = new Point2D.Double(5,5),
                destino2 = new Point2D.Double(6,6);

        mapa.colocarUnidad(arquero, origen);

        Assert.assertEquals(origen, mapa.obtenerCoordenadas(arquero).get(0));

        //Arquero se mueve en Y. Distancia es 1.
        mapa.moverUnidad(arquero, destino1);

        Assert.assertEquals(destino1, mapa.obtenerCoordenadas(arquero).get(0));

        //Arquero se mueve en diagonal. Distancia es sqrt(2).
        mapa.moverUnidad(arquero, destino2);

        Assert.assertEquals(destino2, mapa.obtenerCoordenadas(arquero).get(0));
    }

    @Test
    public void Test208MapaCreaCastilloEnCuadranteOpuestoAlYaCreado() {
        Mapa mapa = new Mapa();
        Jugador jugador1 = new Jugador("Nico", new Mapa()),
                jugador2 = new Jugador("Piter", new Mapa());
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
    public void Test209MapaPuedeCrearSoloDosCastillos() throws FueraDeRangoException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico", new Mapa());
        Castillo castillo1 = new Castillo(jugador),
                 castillo2 = new Castillo(jugador),
                 castillo3 = new Castillo(jugador);
        int count = 0;

        mapa.colocarUnidadesEnExtremo(castillo1, null);
        mapa.colocarUnidadesEnExtremo(castillo2, null);
        mapa.colocarUnidadesEnExtremo(castillo3, null);

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                if(mapa.obtenerDibujable(new Point2D.Double(i,j)) != null) count++;
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
    public void Test302ColocarMiliciaEncimaDeOtraDaError() throws FueraDeRangoException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", new Mapa());
        Arquero arquero1 = new Arquero(jugador),
                arquero2 = new Arquero(jugador);
        Point2D coordenada = new Point2D.Double(1,1);

        try {
            mapa.colocarUnidad(arquero1, coordenada);
            mapa.colocarUnidad(arquero2, coordenada);
        } catch (PosicionOcupadaException e) {
            assertEquals("Ya existe una Unidad en (1.0, 1.0)", e.getMessage());
        }
    }

    @Test
    public void Test303ColocarEdificioEncimaDeOtroDaError() throws FueraDeRangoException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", new Mapa());
        Cuartel cuartel1 = new Cuartel(jugador),
                cuartel2 = new Cuartel(jugador);
        Point2D coordenada1 = new Point2D.Double(5,5);
        Point2D coordenada2 = new Point2D.Double(4,4);

        //En la misma coordenada.
        try {
            mapa.colocarUnidad(cuartel1, coordenada1);
            mapa.colocarUnidad(cuartel2, coordenada1);
        } catch (PosicionOcupadaException e) {
            assertEquals("Ya existe una Unidad en (5.0, 5.0)", e.getMessage());
        }

        //En una coordenada que lo pisa al cuartel anterior solo en una 'esquina'.
        try {
            mapa.colocarUnidad(cuartel2, coordenada2);
        } catch (PosicionOcupadaException e) {
            assertEquals("Ya existe una Unidad en (5.0, 5.0)", e.getMessage());
        }
    }

    @Test
    public void Test304ColocarUnidadFueraDeRangoDaError() throws PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", new Mapa());
        Arquero arquero = new Arquero(jugador);

        try {
            mapa.colocarUnidad(arquero, new Point2D.Double(200,200));
        }
        catch (FueraDeRangoException e) {
            assertEquals("Posición (200.0, 200.0) fuera del Margen del Mapa!", e.getMessage());
        }

        try {
            mapa.colocarUnidad(arquero, new Point2D.Double(-200,-200));
        }
        catch (FueraDeRangoException e) {
            assertEquals("Posición (-200.0, -200.0) fuera del Margen del Mapa!", e.getMessage());
        }
    }

    @Test
    public void Test305MoverUnidadNoMovibleDaError() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", new Mapa());
        Cuartel cuartel = new Cuartel(jugador);
        Point2D origen = new Point2D.Double(1, 1),
                destino = new Point2D.Double(5, 5);

        mapa.colocarUnidad(cuartel, origen);

        try {
            mapa.moverUnidad(cuartel, destino);
        } catch (NoEsMovibleException e) {
            assertEquals("La Unidad que se trata de mover no es Movible!", e.getMessage());
        }
    }

    @Test
    public void Test306MoverUnidadNullDaError() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Point2D destino = new Point2D.Double(5, 5);

        try {
            mapa.moverUnidad(null, destino);
        } catch (NoEsMovibleException e) {
            assertEquals("La Unidad que se trata de mover no es Movible!", e.getMessage());
        }
    }

    @Test
    public void Test307MoverUnidadMasDeUnCasilleroDaError() throws FueraDeRangoException, PosicionOcupadaException, NoEsMovibleException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", new Mapa());
        Arquero arquero = new Arquero(jugador);
        Point2D origen = new Point2D.Double(1, 1),
                destino = new Point2D.Double(5, 5);

        mapa.colocarUnidad(arquero, origen);

        try {
            mapa.moverUnidad(arquero, destino);
        } catch (FueraDeRangoException e) {
            assertEquals("Las Unidades se pueden mover a lo sumo 1 casillero por turno!", e.getMessage());
        }
    }

    @Test
    public void Test308MoverUnidadEncimaDeOtraDaError() throws NoEsMovibleException, FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", new Mapa());
        Arquero arquero1 = new Arquero(jugador),
                arquero2 = new Arquero(jugador);
        Point2D origen = new Point2D.Double(1, 1),
                destino = new Point2D.Double(2, 2);

        mapa.colocarUnidad(arquero1, origen);
        mapa.colocarUnidad(arquero2, destino);

        try {
            mapa.moverUnidad(arquero1, destino);
        } catch (PosicionOcupadaException e) {
            assertEquals("La coordenada de Destino ya se encuentra ocupada!", e.getMessage());
        }
    }

    @Test
    public void Test309ColocarUnidadCercanaEnLugarOcupadoDaError() throws FueraDeRangoException, PosicionOcupadaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", new Mapa());
        Cuartel cuartel = new Cuartel(jugador);
        Arquero arquero = new Arquero(jugador);
        Point2D coordenada1 = new Point2D.Double(5, 5),
                coordenada2 = new Point2D.Double(4, 5);

        mapa.colocarUnidad(cuartel, coordenada1);

        mapa.agregarUnidadCercana(cuartel, arquero, coordenada2);

        try {
            mapa.agregarUnidadCercana(cuartel, arquero, coordenada2);
        } catch (PosicionOcupadaException e) {
            assertEquals("Ya existe una Unidad en (4.0, 5.0)", e.getMessage());
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
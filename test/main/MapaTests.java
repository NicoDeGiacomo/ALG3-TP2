package main;

import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.mapa.UnidadNoMovibleException;
import org.junit.Test;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.edificio.Castillo;
import unidades.edificio.Cuartel;
import unidades.milicia.Arquero;

import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MapaTests {

    private static final int TAMANIO = 100;

    /*1 - Tests Iniciales*/

    @Test
    public void Test101MapaRecienCreadoObtenerDibujableDaNull() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Dibujable dibujable = mapa.obtenerDibujable(new Point2D.Double(0,0));

        assertNull(dibujable);
    }

    @Test
    public void Test102MapaSeCreaDesocupado() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        boolean estaOcupado = false;

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                estaOcupado = mapa.obtenerDibujable(new Point2D.Double(i,j)) != null;
            }
        }

        assertFalse(estaOcupado);
    }

    @Test
    public void Test103QuitarUnidadDeMapaVacioNoHaceNada() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();

        assertNull(mapa.obtenerDibujable(new Point2D.Double(0,0)));
    }

    @Test
    public void Test104ColocarUnidadNullNoHaceNada() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Unidad unidad;

        mapa.colocarDibujable(null, new Point2D.Double(0,0));

        unidad = (Unidad) mapa.obtenerDibujable(new Point2D.Double(0,0));

        assertNull(unidad);
    }

    @Test
    public void Test106ObtenerUnidadesAlAlcanceDeNullTraeConjuntoVacio() {
        Mapa mapa = new Mapa();

        assertEquals(0, mapa.dibujablesAlAlcance(null).size());
    }

    @Test
    public void Test107AgregarUnidadCercanaANullNoHaceNada() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero(new Jugador("Nico", mapa));
        boolean estaOcupado = false;

        mapa.agregarUnidadCercana(arquero, null);

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                estaOcupado = mapa.obtenerDibujable(new Point2D.Double(i,j)) != null;
            }
        }

        assertFalse(estaOcupado);
    }

    @Test
    public void Test108VerAlcanceDeNullDaFalse() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Point2D origen = new Point2D.Double(0,0),
                destino = new Point2D.Double(1,1);

        assertFalse(mapa.estaAlAlcance(origen, destino));
    }

    /*2 - Tests con Unidades*/

    @Test
    public void Test201MapaColocaMiliciaEnCoordenadaProvista() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico", mapa);
        Arquero arquero = new Arquero(jugador);
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2);

        mapa.colocarDibujable(arquero, coordenada1);
        assertNotNull(mapa.obtenerDibujable(coordenada1));
        assertNull(mapa.obtenerDibujable(coordenada2));
    }

    @Test
    public void Test202MapaColocaEdificioEnCoordenadaProvista() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico", mapa);
        Cuartel cuartel = new Cuartel(jugador);
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2),
                coordenada3 = new Point2D.Double(2,1),
                coordenada4 = new Point2D.Double(2,2);

        mapa.colocarDibujable(cuartel, coordenada1);
        assertNotNull(mapa.obtenerDibujable(coordenada1));
        assertNotNull(mapa.obtenerDibujable(coordenada2));
        assertNotNull(mapa.obtenerDibujable(coordenada3));
        assertNotNull(mapa.obtenerDibujable(coordenada4));
    }

    @Test
    public void Test203MapaDevuelveReferenciaAMilicia() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero(new Jugador("Nico", mapa));
        Point2D coordenada1 = new Point2D.Double(1,1);

        mapa.colocarDibujable(arquero, coordenada1);

        Arquero chequeo = (Arquero) mapa.obtenerDibujable(coordenada1);

        assertEquals(arquero , chequeo);
    }

    @Test
    public void Test204MapaDevuelveReferenciaAEdificio() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Cuartel cuartel = new Cuartel(new Jugador("Nico", mapa));
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2),
                coordenada3 = new Point2D.Double(2,1),
                coordenada4 = new Point2D.Double(2,2);

        mapa.colocarDibujable(cuartel, coordenada1);

        Cuartel chequeo1,
                chequeo2,
                chequeo3,
                chequeo4;
        chequeo1 = (Cuartel) mapa.obtenerDibujable(coordenada1);
        chequeo2 = (Cuartel) mapa.obtenerDibujable(coordenada2);
        chequeo3 = (Cuartel) mapa.obtenerDibujable(coordenada3);
        chequeo4 = (Cuartel) mapa.obtenerDibujable(coordenada4);

        assertEquals(cuartel , chequeo1);
        assertEquals(cuartel , chequeo2);
        assertEquals(cuartel , chequeo3);
        assertEquals(cuartel , chequeo4);
    }

    @Test
    public void Test205MapaDevuelveSiCeldaEstaAlAlcanceDeUnidad() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Arquero arquero = new Arquero(new Jugador("Nico", mapa));
        Point2D coordenada1 = new Point2D.Double(1,1),
                coordenada2 = new Point2D.Double(1,2),
                coordenada3 = new Point2D.Double(2,2),
                coordenada4 = new Point2D.Double(10,10);

        mapa.colocarDibujable(arquero, coordenada1);

        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada2));
        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada3));
        assertFalse(mapa.estaAlAlcance(coordenada1, coordenada4));
    }

    @Test
    public void Test206MapaDevuelveReferenciaAUnidadesCercanas() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", mapa);
        Arquero arquero = new Arquero(jugador);
        Cuartel cuartel = new Cuartel(jugador);

        mapa.colocarDibujable(cuartel, new Point2D.Double(5,5));

        mapa.agregarUnidadCercana(arquero, cuartel, new Point2D.Double(4,5));

        List<Dibujable> unidades = mapa.dibujablesAlAlcance(cuartel);

        assertEquals(1, unidades.size());
        assertEquals( arquero, unidades.get(0));
    }

    @Test
    public void Test207UnidadSeMueveUnCasillero() throws CoordenadaInvalidaException, UnidadNoMovibleException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", mapa);
        Arquero arquero = new Arquero(jugador);
        Point2D origen = new Point2D.Double(5,4),
                destino1 = new Point2D.Double(5,5),
                destino2 = new Point2D.Double(6,6);

        mapa.colocarDibujable(arquero, origen);

        assertEquals(origen, mapa.obtenerCoordenadas(arquero).get(0));

        //Arquero se mueve en Y. Distancia es 1.
        mapa.moverUnidad(arquero, destino1);

        assertEquals(destino1, mapa.obtenerCoordenadas(arquero).get(0));

        //Arquero se mueve en diagonal. Distancia es sqrt(2).
        mapa.moverUnidad(arquero, destino2);

        assertEquals(destino2, mapa.obtenerCoordenadas(arquero).get(0));
    }

    @Test
    public void Test208MapaCreaCastilloEnCuadranteOpuestoAlYaCreado() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Jugador jugador1 = new Jugador("Nico", new Mapa()),
                jugador2 = new Jugador("Piter", new Mapa());
        Castillo castillo1 = new Castillo(jugador1),
                 castillo2 = new Castillo(jugador2);

        mapa.colocarUnidadesEnExtremo(castillo1, null);
        mapa.colocarUnidadesEnExtremo(castillo2, null);

        System.out.println("" + castillo1);
        System.out.println("" + mapa.obtenerCoordenadas(castillo1));

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

        assertTrue(comprobarX && comprobarY);
    }

    @Test
    public void Test209MapaPuedeCrearSoloDosCastillos() throws CoordenadaInvalidaException {
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
        assertEquals(32, count);
    }

    /*3 - Tests de Excepciones*/

    @Test
    public void Test301ObtenerDibujableConCoordenadaFueraDeRangoDaError(){
        Mapa mapa = new Mapa();

        try {
            mapa.obtenerDibujable(new Point2D.Double(200,200));
        }
        catch (CoordenadaInvalidaException e) {
            assertEquals("Posición (200.0, 200.0) fuera del Margen del Mapa!", e.getMessage());
        }

        try {
            mapa.obtenerDibujable(new Point2D.Double(-200,-200));
        }
        catch (CoordenadaInvalidaException e) {
            assertEquals("Posición (-200.0, -200.0) fuera del Margen del Mapa!", e.getMessage());
        }
    }

    @Test
    public void Test302ColocarMiliciaEncimaDeOtraDaError() {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", mapa);
        Arquero arquero1 = new Arquero(jugador),
                arquero2 = new Arquero(jugador);
        Point2D coordenada = new Point2D.Double(1,1);

        try {
            mapa.colocarDibujable(arquero1, coordenada);
            mapa.colocarDibujable(arquero2, coordenada);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Ya existe una Unidad en (1.0, 1.0)", e.getMessage());
        }
    }

    @Test
    public void Test303ColocarEdificioEncimaDeOtroDaError() {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", mapa);
        Cuartel cuartel1 = new Cuartel(jugador),
                cuartel2 = new Cuartel(jugador);
        Point2D coordenada1 = new Point2D.Double(5,5);
        Point2D coordenada2 = new Point2D.Double(4,4);

        //En la misma coordenada.
        try {
            mapa.colocarDibujable(cuartel1, coordenada1);
            mapa.colocarDibujable(cuartel2, coordenada1);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Ya existe una Unidad en (5.0, 5.0)", e.getMessage());
        }

        //En una coordenada que lo pisa al cuartel anterior solo en una 'esquina'.
        try {
            mapa.colocarDibujable(cuartel2, coordenada2);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Ya existe una Unidad en (5.0, 5.0)", e.getMessage());
        }
    }

    @Test
    public void Test304ColocarUnidadFueraDeRangoDaError() {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", mapa);
        Arquero arquero = new Arquero(jugador);

        try {
            mapa.colocarDibujable(arquero, new Point2D.Double(200,200));
        }
        catch (CoordenadaInvalidaException e) {
            assertEquals("Posición (200.0, 200.0) fuera del Margen del Mapa!", e.getMessage());
        }

        try {
            mapa.colocarDibujable(arquero, new Point2D.Double(-200,-200));
        }
        catch (CoordenadaInvalidaException e) {
            assertEquals("Posición (-200.0, -200.0) fuera del Margen del Mapa!", e.getMessage());
        }
    }

    @Test
    public void Test305MoverUnidadNoMovibleDaError() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", mapa);
        Cuartel cuartel = new Cuartel(jugador);
        Point2D origen = new Point2D.Double(1, 1),
                destino = new Point2D.Double(5, 5);

        mapa.colocarDibujable(cuartel, origen);

        try {
            mapa.moverUnidad(cuartel, destino);
        } catch (UnidadNoMovibleException e) {
            assertEquals("La Unidad que se trata de mover no es Movible!", e.getMessage());
        }
    }

    @Test
    public void Test306MoverUnidadNullDaError() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Point2D destino = new Point2D.Double(5, 5);

        try {
            mapa.moverUnidad(null, destino);
        } catch (UnidadNoMovibleException e) {
            assertEquals("La Unidad que se trata de mover no es Movible!", e.getMessage());
        }
    }

    @Test
    public void Test307MoverUnidadMasDeUnCasilleroDaError() throws CoordenadaInvalidaException, UnidadNoMovibleException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", mapa);
        Arquero arquero = new Arquero(jugador);
        Point2D origen = new Point2D.Double(1, 1),
                destino = new Point2D.Double(5, 5);

        mapa.colocarDibujable(arquero, origen);

        try {
            mapa.moverUnidad(arquero, destino);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Las Unidades se pueden mover a lo sumo 1 casillero por turno!", e.getMessage());
        }
    }

    @Test
    public void Test308MoverUnidadEncimaDeOtraDaError() throws UnidadNoMovibleException, CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", mapa);
        Arquero arquero1 = new Arquero(jugador),
                arquero2 = new Arquero(jugador);
        Point2D origen = new Point2D.Double(1, 1),
                destino = new Point2D.Double(2, 2);

        mapa.colocarDibujable(arquero1, origen);
        mapa.colocarDibujable(arquero2, destino);

        try {
            mapa.moverUnidad(arquero1, destino);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Ya existe una Unidad en (2.0, 2.0)", e.getMessage());
        }
    }

    @Test
    public void Test309ColocarUnidadCercanaEnLugarOcupadoDaError() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Piter", mapa);
        Cuartel cuartel = new Cuartel(jugador);
        Arquero arquero = new Arquero(jugador);
        Point2D coordenada1 = new Point2D.Double(5, 5),
                coordenada2 = new Point2D.Double(4, 5);

        mapa.colocarDibujable(cuartel, coordenada1);

        mapa.agregarUnidadCercana(arquero, cuartel, coordenada2);

        try {
            mapa.agregarUnidadCercana(arquero, cuartel, coordenada2);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Ya existe una Unidad en (4.0, 5.0)", e.getMessage());
        }
    }

    @Test
    public void Test310ObtenerDibujableConCoordenadaNullDaError(){
        Mapa mapa = new Mapa();

        try {
            mapa.obtenerDibujable(null);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("La coordenada es null.", e.getMessage());
        }
    }

    @Test
    public void Test311SaberSiEstaAlAlcanceConCoordenadaNullDaError(){
        Mapa mapa = new Mapa();
        Point2D origen = new Point2D.Double(1,1),
                destino = new Point2D.Double( 2,2);

        try {
            mapa.estaAlAlcance(null, destino);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("La coordenada es null.", e.getMessage());
        }

        try {
            mapa.estaAlAlcance(origen, null);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("La coordenada es null.", e.getMessage());
        }
    }

    @Test
    public void Test311SaberSiEstaAlAlcanceConCoordenadaFueraDeRangoDaError(){
        Mapa mapa = new Mapa();
        Point2D origen = new Point2D.Double(200,200);

        try {
            mapa.estaAlAlcance(origen, null);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Posición (200.0, 200.0) fuera del Margen del Mapa!", e.getMessage());
        }
    }

    @Test
    public void Test312AgregarMuchasUnidadesCercanasDaError() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico", mapa);
        Arquero arquero = new Arquero(jugador);
        Cuartel cuartel = new Cuartel(jugador);


        mapa.colocarDibujable(cuartel, new Point2D.Double(1,1));

        for (int i = 0; i < 12; i++) {
            mapa.agregarUnidadCercana(arquero, cuartel);
        }

        try {
            mapa.agregarUnidadCercana(arquero, cuartel);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("No hay espacio para crear una nueva Unidad!", e.getMessage());
        }
    }

    @Test
    public void Test313AgregarUnidadCercanaEnCoordenadaSinDibujableDaError() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico", mapa);
        Point2D coordenada = new Point2D.Double(0,0);
        Cuartel cuartel = new Cuartel(jugador);

        try {
            mapa.agregarUnidadCercana(null, cuartel, coordenada);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("No se encontró la Unidad Creadora en el Mapa!", e.getMessage());
        }
    }

    @Test
    public void Test314AgregarUnidadCernadaEnCoordenadaLejanaDaError() throws CoordenadaInvalidaException {
        Mapa mapa = new Mapa();
        Jugador jugador = new Jugador("Nico", mapa);
        Point2D coordenada1 = new Point2D.Double(0,0),
                coordenada2 = new Point2D.Double(15,15);
        Cuartel cuartel = new Cuartel(jugador);

        mapa.colocarDibujable(cuartel, coordenada1);

        try {
            mapa.agregarUnidadCercana(null, cuartel, coordenada2);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("La Posición de Creación debe estar al lado de la Unidad Creadora!", e.getMessage());
        }

    }

    @Test
    public void Test315ObtenerCoordenadasDeNullLanzaUnError() {
        Mapa mapa = new Mapa();

        assertThrows(CoordenadaInvalidaException.class, () -> mapa.obtenerCoordenadas(null));
    }
}
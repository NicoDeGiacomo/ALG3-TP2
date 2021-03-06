package main;

import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.mapa.UnidadNoMovibleException;
import org.junit.Before;
import org.junit.Test;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.edificios.Castillo;
import unidades.edificios.Cuartel;
import unidades.milicias.Arquero;

import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class MapaTests {

    @Before
    public void before() {
        Mapa.destruir();
    }

    /*1 - Tests Iniciales*/

    @Test
    public void Test101MapaRecienCreadoObtenerDibujableDaNull() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Dibujable dibujable = mapa.obtenerDibujable(new Point2D.Double(0, 0));

        assertNull(dibujable);
    }

    @Test
    public void Test102MapaSeCreaDesocupado() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        boolean estaOcupado = false;

        for (int i = 0; i < Mapa.TAMANIO; i++) {
            for (int j = 0; j < Mapa.TAMANIO; j++) {
                estaOcupado = mapa.obtenerDibujable(new Point2D.Double(i, j)) != null;
            }
        }

        assertFalse(estaOcupado);
    }

    @Test
    public void Test103QuitarUnidadDeMapaVacioNoHaceNada() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();

        assertNull(mapa.obtenerDibujable(new Point2D.Double(0, 0)));
    }

    @Test
    public void Test104ColocarUnidadNullNoHaceNada() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Unidad unidad;

        mapa.colocarDibujable(null, new Point2D.Double(0, 0));

        unidad = (Unidad) mapa.obtenerDibujable(new Point2D.Double(0, 0));

        assertNull(unidad);
    }

    @Test
    public void Test106ObtenerUnidadesAlAlcanceDeNullTraeConjuntoVacio() {
        Mapa mapa = Mapa.obtenerInstancia();

        assertEquals(0, mapa.dibujablesAlAlcance(null).size());
    }

    @Test
    public void Test107AgregarUnidadCercanaANullNoHaceNada() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Arquero arquero = new Arquero(mock(Jugador.class));
        boolean estaOcupado = false;

        mapa.agregarUnidadCercana(arquero, null);

        for (int i = 0; i < Mapa.TAMANIO; i++) {
            for (int j = 0; j < Mapa.TAMANIO; j++) {
                estaOcupado = mapa.obtenerDibujable(new Point2D.Double(i, j)) != null;
            }
        }

        assertFalse(estaOcupado);
    }

    @Test
    public void Test108VerAlcanceDeNullDaFalse() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Point2D origen = new Point2D.Double(0, 0),
                destino = new Point2D.Double(1, 1);

        assertFalse(mapa.estaAlAlcance(origen, destino));
    }

    /*2 - Tests con Unidades*/

    @Test
    public void Test201MapaColocaMiliciaEnCoordenadaProvista() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador = mock(Jugador.class);
        Arquero arquero = new Arquero(jugador);
        Point2D coordenada1 = new Point2D.Double(1, 1),
                coordenada2 = new Point2D.Double(1, 2);

        mapa.colocarDibujable(arquero, coordenada1);
        assertNotNull(mapa.obtenerDibujable(coordenada1));
        assertNull(mapa.obtenerDibujable(coordenada2));
    }

    @Test
    public void Test202MapaColocaEdificioEnCoordenadaProvista() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador = mock(Jugador.class);
        Cuartel cuartel = new Cuartel(jugador);
        Point2D coordenada1 = new Point2D.Double(1, 1),
                coordenada2 = new Point2D.Double(1, 2),
                coordenada3 = new Point2D.Double(2, 1),
                coordenada4 = new Point2D.Double(2, 2);

        mapa.colocarDibujable(cuartel, coordenada1);
        assertNotNull(mapa.obtenerDibujable(coordenada1));
        assertNotNull(mapa.obtenerDibujable(coordenada2));
        assertNotNull(mapa.obtenerDibujable(coordenada3));
        assertNotNull(mapa.obtenerDibujable(coordenada4));
    }

    @Test
    public void Test203MapaDevuelveReferenciaAMilicia() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Arquero arquero = new Arquero(mock(Jugador.class));
        Point2D coordenada1 = new Point2D.Double(1, 1);

        mapa.colocarDibujable(arquero, coordenada1);

        Arquero chequeo = (Arquero) mapa.obtenerDibujable(coordenada1);

        assertEquals(arquero, chequeo);
    }

    @Test
    public void Test204MapaDevuelveReferenciaAEdificio() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Cuartel cuartel = new Cuartel(mock(Jugador.class));
        Point2D coordenada1 = new Point2D.Double(1, 1),
                coordenada2 = new Point2D.Double(1, 2),
                coordenada3 = new Point2D.Double(2, 1),
                coordenada4 = new Point2D.Double(2, 2);

        mapa.colocarDibujable(cuartel, coordenada1);

        Cuartel chequeo1,
                chequeo2,
                chequeo3,
                chequeo4;
        chequeo1 = (Cuartel) mapa.obtenerDibujable(coordenada1);
        chequeo2 = (Cuartel) mapa.obtenerDibujable(coordenada2);
        chequeo3 = (Cuartel) mapa.obtenerDibujable(coordenada3);
        chequeo4 = (Cuartel) mapa.obtenerDibujable(coordenada4);

        assertEquals(cuartel, chequeo1);
        assertEquals(cuartel, chequeo2);
        assertEquals(cuartel, chequeo3);
        assertEquals(cuartel, chequeo4);
    }

    @Test
    public void Test205MapaDevuelveSiCeldaEstaAlAlcanceDeUnidad() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Arquero arquero = new Arquero(mock(Jugador.class));
        Point2D coordenada1 = new Point2D.Double(1, 1),
                coordenada2 = new Point2D.Double(1, 2),
                coordenada3 = new Point2D.Double(2, 2),
                coordenada4 = new Point2D.Double(10, 10);

        mapa.colocarDibujable(arquero, coordenada1);

        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada2));
        assertTrue(mapa.estaAlAlcance(coordenada1, coordenada3));
        assertFalse(mapa.estaAlAlcance(coordenada1, coordenada4));
    }

    @Test
    public void Test206MapaDevuelveReferenciaAUnidadesCercanas() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador = mock(Jugador.class);
        Arquero arquero = new Arquero(jugador);
        Cuartel cuartel = new Cuartel(jugador);

        mapa.colocarDibujable(cuartel, new Point2D.Double(15, 15));

        mapa.agregarUnidadCercana(arquero, cuartel, new Point2D.Double(14, 15));

        List<Dibujable> unidades = mapa.dibujablesAlAlcance(cuartel);

        assertEquals(1, unidades.size());
        assertEquals(arquero, unidades.get(0));
    }

    @Test
    public void Test207UnidadSeMueveUnCasillero() throws CoordenadaInvalidaException, UnidadNoMovibleException {
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador = mock(Jugador.class);
        Arquero arquero = new Arquero(jugador);
        Point2D origen = new Point2D.Double(20, 19),
                destino1 = new Point2D.Double(20, 20),
                destino2 = new Point2D.Double(21, 21);

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
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador1 = mock(Jugador.class),
                jugador2 = mock(Jugador.class);
        Castillo castillo1 = new Castillo(jugador1),
                castillo2 = new Castillo(jugador2);

        mapa.colocarUnidadesEnExtremo(castillo1, null);
        mapa.colocarUnidadesEnExtremo(castillo2, null);

        Point2D coordenadaCastillo1 = mapa.obtenerCoordenadas(castillo1).get(0),
                coordenadaCastillo2 = mapa.obtenerCoordenadas(castillo2).get(0);

        boolean comprobarX = (coordenadaCastillo1.getX() < (Mapa.TAMANIO / 2) && coordenadaCastillo2.getX() < (Mapa.TAMANIO / 2)) ||
                (coordenadaCastillo1.getX() >= (Mapa.TAMANIO / 2) && coordenadaCastillo2.getX() >= (Mapa.TAMANIO / 2)) ||
                (coordenadaCastillo1.getX() < (Mapa.TAMANIO / 2) && coordenadaCastillo2.getX() >= (Mapa.TAMANIO / 2)) ||
                (coordenadaCastillo1.getX() >= (Mapa.TAMANIO / 2) && coordenadaCastillo2.getX() < (Mapa.TAMANIO / 2));

        boolean comprobarY = (coordenadaCastillo1.getY() < (Mapa.TAMANIO / 2) && coordenadaCastillo2.getY() < (Mapa.TAMANIO / 2)) ||
                (coordenadaCastillo1.getY() >= (Mapa.TAMANIO / 2) && coordenadaCastillo2.getY() >= (Mapa.TAMANIO / 2)) ||
                (coordenadaCastillo1.getY() < (Mapa.TAMANIO / 2) && coordenadaCastillo2.getY() >= (Mapa.TAMANIO / 2)) ||
                (coordenadaCastillo1.getY() >= (Mapa.TAMANIO / 2) && coordenadaCastillo2.getY() < (Mapa.TAMANIO / 2));

        assertTrue(comprobarX && comprobarY);
    }

    @Test
    public void Test209MapaPuedeCrearSoloDosCastillos() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador = mock(Jugador.class);
        Castillo castillo1 = new Castillo(jugador),
                castillo2 = new Castillo(jugador),
                castillo3 = new Castillo(jugador);
        int count = 0;

        mapa.colocarUnidadesEnExtremo(castillo1, null);
        mapa.colocarUnidadesEnExtremo(castillo2, null);
        mapa.colocarUnidadesEnExtremo(castillo3, null);

        for (int i = 0; i < Mapa.TAMANIO; i++) {
            for (int j = 0; j < Mapa.TAMANIO; j++) {
                if (mapa.obtenerDibujable(new Point2D.Double(i, j)) != null) count++;
            }
        }

        //Cantidad de casilleros ocupados por los castillos. 2 castillos x (4x4 casilleros) = 32.
        assertEquals(32, count);
    }

    /*3 - Tests de Excepciones*/

    @Test
    public void Test301ObtenerDibujableConCoordenadaFueraDeRangoDaError() {
        Mapa mapa = Mapa.obtenerInstancia();

        try {
            mapa.obtenerDibujable(new Point2D.Double(200, 200));
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Posición (200.0, 200.0) fuera del Margen del Mapa!", e.getMessage());
        }

        try {
            mapa.obtenerDibujable(new Point2D.Double(-200, -200));
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Posición (-200.0, -200.0) fuera del Margen del Mapa!", e.getMessage());
        }
    }

    @Test
    public void Test302ColocarMiliciaEncimaDeOtraDaError() {
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador = mock(Jugador.class);
        Arquero arquero1 = new Arquero(jugador),
                arquero2 = new Arquero(jugador);
        Point2D coordenada = new Point2D.Double(1, 1);

        try {
            mapa.colocarDibujable(arquero1, coordenada);
            mapa.colocarDibujable(arquero2, coordenada);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Ya existe una Unidad en (1.0, 1.0)", e.getMessage());
        }
    }

    @Test
    public void Test303ColocarEdificioEncimaDeOtroDaError() {
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador = mock(Jugador.class);
        Cuartel cuartel1 = new Cuartel(jugador),
                cuartel2 = new Cuartel(jugador);
        Point2D coordenada1 = new Point2D.Double(10, 10);
        Point2D coordenada2 = new Point2D.Double(12, 12);

        //En la misma coordenada.
        try {
            mapa.colocarDibujable(cuartel1, coordenada1);
            mapa.colocarDibujable(cuartel2, coordenada1);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Ya existe una Unidad en (10.0, 10.0)", e.getMessage());
        }

        //En una coordenada que lo pisa al cuartel anterior solo en una 'esquina'.
        try {
            mapa.colocarDibujable(cuartel2, coordenada2);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Ya existe una Unidad en (12.0, 12.0)", e.getMessage());
        }
    }

    @Test
    public void Test304ColocarUnidadFueraDeRangoDaError() {
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador = mock(Jugador.class);
        Arquero arquero = new Arquero(jugador);

        try {
            mapa.colocarDibujable(arquero, new Point2D.Double(200, 200));
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Posición (200.0, 200.0) fuera del Margen del Mapa!", e.getMessage());
        }

        try {
            mapa.colocarDibujable(arquero, new Point2D.Double(-200, -200));
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Posición (-200.0, -200.0) fuera del Margen del Mapa!", e.getMessage());
        }
    }

    @Test
    public void Test305MoverUnidadNoMovibleDaError() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador = mock(Jugador.class);
        Cuartel cuartel = new Cuartel(jugador);
        Point2D origen = new Point2D.Double(0, 0),
                destino = new Point2D.Double(10, 10);

        mapa.colocarDibujable(cuartel, origen);

        try {
            mapa.moverUnidad(cuartel, destino);
        } catch (UnidadNoMovibleException e) {
            assertEquals("La Unidad que se trata de mover no es Movible!", e.getMessage());
        }
    }

    @Test
    public void Test306MoverUnidadNullDaError() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Point2D destino = new Point2D.Double(5, 5);

        try {
            mapa.moverUnidad(null, destino);
        } catch (UnidadNoMovibleException e) {
            assertEquals("La Unidad que se trata de mover no es Movible!", e.getMessage());
        }
    }

    @Test
    public void Test307MoverUnidadMasDeUnCasilleroDaError() throws CoordenadaInvalidaException, UnidadNoMovibleException {
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador = mock(Jugador.class);
        Arquero arquero = new Arquero(jugador);
        Point2D origen = new Point2D.Double(15, 15),
                destino = new Point2D.Double(19, 19);

        mapa.colocarDibujable(arquero, origen);

        try {
            mapa.moverUnidad(arquero, destino);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("La Unidad se puede mover a lo sumo 1 casillero/s por turno!", e.getMessage());
        }
    }

    @Test
    public void Test308MoverUnidadEncimaDeOtraDaError() throws UnidadNoMovibleException, CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador = mock(Jugador.class);
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
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador = mock(Jugador.class);
        Cuartel cuartel = new Cuartel(jugador);
        Arquero arquero = new Arquero(jugador);
        Point2D coordenada1 = new Point2D.Double(15, 15),
                coordenada2 = new Point2D.Double(14, 15);

        mapa.colocarDibujable(cuartel, coordenada1);

        mapa.agregarUnidadCercana(arquero, cuartel, coordenada2);

        try {
            mapa.agregarUnidadCercana(arquero, cuartel, coordenada2);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Ya existe una Unidad en (14.0, 15.0)", e.getMessage());
        }
    }

    @Test
    public void Test310ObtenerDibujableConCoordenadaNullDaError() {
        Mapa mapa = Mapa.obtenerInstancia();

        try {
            mapa.obtenerDibujable(null);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("La Coordenada es NULL.", e.getMessage());
        }
    }

    @Test
    public void Test311SaberSiEstaAlAlcanceConCoordenadaNullDaError() {
        Mapa mapa = Mapa.obtenerInstancia();
        Point2D origen = new Point2D.Double(1, 1),
                destino = new Point2D.Double(2, 2);

        try {
            mapa.estaAlAlcance(null, destino);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("La Coordenada es NULL.", e.getMessage());
        }

        try {
            mapa.estaAlAlcance(origen, null);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("La Coordenada es NULL.", e.getMessage());
        }
    }

    @Test
    public void Test311SaberSiEstaAlAlcanceConCoordenadaFueraDeRangoDaError() {
        Mapa mapa = Mapa.obtenerInstancia();
        Point2D origen = new Point2D.Double(200, 200);

        try {
            mapa.estaAlAlcance(origen, null);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("Posición (200.0, 200.0) fuera del Margen del Mapa!", e.getMessage());
        }
    }

    @Test
    public void Test312AgregarMuchasUnidadesCercanasDaError() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador = mock(Jugador.class);
        Arquero arquero = new Arquero(jugador);
        Cuartel cuartel = new Cuartel(jugador);


        mapa.colocarDibujable(cuartel, new Point2D.Double(15, 15));

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
    public void Test313AgregarUnidadCercanaEnCoordenadaSinDibujableDaError() {
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador = mock(Jugador.class);
        Point2D coordenada = new Point2D.Double(0, 0);
        Cuartel cuartel = new Cuartel(jugador);

        try {
            mapa.agregarUnidadCercana(null, cuartel, coordenada);
        } catch (CoordenadaInvalidaException e) {
            assertEquals("No se encontró la Unidad Creadora en el Mapa!", e.getMessage());
        }
    }

    @Test
    public void Test314AgregarUnidadCernadaEnCoordenadaLejanaDaError() throws CoordenadaInvalidaException {
        Mapa mapa = Mapa.obtenerInstancia();
        Jugador jugador = mock(Jugador.class);
        Point2D coordenada1 = new Point2D.Double(0, 0),
                coordenada2 = new Point2D.Double(15, 15);
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
        Mapa mapa = Mapa.obtenerInstancia();

        assertThrows(CoordenadaInvalidaException.class, () -> mapa.obtenerCoordenadas(null));
    }
}
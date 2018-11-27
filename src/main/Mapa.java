package main;

import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.mapa.UnidadNoMovibleException;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.edificio.Castillo;

import java.awt.geom.Point2D;
import java.util.*;

public class Mapa {
    //Para obtener una óptima experiencia probando la pantalla, dejar TAMANIO = 25;
    public static final int TAMANIO = 25;
    private static final int ESPACIO_LIBRE = 6,
                             DISTANCIA_PROXIMA = 1;
    private Dibujable[][] mapa = new Dibujable[TAMANIO][TAMANIO];

    public void colocarDibujable(Dibujable dibujable, Point2D coordenada) throws CoordenadaInvalidaException {
        if (dibujable == null)
            return;

        validarCoordenada(coordenada);

        int tamanioUnidad = obtenerTamanioMapeable(dibujable);
        for (int i = 0; i < tamanioUnidad; i++) {
            for (int j = 0; j < tamanioUnidad; j++) {
                validarCoordenada(new Point2D.Double(coordenada.getX() + i, coordenada.getY() + j));
            }
        }

        for (int i = 0; i < tamanioUnidad; i++) {
            for (int j = 0; j < tamanioUnidad; j++) {
                mapa[(int) coordenada.getX() + i][(int) coordenada.getY() + j] = dibujable;
            }
        }
    }

    public Dibujable obtenerDibujable(Point2D coordenada) throws CoordenadaInvalidaException {
        validarCoordenadaEnMapa(coordenada);

        Dibujable dibujable = mapa[(int) coordenada.getX()][(int) coordenada.getY()];

        if (dibujable != null && !((Unidad) dibujable).esMapeable()) {
            quitarUnidad((Unidad) dibujable);
            return null;
        }

        return dibujable;
    }

    public List<Dibujable> obtenerTodosLosDibujables() {
        List<Dibujable> dibujables = new ArrayList<>();

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                if (this.mapa[i][j] != null && !dibujables.contains(mapa[i][j]))
                    dibujables.add(this.mapa[i][j]);
            }
        }

        return dibujables;
    }

    public List<Point2D> obtenerCoordenadas(Dibujable dibujable) throws CoordenadaInvalidaException {
        if (dibujable == null) throw new CoordenadaInvalidaException("La Unidad es NULL!");

        List<Point2D> coordenadas = new ArrayList<>();

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                if (mapa[i][j] == dibujable) {
                    coordenadas.add(new Point2D.Double(i, j));
                }
            }
        }

        return coordenadas;
    }

    private int obtenerTamanioMapeable(Dibujable dibujable) {
        return (dibujable.verTamanio() < 1) ? (1) : ((int) Math.sqrt(dibujable.verTamanio()));
    }

    private boolean estaOcupado(Point2D coordenada) throws CoordenadaInvalidaException {
        return obtenerDibujable(coordenada) != null;
    }

    private Point2D obtenerEsquinaCuadrante(int cuadrante) {
        Point2D coordenadaEsquina = null;

        switch (cuadrante) {
            case 0: coordenadaEsquina = new Point2D.Double(ESPACIO_LIBRE / 2, ESPACIO_LIBRE / 2);
                break;
            case 1: coordenadaEsquina = new Point2D.Double(TAMANIO - ESPACIO_LIBRE - (TAMANIO % 2 != 0 ? 1 : 0), ESPACIO_LIBRE / 2);
                break;
            case 2: coordenadaEsquina = new Point2D.Double(ESPACIO_LIBRE / 2, TAMANIO - ESPACIO_LIBRE - (TAMANIO % 2 != 0 ? 1 : 0));
                break;
            case 3: coordenadaEsquina = new Point2D.Double(TAMANIO - ESPACIO_LIBRE - (TAMANIO % 2 != 0 ? 1 : 0), TAMANIO - ESPACIO_LIBRE - (TAMANIO % 2 != 0 ? 1 : 0));
                break;
        }

        return coordenadaEsquina;
    }

    private int obtenerCuadranteEsquina(Point2D esquina) {

        int xOrigen = (int) esquina.getX();
        int yOrigen = (int) esquina.getY();

        int cuadranteOrigen = 0;
        if ((xOrigen < TAMANIO / 2) && (yOrigen >= TAMANIO / 2)) {
            cuadranteOrigen = 2;
        } else if (yOrigen >= TAMANIO / 2) {
            cuadranteOrigen = 3;
        } else if (xOrigen >= TAMANIO / 2) {
            cuadranteOrigen = 1;
        }

        return cuadranteOrigen;
    }

    private List<Point2D> obtenerCoordenadasADistancia(Unidad unidad, int distancia) throws CoordenadaInvalidaException {
        List<Point2D> coordenadasAlAlcance = new ArrayList<>();
        List<Point2D> coordenadasUnidad = obtenerCoordenadas(unidad);

        Point2D coordenadaOrigen = coordenadasUnidad.get(0);
        int tamanio = obtenerTamanioMapeable(unidad);

        for (int i = -distancia; i < tamanio + distancia; i++) {
            for (int j = -distancia; j < tamanio + distancia; j++) {
                Point2D chequeo = new Point2D.Double(coordenadaOrigen.getX() + i, coordenadaOrigen.getY() + j);
                if (coordenadaEnMapa(chequeo) && !(coordenadasUnidad.contains(chequeo))) {
                    coordenadasAlAlcance.add(chequeo);
                }
            }
        }

        return coordenadasAlAlcance;
    }

    boolean estaAlAlcance(Point2D origen, Point2D destino) throws CoordenadaInvalidaException {
        validarCoordenadaEnMapa(origen);
        validarCoordenadaEnMapa(destino);

        Unidad atacante = (Unidad) obtenerDibujable(origen);
        if (atacante == null) return false;

        List<Point2D> coodenadas = obtenerCoordenadasADistancia(atacante, atacante.verAlcance());
        return coodenadas.contains(destino);
    }

    List<Dibujable> dibujablesAlAlcance(Unidad unidad) {
        List<Dibujable> unidades = new ArrayList<>();
        if(unidad == null) return unidades;

        try {
            for (Point2D coordenadaAlAlcance : obtenerCoordenadasADistancia(unidad, unidad.verAlcance())) {
                Dibujable dibujable = obtenerDibujable(coordenadaAlAlcance);
                if (dibujable != null && !unidades.contains(dibujable)) {
                    unidades.add(dibujable);
                }
            }
        } catch (CoordenadaInvalidaException ignored) {
        }

        return unidades;
    }

    private List<Castillo> encontrarCastillos() {
        List<Castillo> castillos = new ArrayList<>();

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                try {
                    Dibujable dibujable = obtenerDibujable(new Point2D.Double(i, j));
                    if (dibujable != null && dibujable.getClass() == Castillo.class && !castillos.contains(dibujable))
                        castillos.add((Castillo) dibujable);
                } catch (CoordenadaInvalidaException ignored) {
                }
            }
        }

        return castillos;
    }

    void colocarUnidadesEnExtremo(Unidad castillo, Unidad plazaCentral) {
        List<Castillo> castillos = encontrarCastillos();
        int cuadranteDestino;
        Random random = new Random();

        if (castillos.size() > 1) {
            return;
        } else if (castillos.size() == 1) {
            Point2D origenCastillo = null;

            try {
                origenCastillo = obtenerCoordenadas(castillos.get(0)).get(0);
            } catch (CoordenadaInvalidaException ignored) {
            }

            int cuadranteOrigen = obtenerCuadranteEsquina(origenCastillo);

            cuadranteDestino = (cuadranteOrigen % 2) == 0 ? (cuadranteOrigen + 3) % 4 : (cuadranteOrigen + 1) % 4;
        } else {
            cuadranteDestino = random.nextInt(4);
        }

        Point2D coordenadaCastillo = obtenerEsquinaCuadrante(cuadranteDestino);

        int xDestinoCastillo = (int) coordenadaCastillo.getX();
        int yDestinoCastillo = (int) coordenadaCastillo.getY();

        int xDestinoPlaza = cuadranteDestino % 2 == 0 ? xDestinoCastillo + ESPACIO_LIBRE : xDestinoCastillo - ESPACIO_LIBRE / 2;
        int yDestinoPlaza = cuadranteDestino - 2 < 0 ? yDestinoCastillo + ESPACIO_LIBRE : yDestinoCastillo - ESPACIO_LIBRE / 2;

        Point2D coordenadaPlaza = new Point2D.Double(xDestinoPlaza, yDestinoPlaza);

        try {
            colocarDibujable(castillo, coordenadaCastillo);
            colocarDibujable(plazaCentral, coordenadaPlaza);
        } catch (CoordenadaInvalidaException ignored) {
        }
    }

    void agregarUnidadCercana(Unidad unidad, Unidad creador) throws CoordenadaInvalidaException {
        if (creador == null)
            return;
        List<Point2D> lugaresLibres = obtenerCoordenadasADistancia(creador, DISTANCIA_PROXIMA);

        Iterator<Point2D> iter = lugaresLibres.iterator();
        while (iter.hasNext()) {
            try {
                Dibujable dibujable = obtenerDibujable(iter.next());
                if (dibujable != null)
                    iter.remove();
            } catch (CoordenadaInvalidaException ignored) {
            }
        }

        if (lugaresLibres.size() == 0) {
            throw new CoordenadaInvalidaException("No hay espacio para crear una nueva Unidad!");
        }

        Random random = new Random();
        try {
            colocarDibujable(unidad, lugaresLibres.get(random.nextInt(lugaresLibres.size())));
        } catch (CoordenadaInvalidaException ignored) {
        }
    }

    void agregarUnidadCercana(Unidad unidad, Unidad creador, Point2D coordenadaDestino) throws CoordenadaInvalidaException {
        validarCoordenada(coordenadaDestino);

        List<Point2D> coordenadas = obtenerCoordenadas(creador);
        if (coordenadas.size() == 0)
            throw new CoordenadaInvalidaException("No se encontró la Unidad Creadora en el Mapa!");

        for (Point2D coordenada : coordenadas) {
            if (Math.floor(coordenada.distance(coordenadaDestino)) <= 1) {

                if (coordenadas.size() == 1) {
                    if(coordenadaDestino.getX() == coordenadas.get(0).getX() - 1) coordenadaDestino.setLocation(coordenadaDestino.getX() - 1, coordenadaDestino.getY());
                    else if(coordenadaDestino.getY() == coordenadas.get(0).getY() - 1) coordenadaDestino.setLocation(coordenadaDestino.getX(), coordenadaDestino.getY() -1);
                }

                colocarDibujable(unidad, coordenadaDestino);
                return;
            }
        }

        throw new CoordenadaInvalidaException("La Posición de Creación debe estar al lado de la Unidad Creadora!");
    }

    public void moverUnidad(Unidad unidad, Point2D destino) throws UnidadNoMovibleException, CoordenadaInvalidaException {
        validarCoordenada(destino);
        if (unidad == null || !unidad.esMovible()) {
            throw new UnidadNoMovibleException("La Unidad que se trata de mover no es Movible!");
        }

        Point2D origen = obtenerCoordenadas(unidad).get(0);
        if (Math.floor(origen.distance(destino)) > unidad.verVelocidad()) {
            throw new CoordenadaInvalidaException("La Unidad se puede mover a lo sumo " + unidad.verVelocidad() + " casillero/s por turno!");
        }

        quitarUnidad(unidad);
        colocarDibujable(unidad, destino);
    }

    public void quitarUnidad(Unidad unidad) throws CoordenadaInvalidaException {
        List<Point2D> coordenadas = obtenerCoordenadas(unidad);

        for (Point2D coordenada : coordenadas) {
            mapa[(int) coordenada.getX()][(int) coordenada.getY()] = null;
        }
    }

    //Validaciones

    public void validarCoordenada(Point2D coordenada) throws CoordenadaInvalidaException {
        validarCoordenadaEnMapa(coordenada);

        if (estaOcupado(coordenada)) {
            throw new CoordenadaInvalidaException("Ya existe una Unidad en (" + (coordenada.getX()) + ", " + (coordenada.getY()) + ")");
        }
    }

    private void validarCoordenadaEnMapa(Point2D coordenada) throws CoordenadaInvalidaException {
        if (coordenada == null) {
            throw new CoordenadaInvalidaException("La coordenada es null.");
        }

        if (!coordenadaEnMapa(coordenada)) {
            throw new CoordenadaInvalidaException("Posición (" + coordenada.getX() + ", " + coordenada.getY() + ") fuera del Margen del Mapa!");
        }
    }

    private boolean coordenadaEnMapa(Point2D coordenada) {
        return (!(coordenada.getX() >= TAMANIO)) && (!(coordenada.getY() >= TAMANIO)) && (!(coordenada.getX() < 0)) && (!(coordenada.getY() < 0));
    }
}

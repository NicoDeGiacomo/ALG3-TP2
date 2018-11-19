package main;

import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.mapa.UnidadNoMovibleException;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.edificio.Castillo;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Mapa {
    private static final int TAMANIO = 100;
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

    Dibujable obtenerDibujable(Point2D coordenada) throws CoordenadaInvalidaException {
        validarCoordenadaEnMapa(coordenada);

        Dibujable dibujable = mapa[(int) coordenada.getX()][(int) coordenada.getY()];

        if (dibujable != null && !((Unidad) dibujable).esMapeable()) {
            quitarUnidad((Unidad) dibujable);
            return null;
        }

        return dibujable;
    }

    List<Point2D> obtenerCoordenadas(Unidad unidad) throws CoordenadaInvalidaException {
        //ToDO: Rompe en castillo
        if (unidad == null)
            throw new CoordenadaInvalidaException("La unidad es null");

        List<Point2D> coordenadas = new ArrayList<>();

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                if (mapa[i][j] == unidad) {
                    coordenadas.add(new Point2D.Double(i, j));
                }
            }
        }

        return coordenadas;
    }

    List<Point2D> obtenerCoordenadasAlAlcance(Unidad unidad) throws CoordenadaInvalidaException {
        List<Point2D> coordenadasAlAlcance = new ArrayList<>();
        List<Point2D> coordenadasUnidad = obtenerCoordenadas(unidad);

        Point2D coordenadaOrigen = obtenerCoordenadas(unidad).get(0);
        int alcance = unidad.verAlcance();
        int tamanio = obtenerTamanioMapeable(unidad);

        for (int i = -alcance; i < tamanio + alcance; i++) {
            for (int j = -alcance; j < tamanio + alcance; j++) {
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
        if (atacante == null)
            return false;

        List<Point2D> coodenadas = obtenerCoordenadasAlAlcance(atacante);
        return coodenadas.contains(destino);
    }

    List<Dibujable> dibujablesAlAlcance(Unidad unidad) throws CoordenadaInvalidaException {
        List<Dibujable> unidades = new ArrayList<>();

        for (Point2D coordenadaAlAlcance : obtenerCoordenadasAlAlcance(unidad)) {
            Dibujable dibujable = obtenerDibujable(coordenadaAlAlcance);
            if (dibujable != null && !unidades.contains(dibujable)) {
                unidades.add(dibujable);
            }
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
            int xOrigen = 0, yOrigen = 0;
            try {
                Point2D origenCastillo = obtenerCoordenadas(castillos.get(0)).get(0);
                xOrigen = (int) origenCastillo.getX();
                yOrigen = (int) origenCastillo.getY();
            } catch (CoordenadaInvalidaException ignored) {
            }

            int cuadranteOrigen;
            if ((xOrigen < TAMANIO / 2) && (yOrigen >= TAMANIO / 2)) {
                cuadranteOrigen = 2;
            } else if (yOrigen < TAMANIO / 2) {
                cuadranteOrigen = 1;
            } else {
                cuadranteOrigen = 3;
            }

            cuadranteDestino = (cuadranteOrigen % 2) == 0 ? (cuadranteOrigen + 3) % 4 : (cuadranteOrigen + 1) % 4;
        } else {
            cuadranteDestino = random.nextInt(4);
        }

        int xDestinoCastillo = random.nextInt(TAMANIO / 2) + ((TAMANIO / 2) * (cuadranteDestino % 2));
        int yDestinoCastillo = random.nextInt(TAMANIO / 2) - ((TAMANIO / 2) * ((cuadranteDestino % 2 - (cuadranteDestino > 1 ? cuadranteDestino - 1 : cuadranteDestino))));

        xDestinoCastillo = xDestinoCastillo / 2 + 8 >= TAMANIO / 2 ? xDestinoCastillo - 8 : xDestinoCastillo;
        yDestinoCastillo = yDestinoCastillo / 2 + 8 >= TAMANIO / 2 ? yDestinoCastillo - 8 : yDestinoCastillo;

        int xDestinoPlaza = cuadranteDestino % 2 == 0 ? xDestinoCastillo + 8 : xDestinoCastillo - 8;
        int yDestinoPlaza = cuadranteDestino - 2 < 0 ? yDestinoCastillo + 8 : yDestinoCastillo - 8;

        Point2D coordenadaCastillo = new Point2D.Double(xDestinoCastillo, yDestinoCastillo),
                coordenadaPlaza = new Point2D.Double(xDestinoPlaza, yDestinoPlaza);

        try {
            colocarDibujable(castillo, coordenadaCastillo);
            colocarDibujable(plazaCentral, coordenadaPlaza);
        } catch (CoordenadaInvalidaException ignored) {
        }
    }

    void agregarUnidadCercana(Unidad unidad, Unidad creador) throws CoordenadaInvalidaException {
        if (creador == null)
            return;
        List<Point2D> lugaresLibres = obtenerCoordenadasAlAlcance(creador); //TODO: Esto deberia obtener unidades en la casilla de al lado

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
            throw new CoordenadaInvalidaException("No se encontro la unidad creadora en el mapa");

        for (Point2D coordenada : coordenadas) {
            if (Math.floor(coordenada.distance(coordenadaDestino)) <= 1) {
                colocarDibujable(unidad, coordenadaDestino);
                return;
            }
        }

        throw new CoordenadaInvalidaException("La posicion de creacion debe estar al lado de la unidad creadora");
    }

    void moverUnidad(Unidad unidad, Point2D destino) throws UnidadNoMovibleException, CoordenadaInvalidaException {
        validarCoordenada(destino);
        if (unidad == null || !unidad.esMovible()) {
            throw new UnidadNoMovibleException("La Unidad que se trata de mover no es Movible!");
        }

        Point2D origen = obtenerCoordenadas(unidad).get(0);
        if (Math.floor(origen.distance(destino)) > 1) {
            throw new CoordenadaInvalidaException("Las Unidades se pueden mover a lo sumo 1 casillero por turno!");
        }

        quitarUnidad(unidad);
        colocarDibujable(unidad, destino);
    }

    private void quitarUnidad(Unidad unidad) throws CoordenadaInvalidaException {
        List<Point2D> coordenadas = obtenerCoordenadas(unidad);

        for (Point2D coordenada : coordenadas) {
            mapa[(int) coordenada.getX()][(int) coordenada.getY()] = null;
        }
    }

    private void validarCoordenada(Point2D coordenada) throws CoordenadaInvalidaException {
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

    private int obtenerTamanioMapeable(Dibujable dibujable) {
        return (dibujable.verTamanio() < 1) ? (1) : ((int) Math.sqrt(dibujable.verTamanio()));
    }

    private boolean estaOcupado(Point2D coordenada) throws CoordenadaInvalidaException {
        return obtenerDibujable(coordenada) != null;
    }


}

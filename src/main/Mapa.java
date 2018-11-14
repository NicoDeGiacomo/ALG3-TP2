package main;

import excepciones.mapa.FueraDeRangoException;
import excepciones.mapa.NoEsMovibleException;
import excepciones.mapa.PosicionOcupadaException;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.edificio.Castillo;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Mapa {
    private static final int TAMANIO = 100;
    private Dibujable[][] mapa = new Dibujable[TAMANIO][TAMANIO];

    void colocarUnidad(Unidad unidad, Point2D coordenada) throws FueraDeRangoException, PosicionOcupadaException {
        if (unidad == null) return;

        int tamanioUnidad = (unidad.verTamanio() < 2) ? (1) : ((int) Math.sqrt(unidad.verTamanio()));

        for (int i = 0; i < tamanioUnidad; i++) {
            for (int j = 0; j < tamanioUnidad; j++) {

                validarCoordenada(coordenada);

                validarCoordenada(new Point2D.Double(coordenada.getX() + i, coordenada.getY() + j));
            }
        }

        for (int i = 0; i < tamanioUnidad; i++) {
            for (int j = 0; j < tamanioUnidad; j++) {
                mapa[(int) coordenada.getX() + i][(int) coordenada.getY() + j] = unidad;
            }
        }
    }

    Dibujable obtenerDibujable(Point2D coordenada) throws FueraDeRangoException {
        validarCoordenadaEnMapa(coordenada);

        Dibujable dibujable = mapa[(int) coordenada.getX()][(int) coordenada.getY()];

        if (dibujable == null || !((Unidad) dibujable).esMapeable()) {
            quitarUnidad((Unidad) dibujable);
            return null;
        }

        return dibujable;
    }

    List<Point2D> obtenerCoordenadas(Unidad unidad) {
        List<Point2D> coordenadas = new ArrayList<>();

        if (unidad == null) return coordenadas;

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                if (mapa[i][j] == unidad) {
                    coordenadas.add(new Point2D.Double(i, j));
                }
            }
        }

        return coordenadas;
    }

    private boolean estaOcupado(Point2D coordenada) throws FueraDeRangoException {
        return obtenerDibujable(coordenada) != null;
    }

    boolean estaAlAlcance(Point2D unidad, Point2D destino) throws FueraDeRangoException {
        if (unidad == null) return false;

        Dibujable atacante = obtenerDibujable(unidad);

        validarCoordenadaEnMapa(destino);

        return atacante.verAlcance() >= Math.floor(unidad.distance(destino));
    }

    List<Dibujable> unidadesAlAlcance(Unidad unidad) {
        List<Dibujable> unidades = new ArrayList<>();

        if (unidad == null) return unidades;

        List<Point2D> coordenadasCercanas = obtenerCoordenadasCercanas(unidad);

        coordenadasCercanas.removeAll(Collections.singleton(null));

        for (Point2D coordenadasCercana : coordenadasCercanas) {
            try {
                if (!(unidades.contains(obtenerDibujable(coordenadasCercana))) && obtenerDibujable(coordenadasCercana) != null) {
                    unidades.add(obtenerDibujable(coordenadasCercana));
                }
            } catch (FueraDeRangoException ignored) {
            }
        }

        return unidades;
    }

    private List<Castillo> encontrarCastillos() {
        List<Castillo> castillos = new ArrayList<>();

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                Point2D coordenada = new Point2D.Double(i, j);
                Dibujable buffer = null;

                try {
                    buffer = obtenerDibujable(coordenada);
                } catch (FueraDeRangoException ignored) {
                }

                if ((buffer != null) && (buffer.getClass() == Castillo.class) && (!castillos.contains(buffer))) {
                    castillos.add((Castillo) buffer);
                }
            }
        }

        return castillos;
    }

    void colocarUnidadEnExtremo(Castillo castillo) {
        if (castillo == null) return;

        List<Castillo> castillos = encontrarCastillos();
        int xOrigen = 0,
                yOrigen = 0,
                xDestino = 0,
                yDestino = 0,
                cuadranteOrigen = 0,
                cuadranteDestino = 0;
        Random random = new Random();

        if (castillos.size() > 1) {
            return;
        } else if (castillos.size() == 1) {
            Point2D origenCastillo = obtenerCoordenadas(castillos.get(0)).get(0);
            xOrigen = (int) origenCastillo.getX();
            yOrigen = (int) origenCastillo.getY();

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

        xDestino = random.nextInt(TAMANIO / 2) + ((TAMANIO / 2) * (cuadranteDestino % 2));
        yDestino = random.nextInt(TAMANIO / 2) - ((TAMANIO / 2) * ((cuadranteDestino % 2 - (cuadranteDestino > 1 ? cuadranteDestino - 1 : cuadranteDestino))));

        xDestino = xDestino / 2 + 8 >= TAMANIO / 2 ? xDestino - 8 : xDestino;
        yDestino = yDestino / 2 + 8 >= TAMANIO / 2 ? yDestino - 8 : yDestino;

        Point2D coordenada = new Point2D.Double(xDestino, yDestino);

        try {
            colocarUnidad(castillo, coordenada);
        } catch (FueraDeRangoException | PosicionOcupadaException ignored) {
        }
    }

    void moverUnidad(Unidad unidad, Point2D destino) throws NoEsMovibleException, FueraDeRangoException, PosicionOcupadaException {
        if (unidad == null || !unidad.esMovible()) {
            throw new NoEsMovibleException("La Unidad que se trata de mover no es Movible!");
        }

        if (estaOcupado(destino)) {
            throw new PosicionOcupadaException("La coordenada de Destino ya se encuentra ocupada!");
        }

        Point2D origen = obtenerCoordenadas(unidad).get(0);

        if (Math.floor(origen.distance(destino)) > 1) {
            throw new FueraDeRangoException("Las Unidades se pueden mover a lo sumo 1 casillero por turno!");
        }

        quitarUnidad(unidad);
        colocarUnidad(unidad, destino);
    }

    private List<Point2D> obtenerCoordenadasCercanas(Unidad unidad) {
        List<Point2D> coordenadasAlRededor = new ArrayList<>();
        List<Point2D> coordenadasUnidad = obtenerCoordenadas(unidad);

        if (unidad == null) return coordenadasAlRededor;

        Point2D coordenadaOrigen = obtenerCoordenadas(unidad).get(0);
        int alcance = (unidad.verAlcance() < 2) ? (1) : (unidad.verAlcance());
        int tamanio = (unidad.verTamanio() < 2) ? (1) : (unidad.verTamanio() / 2);

        for (int i = -alcance; i < tamanio + alcance; i++) {
            for (int j = -alcance; j < tamanio + alcance; j++) {
                Point2D chequeo = new Point2D.Double(coordenadaOrigen.getX() + i, coordenadaOrigen.getY() + j);
                if (coordenadaEnMapa(chequeo) && !(coordenadasUnidad.contains(chequeo))) {
                    coordenadasAlRededor.add(chequeo);
                }
            }
        }

        return coordenadasAlRededor;
    }

    void agregarUnidadCercana(Unidad unidad, Unidad unidadCercana, Point2D coordenadaDestino) throws FueraDeRangoException, PosicionOcupadaException {
        validarCoordenada(coordenadaDestino);

        boolean estaCerca = false;

        List<Point2D> coordenadas = obtenerCoordenadas(unidad);

        for (int i = 0; i < coordenadas.size(); i++) {
            if(Math.floor(coordenadas.get(i).distance(coordenadaDestino)) <= 1) {
                estaCerca = true;
            }
        }

        if(estaCerca) {
            try {
                colocarUnidad(unidadCercana, coordenadaDestino);
            } catch (FueraDeRangoException | PosicionOcupadaException ignored) {
            }
        }
    }

    void quitarUnidad(Unidad unidad) {
        List<Point2D> coordenadas = obtenerCoordenadas(unidad);

        for (Point2D coordenada : coordenadas) {
            mapa[(int) coordenada.getX()][(int) coordenada.getY()] = null;
        }
    }

    private void validarCoordenada(Point2D coordenada) throws FueraDeRangoException, PosicionOcupadaException {
        validarCoordenadaEnMapa(coordenada);

        if (estaOcupado(coordenada)) {
            throw new PosicionOcupadaException("Ya existe una Unidad en (" + (coordenada.getX()) + ", " + (coordenada.getY()) + ")");
        }
    }

    private void validarCoordenadaEnMapa(Point2D coordenada) throws FueraDeRangoException {
        if(coordenada == null) {
            throw new FueraDeRangoException("No se puede validar una coordenada NULL!");
        }

        if (!coordenadaEnMapa(coordenada)) {
            throw new FueraDeRangoException("Posición (" + coordenada.getX() + ", " + coordenada.getY() + ") fuera del Margen del Mapa!");
        }
    }

    private boolean coordenadaEnMapa(Point2D coordenada) {
        return (!(coordenada.getX() >= TAMANIO)) && (!(coordenada.getY() >= TAMANIO)) && (!(coordenada.getX() < 0)) && (!(coordenada.getY() < 0));
    }
}

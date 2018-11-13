package main;

import excepciones.mapa.EspacioInsuficienteException;
import excepciones.mapa.FueraDeRangoException;
import excepciones.mapa.NoEsMovibleException;
import excepciones.mapa.PosicionOcupadaException;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.edificio.Castillo;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Mapa {
    private static final int TAMANIO = 100;
    private Dibujable[][] mapa = new Dibujable[TAMANIO][TAMANIO];

    void colocarUnidad(Unidad unidad, Point2D coordenada) throws FueraDeRangoException, PosicionOcupadaException {
        int tamanioUnidad = (unidad.verTamanio() < 2) ? (1) : (unidad.verTamanio() / 2);

        for (int i = 0; i < tamanioUnidad; i++) {
            for (int j = 0; j < tamanioUnidad; j++) {
                Point2D coordenadaMovida = new Point2D.Double(coordenada.getX() + i, coordenada.getY() + j);

                if (!coordenadaEnMapa(coordenadaMovida)) {
                    throw new FueraDeRangoException("Posición (" + coordenadaMovida.getX() + ", " + coordenadaMovida.getY() + ") fuera del Margen del Mapa!");
                }

                if (estaOcupado(coordenadaMovida)) {
                    throw new PosicionOcupadaException("Ya existe una Unidad en (" + (coordenadaMovida.getX()) + ", " + (coordenadaMovida.getY()) + ")");
                }
            }
        }

        for (int i = 0; i < tamanioUnidad; i++) {
            for (int j = 0; j < tamanioUnidad; j++) {
                mapa[(int) coordenada.getX() + i][(int) coordenada.getY() + j] = unidad;
            }
        }
    }

    Dibujable obtenerDibujable(Point2D coordenada) throws FueraDeRangoException {
        if (!coordenadaEnMapa(coordenada)) {
            throw new FueraDeRangoException("Posición (" + coordenada.getX() + ", " + coordenada.getY() + ") fuera del Margen del Mapa!");
        }

        Dibujable dibujable = mapa[(int) coordenada.getX()][(int) coordenada.getY()];

        if(dibujable == null || !((Unidad) dibujable).esMapeable()) {
            quitarUnidad((Unidad) dibujable);
            return null;
        }

        return dibujable;
    }

    List<Point2D> obtenerCoordenadas(Unidad unidad) {
        List<Point2D> coordenadas = new ArrayList<Point2D>();

        busqueda:
        for(int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                if(mapa[i][j] == unidad) {
                    coordenadas.add(new Point2D.Double(i, j));
                }
            }
        }

        return coordenadas;
    }

    private boolean coordenadaEnMapa(Point2D coordenada) {
        return (!(coordenada.getX() >= TAMANIO)) && (!(coordenada.getY() >= TAMANIO)) && (!(coordenada.getX() < 0)) && (!(coordenada.getY() < 0));
    }

    boolean estaOcupado(Point2D coordenada) throws FueraDeRangoException {
        return obtenerDibujable(coordenada) != null;
    }

    boolean estaAlAlcance(Point2D unidad, Point2D destino) throws FueraDeRangoException {
        Dibujable atacante = obtenerDibujable(unidad);
        if (!coordenadaEnMapa(destino)) {
            throw new FueraDeRangoException("Posición (" + destino.getX() + ", " + destino.getY() + ") fuera del Margen del Mapa!");
        }

        return atacante.verAlcance() >= Math.floor(unidad.distance(destino));
    }

    public List<Dibujable> unidadesAlAlcance(Unidad unidad) throws FueraDeRangoException {

        List<Point2D> coordenadasCercanas = obtenerCoordenadasCercanas(unidad);

        coordenadasCercanas.removeAll(Collections.singleton(null));

        List<Dibujable> unidades = new ArrayList<Dibujable>();

        for(int i = 0; i < coordenadasCercanas.size(); i++) {
            unidades.add(obtenerDibujable(coordenadasCercanas.get(i)));
        }

        return unidades;
    }

    void colocarUnidadEnExtremo(Castillo castillo) {
        //TODO: IMPLEMENT ME!
    }

    void moverUnidad(Unidad unidad, Point2D destino) throws NoEsMovibleException, FueraDeRangoException, PosicionOcupadaException {

        if(!unidad.esMovible()) {
            throw new NoEsMovibleException("La Unidad que se trata de mover no es Movible!");
        }

        if(estaOcupado(destino)) {
            throw new PosicionOcupadaException("La coordenada de Destino ya se encuentra ocupada!");
        }

        Point2D origen = obtenerCoordenadas(unidad).get(0);

        //TODO: Crear una excepcion nueva para este caso particular?
        if(Math.floor(origen.distance(destino)) > 1) {
            throw new FueraDeRangoException("Las Unidades se pueden mover a lo sumo 1 casillero por turno!");
        }

        quitarUnidad(unidad);
        colocarUnidad(unidad, destino);

    }

    private List<Point2D> obtenerCoordenadasCercanas(Unidad unidad) {
        Point2D coordenadaOrigen = obtenerCoordenadas(unidad).get(0);
        List<Point2D> coordenadasAlRededor = new ArrayList<Point2D>();

        int alcance = unidad.verAlcance();
        int tamanio = (unidad.verTamanio() < 2) ? (1) : (unidad.verTamanio() / 2);

        for (int i = -alcance; i < tamanio + alcance; i++) {
            for (int j = -alcance; j < tamanio + alcance; j++) {
                Point2D chequeo = new Point2D.Double(coordenadaOrigen.getX() + i, coordenadaOrigen.getY() + j);
                if(coordenadaEnMapa(chequeo)) {
                    coordenadasAlRededor.add(chequeo);
                }
            }
        }

        return coordenadasAlRededor;
    }

    void agregarUnidadCercana(Unidad unidad, Unidad unidadCercana) throws EspacioInsuficienteException {
        List<Point2D> posiblesLugares = obtenerCoordenadasCercanas(unidad);

        posiblesLugares.removeIf(lugar -> lugar != null);

        if(posiblesLugares.size() == 0) {
            throw new EspacioInsuficienteException("No hay espacio para colocar una nueva Unidad!");
        }

        Random random = new Random();
        int pos = random.nextInt(posiblesLugares.size());

        Point2D coordenadaDestino = posiblesLugares.get(pos);

        try {
            colocarUnidad(unidadCercana, coordenadaDestino);
        }
        catch (FueraDeRangoException | PosicionOcupadaException e) {}
    }

    public void quitarUnidad(Unidad unidad) {
        List<Point2D> coordenadas = obtenerCoordenadas(unidad);

        for (int i = 0; i < coordenadas.size(); i++) {
            mapa[(int) coordenadas.get(i).getX()][(int) coordenadas.get(i).getY()] = null;
        }
    }
}

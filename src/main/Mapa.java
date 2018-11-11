package main;

import exceptions.FueraDeRangoException;
import exceptions.PosicionOcupadaException;
import unidades.Dibujable;
import unidades.Unidad;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Mapa {
    private static final int TAMANIO = 100;
    Dibujable[][] mapa = new Dibujable[TAMANIO][TAMANIO];

    public void colocarUnidad(Unidad unidad, Point2D coordenada) throws FueraDeRangoException, PosicionOcupadaException {
        int tamanioUnidad = (unidad.verTamanio() < 2) ? (1) : (unidad.verTamanio() / 2);

        for (int i = 0; i < tamanioUnidad; i++) {
            for (int j = 0; j < tamanioUnidad; j++) {
                Point2D coordenadaMovida = new Point2D.Double(coordenada.getX() + i, coordenada.getY() + j);

                if(!coordenadaEnMapa(coordenadaMovida)){
                    throw new FueraDeRangoException("Posición (" + coordenadaMovida.getX() + ", " + coordenadaMovida.getY() + ") fuera del Margen del Mapa!");
                }

                if(estaOcupado(coordenadaMovida)){
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

    public Dibujable obtenerDibujable(Point2D coordenada) throws FueraDeRangoException {
        if(!coordenadaEnMapa(coordenada)){
            throw new FueraDeRangoException("Posición (" + coordenada.getX() + ", " + coordenada.getY() +") fuera del Margen del Mapa!");
        }

        return mapa[(int) coordenada.getX()][(int) coordenada.getY()];
    }

    private boolean coordenadaEnMapa (Point2D coordenada) {
        if((coordenada.getX() >=  TAMANIO) || (coordenada.getY() >= TAMANIO) || (coordenada.getX() < 0) || (coordenada.getY() < 0)) {
            return false;
        }

        return true;
    }

    public boolean estaOcupado(Point2D coordenada) throws FueraDeRangoException {
        return obtenerDibujable(coordenada) != null;
    }

    public boolean estaAlAlcance(Point2D unidad, Point2D destino) throws FueraDeRangoException {
        Dibujable atacante = obtenerDibujable(unidad);
        if(!coordenadaEnMapa(destino)) {
            throw new FueraDeRangoException("Posición (" + destino.getX() + ", " + destino.getY() +") fuera del Margen del Mapa!");
        }

        return atacante.verAlcance() >= Math.floor(unidad.distance(destino));
    }

    public List<Dibujable> unidadesAlRededor(Point2D coordenada) throws FueraDeRangoException {
        Dibujable unidad = obtenerDibujable(coordenada);
        int alcance = unidad.verAlcance();
        int tamanio = (unidad.verTamanio() < 2) ? (1) : (unidad.verTamanio() / 2);
        List<Dibujable> unidades = new ArrayList<Dibujable>();

        //TODO: Obtener la 'primera' coordenada de la unidad.
        for (int i = -alcance; i < tamanio + alcance; i++) {
            for (int j = -alcance; j < tamanio + alcance; j++) {
                Point2D coordenadaMovida = new Point2D.Double(coordenada.getX() + i, coordenada.getY() + j);

                if(!coordenadaEnMapa(coordenadaMovida)) continue;

                Dibujable encontrado = obtenerDibujable(coordenadaMovida);
                if((encontrado == unidad) || (encontrado == null)) continue;

                unidades.add(encontrado);
            }
        }

        return unidades;
    }

}

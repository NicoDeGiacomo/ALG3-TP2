package main;

import exceptions.FueraDeRangoException;
import exceptions.PosicionOcupadaException;
import unidades.Dibujable;
import unidades.Unidad;

import java.awt.geom.Point2D;

public class Mapa {
    private static final int TAMANIO = 100;
    Dibujable[][] mapa = new Dibujable[TAMANIO][TAMANIO];

    //TODO: FUERTE. Revisar el tema de las excepciones.

    public void colocarUnidad(Unidad unidad, Point2D coordenada) throws FueraDeRangoException, PosicionOcupadaException {
        int tamanio = (unidad.verTamanio() < 2) ? (1) : (unidad.verTamanio() / 2);

        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                double nuevaX = coordenada.getX() + i;
                double nuevaY = coordenada.getY() + j;

                if((nuevaX >=  TAMANIO) || (nuevaY >= TAMANIO)) {
                    throw new FueraDeRangoException("Posición (" + (nuevaX) + ", " + (nuevaY) +") fuera del Margen del Mapa!");
                }

                if(estaOcupado(new Point2D.Double(nuevaX, nuevaY))){
                    throw new PosicionOcupadaException("Ya existe una Unidad en (" + (nuevaX) + ", " + (nuevaY) +")");
                }
            }
        }

        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                mapa[(int) coordenada.getX() + i][(int) coordenada.getY() + j] = unidad;
            }
        }
    }

    public Dibujable obtenerDibujable(Point2D coordenada) throws FueraDeRangoException {
        double x = coordenada.getX();
        double y = coordenada.getY();

        if((x >=  TAMANIO) || (y >= TAMANIO)) {
            throw new FueraDeRangoException("Posición (" + (x) + ", " + (y) +") fuera del Margen del Mapa!");
        }
        return mapa[(int) x][(int) y];
    }

    public boolean estaOcupado(Point2D coordenada) throws FueraDeRangoException {
        return obtenerDibujable(coordenada) != null;
    }

    public boolean estaAlAlcance(Point2D unidad, Point2D destino) throws FueraDeRangoException {
        Dibujable atacante = obtenerDibujable(unidad);
        obtenerDibujable(destino);

        return atacante.verAlcance() >= unidad.distance(destino);
    }

}

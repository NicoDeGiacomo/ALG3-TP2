package main;

import exceptions.FueraDeRangoException;
import exceptions.PosicionOcupadaException;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.milicia.Arquero;

import java.awt.geom.Point2D;

public class Mapa {
    private static final int TAMANIO = 100;
    Dibujable[][] mapa = new Dibujable[TAMANIO][TAMANIO];

    public void colocarUnidad(Unidad unidad, Point2D coordenada) throws FueraDeRangoException, PosicionOcupadaException {
        int tamanio = (unidad.verTamanio() < 2) ? (1) : (unidad.verTamanio() / 2);

        //TODO: Hay mejor manera que dos for anidados?
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

    public Dibujable[][] verMapa (){
        return mapa;
    }


    public Dibujable obtenerDibujable(Point2D coordenada) {
        return mapa[(int) coordenada.getX()][(int) coordenada.getY()];
    }

    public boolean estaOcupado(Point2D coordenada){
        return obtenerDibujable(coordenada) != null;
    }

    public boolean estaAlAlcance(Point2D unidad, Point2D destino){
        Dibujable atacante = obtenerDibujable(unidad);

        return atacante.verAlcance() >= unidad.distance(destino);
    }

}

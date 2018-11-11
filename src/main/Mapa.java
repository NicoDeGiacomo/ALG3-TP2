package main;

import unidades.Dibujable;
import unidades.Unidad;
import exceptions.PosicionOcupadaException;

import java.awt.geom.Point2D;

public class Mapa {

    Dibujable[][] mapa = new Dibujable[100][100];

    public void colocarUnidad(Unidad unidad, Point2D coordenada) {
        int tamanio = unidad.verTamanio() / 2;

        //TODO: Hay mejor manera que dos for anidados?
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                if(estaOcupado(new Point2D.Double(coordenada.getX() + i, coordenada.getY() + j))){
                    throw new PosicionOcupadaException("Ya existe una Unidad en esa Posición!");
                    //TODO: Mandar la posición que está ocupada.
                }
            }
        }

        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                mapa[(int) coordenada.getX() + i][(int) coordenada.getY() + j] = unidad;
            }
        }
    }

    public Dibujable obtenerDibujable(Point2D coordenada) {
        return mapa[(int) coordenada.getX()][(int) coordenada.getY()];
    }

    public boolean estaOcupado(Point2D coordenada){
        return obtenerDibujable(coordenada) instanceof Unidad;
    }

    public boolean estaAlAlcance(Point2D unidad, Point2D destino){
        Dibujable atacante = obtenerDibujable(unidad);

        return atacante.verAlcance() >= unidad.distance(destino);
    }

}

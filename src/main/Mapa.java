package main;

import unidades.Dibujable;
import unidades.Unidad;
import exceptions.PosicionOcupadaException;

import java.awt.geom.Point2D;

public class Mapa {

    Dibujable[][] mapa = new Dibujable[100][100];

    public void colocarUnidad(Unidad unidad, Point2D coordenada) throws PosicionOcupadaException {
        if(estaOcupado(coordenada)){
            throw new PosicionOcupadaException("Ya existe una Unidad en esa Posición!");
        }

        mapa[(int) coordenada.getX()][(int) coordenada.getY()] = unidad;
    }

    public Dibujable obtenerDibujable(Point2D coordenada) {
        return mapa[(int) coordenada.getX()][(int) coordenada.getY()];
    }

    private boolean estaOcupado(Point2D coordenada){
        return obtenerDibujable(coordenada) instanceof Unidad;
    }

    private boolean estaAlAlcance(Point2D unidad, Point2D destino){
        Dibujable atacante = obtenerDibujable(unidad);

        return atacante.verAlcance() >= unidad.distance(destino);
    }

}

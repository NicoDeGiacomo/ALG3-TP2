package unidades;

import javafx.scene.paint.Color;

import java.awt.geom.Point2D;

public interface Dibujable {

    int verTamanio();

    Color obtenerColor();

    boolean mostrarMenu(Point2D point2D);
}

package unidades;

import javafx.scene.image.Image;

import java.awt.geom.Point2D;

public interface Dibujable {

    int verTamanio();

    Image obtenerImagen();

    boolean mostrarMenu(Point2D point2D);

}

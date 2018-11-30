package unidades;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.awt.geom.Point2D;

public interface Dibujable {

    int verTamanio();

    ImageView obtenerImagen();

    boolean mostrarMenu(Point2D point2D);

}

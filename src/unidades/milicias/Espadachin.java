package unidades.milicias;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import main.Jugador;
import ui.Menu;

import java.awt.geom.Point2D;
import java.io.File;

public class Espadachin extends Milicia {

    public Espadachin(Jugador propietario) {
        super(propietario);
        this.vida = 100;
        this.danio = 25;
        this.danioAEdificios = 15;
        this.precio = 50;
    }

    @Override
    public void ejecutarTareas() {

    }

    @Override
    public Image obtenerImagen() {
        return new Image("/assets/graphics/espadachin.png", true);
    }

    @Override
    public boolean mostrarMenu(Point2D point2D) {
        return Menu.mostrarMenuDeEspadachin(this, point2D);
    }

    @Override
    public Media obtenerSonidoDeAtaque() {
        return new Media(new File("src/assets/sounds/unidades/milicias/espadachines/ataque.wav").toURI().toString());
    }

    @Override
    public Media obtenerSonidoDeMovimiento() {
        return new Media(new File("src/assets/sounds/unidades/milicias/espadachines/movimiento.wav").toURI().toString());
    }

    @Override
    public Media obtenerSonidoDeCreacion() {
        return new Media(new File("src/assets/sounds/unidades/milicias/espadachines/creacion.wav").toURI().toString());
    }
}

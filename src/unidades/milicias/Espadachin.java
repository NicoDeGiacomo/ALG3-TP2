package unidades.milicias;

import javafx.scene.media.Media;
import javafx.scene.paint.Color;
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
    public Color obtenerColor() {
        return Color.BLUE;
    }

    @Override
    public boolean mostrarMenu(Point2D point2D) {
        return Menu.mostrarMenuDeEspadachin(this, point2D);
    }

    @Override
    public Media obtenerSonidoDeAtaque() {
        return new Media(new File("src/assets/sounds/unidades/milicias/ataqueEspadachin.wav").toURI().toString());
    }

    @Override
    public Media obtenerSonidoDeMovimiento() {
        return new Media(new File("src/assets/sounds/unidades/milicias/movimientoEspadachin.wav").toURI().toString());
    }

    @Override
    public Media obtenerSonidoDeCreacion() {
        return new Media(new File("src/assets/sounds/unidades/milicias/creacionEspadachin.wav").toURI().toString());
    }
}

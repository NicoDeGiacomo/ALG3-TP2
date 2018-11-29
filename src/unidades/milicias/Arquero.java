package unidades.milicias;


import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import main.Jugador;
import ui.Menu;

import java.awt.geom.Point2D;
import java.io.File;

public class Arquero extends Milicia {

    public Arquero(Jugador propietario) {
        super(propietario);
        this.vida = 75;
        this.danio = 15;
        this.danioAEdificios = 10;
        this.precio = 75;
        this.alcance = 3;
    }

    @Override
    public void ejecutarTareas() {

    }

    @Override
    public Color obtenerColor() {
        return Color.RED;
    }

    @Override
    public ImageView obtenerImagen() {
        return null;
    }

    @Override
    public boolean mostrarMenu(Point2D point2D) {
        return Menu.mostrarMenuDeArquero(this, point2D);
    }


    @Override
    public Media obtenerSonidoDeAtaque() {
        return new Media(new File("src/assets/sounds/unidades/milicias/arqueros/ataque.wav").toURI().toString());
    }

    @Override
    public Media obtenerSonidoDeMovimiento() {
        return new Media(new File("src/assets/sounds/unidades/milicias/arqueros/movimiento.wav").toURI().toString());
    }

    @Override
    public Media obtenerSonidoDeCreacion() {
        return new Media(new File("src/assets/sounds/unidades/milicias/arqueros/creacion.wav").toURI().toString());
    }

    @Override
    public Media obtenerSonidoDeMuerte() {
        return new Media(new File("src/assets/sounds/unidades/milicias/arqueros/muerte.wav").toURI().toString());
    }
}


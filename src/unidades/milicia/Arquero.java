package unidades.milicia;


import javafx.scene.paint.Color;
import main.Jugador;
import ui.Menu;

import java.awt.geom.Point2D;

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
    public boolean mostrarMenu(Point2D point2D) {
        return Menu.mostrarMenuDeArquero(this, point2D);
    }


}


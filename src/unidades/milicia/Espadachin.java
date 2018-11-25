package unidades.milicia;

import javafx.scene.paint.Color;
import main.Jugador;

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

}

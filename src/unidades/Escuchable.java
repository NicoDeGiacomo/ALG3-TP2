package unidades;

import javafx.scene.media.Media;

public interface Escuchable {
    Media obtenerSonidoDeAtaque();

    Media obtenerSonidoDeMovimiento();

    Media obtenerSonidoDeCreacion();

    //Media obtenerSonidoDeMuerte(); TODO: Implementar!
}

package ui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sonido {

    static private MediaPlayer mediaPlayer;

    static void reproducirSonidoDeError() {
        Media sonido = new Media(new File("src/assets/sounds/main/error.wav").toURI().toString());
        reproducirSonido(sonido);
    }

    static void reproducirSonidoDeMenu() {
        Media sonido = new Media(new File("src/assets/sounds/main/menu3.wav").toURI().toString());
        reproducirSonido(sonido);
    }

    static void reproducirSonidoDeFondo() {
        Media sonido = new Media(new File("src/assets/sounds/main/menu2.wav").toURI().toString());
        reproducirSonido(sonido);
    }

    static void reproducirSonidoDeBoton() {
        Media sonido = new Media(new File("src/assets/sounds/main/clickboton.wav").toURI().toString());
        reproducirSonido(sonido);
    }

    static void reproducirSonidoDeVictoria() {
        detenerSonidos();
        Media sonido = new Media(new File("src/assets/sounds/main/victoria.wav").toURI().toString());
        reproducirSonido(sonido);
    }

    public static void reproducirSonido(Media sonido) {
        if (sonido == null)
            return;
        mediaPlayer = new MediaPlayer(sonido);
        mediaPlayer.play();
        //TODO: Que haga loop!
    }

    static void detenerSonidos() {
        mediaPlayer.stop();
    }
}

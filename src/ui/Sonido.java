package ui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

class Sonido {

    static private MediaPlayer mediaPlayer;

    static void reproducirSonidoDeError() {
        Media sonido = new Media(new File("src/assets/sounds/main/error.wav").toURI().toString());
        reproducirSonido(sonido);
    }

    static void reproducirSonidoDeMenu() {
        Media sonido = new Media(new File("src/assets/sounds/main/menu1.wav").toURI().toString());
        reproducirSonido(sonido);
    }

    static void reproducirSonidoDeBoton() {
        Media sonido = new Media(new File("src/assets/sounds/main/clickboton.wav").toURI().toString());
        reproducirSonido(sonido);
    }

    static void reproducirSonido(Media sonido) {
        mediaPlayer = new MediaPlayer(sonido);
        mediaPlayer.play();
    }

    static void detenerSonidos() {
        mediaPlayer.stop();
    }
}

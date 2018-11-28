package ui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

class Sonido {
    static void reproducirSonidoDeError() {
        Media sound = new Media(new File("src/assets/sounds/main/error.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    static void reproducirSonido(Media sonido) {
        MediaPlayer mediaPlayer = new MediaPlayer(sonido);
        mediaPlayer.play();
    }
}

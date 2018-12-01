package ui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sonido {

    static private MediaPlayer mediaPlayer;

    public Sonido() {
    }

    public void reproducirSonidoDeError() {
        Media sonido = new Media(new File("src/assets/sounds/main/error.wav").toURI().toString());
        reproducirSonido(sonido);
    }

    public void reproducirSonidoDeMenu() {
        Media sonido = new Media(new File("src/assets/sounds/main/menu3.wav").toURI().toString());
        reproducirSonido(sonido);
    }

    public void reproducirSonidoDeFondo() {
        Media sonido = new Media(new File("src/assets/sounds/main/menu2.wav").toURI().toString());
        reproducirSonido(sonido);
    }

    public void reproducirSonidoDeBoton() {
        Media sonido = new Media(new File("src/assets/sounds/main/clickboton.wav").toURI().toString());
        reproducirSonido(sonido);
    }

    public void reproducirSonidoDeVictoria() {
        detenerSonidos();
        Media sonido = new Media(new File("src/assets/sounds/main/victoria.wav").toURI().toString());
        reproducirSonido(sonido);
    }

    public void reproducirSonido(Media sonido) {
        mediaPlayer = new MediaPlayer(sonido);
        mediaPlayer.play();
        //TODO: Que haga loop!
    }

    static void detenerSonidos() {
        mediaPlayer.stop();
    }
}

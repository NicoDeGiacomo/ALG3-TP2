package unidades.milicias;

import excepciones.unidades.ArmaDeAsedioYaDesmontadaException;
import excepciones.unidades.ArmaDeAsedioYaMontadaException;
import excepciones.unidades.AtaqueIncorrectoException;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import main.Jugador;
import ui.Menu;
import unidades.Unidad;
import unidades.estados.armaDeAsedio.EstadoDeArmaDeAsedio;
import unidades.estados.armaDeAsedio.ArmaDesmontada;

import java.awt.geom.Point2D;
import java.io.File;

public class ArmaDeAsedio extends Milicia {

    private EstadoDeArmaDeAsedio estadoDeArma;

    public ArmaDeAsedio(Jugador propietario) {
        super(propietario);
        this.estadoDeArma = new ArmaDesmontada();
        this.vida = 150;
        this.danioAEdificios = 75;
        this.precio = 200;
        this.alcance = 5;
    }

    @Override
    public boolean esMovible() {
        return estadoDeArma.esMovible();
    }

    public void montarArma() throws ArmaDeAsedioYaMontadaException {
        this.estadoDeArma = this.estadoDeArma.montarArma();
    }

    public void desmontarArma() throws ArmaDeAsedioYaDesmontadaException {
        this.estadoDeArma = this.estadoDeArma.desmontarArma();
    }

    @Override
    public void provocarDanio(Unidad unidad) throws AtaqueIncorrectoException {
        if (unidad.esMovible())
            throw new AtaqueIncorrectoException("El arma de asedio solo puede atacar edificios");
        this.estadoDeArma.provocarDanio(unidad, this.danioAEdificios);
    }

    @Override
    public void ejecutarTareas() {
    }

    @Override
    public Color obtenerColor() {
        return Color.WHEAT;
    }

    @Override
    public boolean mostrarMenu(Point2D point2D) {
        return Menu.mostrarMenuDeArmaDeAsedio(this, point2D);
    }

    @Override
    public Media obtenerSonidoDeAtaque() {
        return new Media(new File("src/assets/sounds/unidades/milicias/armasDeAsedio/ataque.wav").toURI().toString());
    }

    @Override
    public Media obtenerSonidoDeMovimiento() {
        return new Media(new File("src/assets/sounds/unidades/milicias/armasDeAsedio/movimiento.wav").toURI().toString());
    }

    @Override
    public Media obtenerSonidoDeCreacion() {
        return new Media(new File("src/assets/sounds/unidades/milicias/armasDeAsedio/creacion.wav").toURI().toString());
    }

    @Override
    public Media obtenerSonidoDeMuerte() {
        return new Media(new File("src/assets/sounds/unidades/milicias/armasDeAsedio/muerte.wav").toURI().toString());
    }
}















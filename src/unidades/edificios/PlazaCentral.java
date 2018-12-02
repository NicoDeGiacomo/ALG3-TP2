package unidades.edificios;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.ErrorDeConstruccionException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import main.Jugador;
import ui.Menu;
import unidades.milicias.Aldeano;
import unidades.milicias.Milicia;

import java.awt.geom.Point2D;
import java.io.File;

public class PlazaCentral extends Edificio {

    public PlazaCentral(Jugador propietario) {
        super(propietario);
        this.vida = 450;
        this.tamanio = 4;
        this.precio = 100;
    }

    @Override
    public boolean arreglar() {
        this.vida += 25;
        if (this.vida >= 450) {
            this.vida = 450;
            return true;
        }
        return false;
    }

    @Override
    public Milicia crearUnidad() throws ErrorDeConstruccionException {
        if (!this.estadoDeUnidad.estaHabilitado())
            throw new ErrorDeConstruccionException("El edificios está en construcción");

        Aldeano aldeano = new Aldeano(this.propietario);
        try {
            this.propietario.agregarUnidad(aldeano, this);
        } catch (OroInsuficienteException | LimiteDePoblacionException | CoordenadaInvalidaException e) {
            throw new ErrorDeConstruccionException(e.getMessage());
        }
        return aldeano;
    }

    @Override
    public void ejecutarTareas() {
    }

    @Override
    public Image obtenerImagen() {
        return new Image("/assets/graphics/village.png", true);
    }

    @Override
    public boolean mostrarMenu(Point2D point2D) {
        return Menu.mostrarMenuDePlazaCentral(this);
    }

    @Override
    public Media obtenerSonidoDeAtaque() {
        return null;
    }

    @Override
    public Media obtenerSonidoDeMovimiento() {
        return null;
    }

    @Override
    public Media obtenerSonidoDeCreacion() {
        return new Media(new File("src/assets/sounds/unidades/plazasCentrales/creacion.wav").toURI().toString());
    }
}

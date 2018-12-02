package unidades.edificios;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.ErrorDeConstruccionException;
import excepciones.unidades.UnidadNoEspecificadaException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import main.Jugador;
import ui.Menu;
import unidades.milicias.Arquero;
import unidades.milicias.Espadachin;
import unidades.milicias.Milicia;

import java.awt.geom.Point2D;
import java.io.File;

public class Cuartel extends Edificio {

    public Cuartel(Jugador propietario) {
        super(propietario);
        this.vida = 250;
        this.tamanio = 4;
        this.precio = 50;
    }

    @Override
    public boolean arreglar() {
        this.vida += 50;
        if (this.vida >= 250) {
            this.vida = 250;
            return true;
        }
        return false;
    }

    @Override
    public Milicia crearUnidad() throws UnidadNoEspecificadaException {
        throw new UnidadNoEspecificadaException("El Cuartel puede crear más de una Unidad. Utilizar metodos: crearEspadachin / crearArquero");
    }

    public Milicia crearEspadachin() throws ErrorDeConstruccionException {
        if (!this.estadoDeUnidad.estaHabilitado())
            throw new ErrorDeConstruccionException("El Edificio está en Construcción!");

        Espadachin espadachin = new Espadachin(this.propietario);
        try {
            this.propietario.agregarUnidad(espadachin, this);
        } catch (OroInsuficienteException | LimiteDePoblacionException | CoordenadaInvalidaException e) {
            throw new ErrorDeConstruccionException(e.getMessage());
        }

        return espadachin;
    }

    public Milicia crearArquero() throws ErrorDeConstruccionException {
        if (!this.estadoDeUnidad.estaHabilitado())
            throw new ErrorDeConstruccionException("El Edificio está en Construcción!");

        Arquero arquero = new Arquero(this.propietario);
        try {
            this.propietario.agregarUnidad(arquero, this);
        } catch (OroInsuficienteException | LimiteDePoblacionException | CoordenadaInvalidaException e) {
            throw new ErrorDeConstruccionException(e.getMessage());
        }

        return arquero;
    }

    @Override
    public void ejecutarTareas() {
    }

    @Override
    public Image obtenerImagen() {
        return new Image("/assets/graphics/barracks.png", true);
    }

    @Override
    public boolean mostrarMenu(Point2D point2D) {
        return Menu.mostrarMenuDeCuartel(this);
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
        return new Media(new File("src/assets/sounds/unidades/cuarteles/creacion.wav").toURI().toString());
    }
}

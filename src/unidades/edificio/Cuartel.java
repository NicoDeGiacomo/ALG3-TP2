package unidades.edificio;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.ErrorDeConstruccionException;
import excepciones.unidades.UnidadNoEspecificadaException;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import main.Jugador;
import ui.Menu;
import unidades.milicia.Arquero;
import unidades.milicia.Espadachin;
import unidades.milicia.Milicia;

import java.awt.geom.Point2D;

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
        throw new UnidadNoEspecificadaException("El cuartel puede crear mas de una unidad. Utilizar metodos: crearEspadachin / crearArquero");
    }

    public Milicia crearEspadachin() throws ErrorDeConstruccionException {
        if (!this.estadoDeUnidad.estaHabilitado())
            throw new ErrorDeConstruccionException("El edificio está en construcción");

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
            throw new ErrorDeConstruccionException("El edificio está en construcción");

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
    public Color obtenerColor() {
        return Color.BROWN;
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
        return null;
    }
}

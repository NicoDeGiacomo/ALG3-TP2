package unidades.edificio;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.UnidadNoEspecificadaException;
import javafx.scene.paint.Color;
import main.Jugador;
import ui.Menu;
import unidades.milicia.Arquero;
import unidades.milicia.Espadachin;

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
    public void crearUnidad() throws UnidadNoEspecificadaException {
        throw new UnidadNoEspecificadaException("El cuartel puede crear mas de una unidad. Utilizar metodos: crearEspadachin / crearArquero");
    }

    public void crearEspadachin() throws OroInsuficienteException, LimiteDePoblacionException, CoordenadaInvalidaException {
        Espadachin espadachin = new Espadachin(this.propietario);
        this.propietario.agregarUnidad(espadachin, this);
    }

    public void crearArquero() throws OroInsuficienteException, LimiteDePoblacionException, CoordenadaInvalidaException {
        Arquero arquero = new Arquero(this.propietario);
        this.propietario.agregarUnidad(arquero, this);
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
}

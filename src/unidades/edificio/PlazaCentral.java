package unidades.edificio;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.ErrorDeConstruccionException;
import javafx.scene.paint.Color;
import main.Jugador;
import ui.Menu;
import unidades.milicia.Aldeano;

import java.awt.geom.Point2D;

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
    public void crearUnidad() throws ErrorDeConstruccionException {
        if (!this.estadoDeUnidad.estaHabilitado())
            throw new ErrorDeConstruccionException("El edificio está en construcción");

        try {
            this.propietario.agregarUnidad(new Aldeano(this.propietario), this);
        } catch (OroInsuficienteException | LimiteDePoblacionException | CoordenadaInvalidaException e) {
            throw new ErrorDeConstruccionException(e.getMessage());
        }
    }

    @Override
    public void ejecutarTareas() {
    }

    @Override
    public Color obtenerColor() {
        return Color.YELLOW;
    }

    @Override
    public boolean mostrarMenu(Point2D point2D) {
        return Menu.mostrarMenuDePlazaCentral(this);
    }
}

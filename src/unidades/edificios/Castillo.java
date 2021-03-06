package unidades.edificios;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.CreacionDeCastilloException;
import excepciones.unidades.ErrorDeConstruccionException;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import main.Jugador;
import main.Mapa;
import ui.Menu;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.estados.unidades.Vivo;
import unidades.milicias.Aldeano;
import unidades.milicias.ArmaDeAsedio;
import unidades.milicias.Milicia;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.List;


public class Castillo extends Edificio {
    private int danio;

    public Castillo(Jugador propietario) {
        super(propietario);
        this.vida = 1000;
        this.danio = 20;
        this.tamanio = 16;
        this.alcance = 3;
        this.estadoDeUnidad = new Vivo();
    }

    @Override
    public void provocarDanio(Unidad unidad) {
        if (unidad.unidadesSonDelMismoEquipo(this.propietario))
            return;
        unidad.recibirDanio(this.danio);
    }

    @Override
    public void ejecutarTareas() {
        List<Dibujable> unidades = Mapa.obtenerInstancia().dibujablesAlAlcance(this);
        for (Dibujable unidad : unidades)
            this.provocarDanio((Unidad) unidad);
    }

    @Override
    public boolean arreglar() {
        this.vida += 15;
        if (this.vida >= 1000) {
            this.vida = 1000;
            return true;
        }
        return false;
    }

    @Override
    public Milicia crearUnidad() throws ErrorDeConstruccionException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(this.propietario);
        try {
            this.propietario.agregarUnidad(armaDeAsedio, this);
        } catch (OroInsuficienteException | LimiteDePoblacionException | CoordenadaInvalidaException e) {
            throw new ErrorDeConstruccionException(e.getMessage());
        }
        return armaDeAsedio;
    }

    @Override
    public void comenzarConstruccion(Aldeano aldeano, Point2D pos) throws CreacionDeCastilloException {
        throw new CreacionDeCastilloException("No se puede construir un Castillo.");
    }

    @Override
    public void terminarConstruccion() {
    }

    @Override
    public Image obtenerImagen() {
        return new Image("/assets/graphics/castle.png", true);
    }

    @Override
    public boolean mostrarMenu(Point2D point2D) {
        return Menu.mostrarMenuDeCastillo(this);
    }

    @Override
    public Media obtenerSonidoDeAtaque() {
        return new Media(new File("src/assets/sounds/unidades/edificios/ataque.wav").toURI().toString());
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

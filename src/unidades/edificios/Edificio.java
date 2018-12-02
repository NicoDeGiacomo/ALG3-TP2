package unidades.edificios;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.AtaqueIncorrectoException;
import excepciones.unidades.CreacionDeCastilloException;
import excepciones.unidades.ErrorDeConstruccionException;
import excepciones.unidades.UnidadNoEspecificadaException;
import javafx.scene.media.Media;
import main.Jugador;
import unidades.Unidad;
import unidades.estados.unidades.EnConstruccion;
import unidades.estados.unidades.Vivo;
import unidades.milicias.Aldeano;
import unidades.milicias.Milicia;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.Random;

public abstract class Edificio extends Unidad {

    int tamanio;
    int alcance = 1;
    private final int CANTIDAD_SONIDOS_MUERTE = 4;

    public Edificio(Jugador propietario) {
        super(propietario);
        this.estadoDeUnidad = new EnConstruccion();
    }

    @Override
    public boolean esMovible() {
        return false;
    }

    @Override
    public int verAlcance() {
        return this.alcance;
    }

    @Override
    public void provocarDanio(Unidad unidad) throws AtaqueIncorrectoException {
        throw new AtaqueIncorrectoException("Los edificios no pueden atacar.");
    }

    @Override
    public int verTamanio() {
        return this.tamanio;
    }

    public void comenzarConstruccion(Aldeano aldeano, Point2D pos) throws OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        this.propietario.agregarUnidad(this, aldeano, pos);
        this.estadoDeUnidad = new EnConstruccion();
    }

    public void terminarConstruccion() {
        this.estadoDeUnidad = new Vivo();
    }

    public void reportarMuerte() {
        this.propietario.removerUnidad(this);
    }

    public abstract Milicia crearUnidad() throws OroInsuficienteException, UnidadNoEspecificadaException, LimiteDePoblacionException, CoordenadaInvalidaException, ErrorDeConstruccionException;

    public abstract boolean arreglar();

    @Override
    public Media obtenerSonidoDeMuerte() {
        return new Media(new File("src/assets/sounds/unidades/edificios/muerte/muerte" + (new Random().nextInt(CANTIDAD_SONIDOS_MUERTE) + 1) + ".wav").toURI().toString());
    }

}

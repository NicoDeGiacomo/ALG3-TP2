package unidades;

import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.mapa.UnidadNoMovibleException;
import excepciones.unidades.AtaqueIncorrectoException;
import main.Jugador;
import ui.Sonido;
import unidades.estados.unidades.EstadoDeUnidad;
import unidades.estados.unidades.Muerto;
import unidades.estados.unidades.Vivo;

import java.awt.geom.Point2D;

public abstract class Unidad implements Dibujable, Escuchable {

    protected EstadoDeUnidad estadoDeUnidad;

    protected Jugador propietario;

    protected int vida;

    protected int precio;

    public Unidad(Jugador propietario) {
        this.estadoDeUnidad = new Vivo();
        this.propietario = propietario;
    }

    public void recibirDanio(int danio) {
        this.vida -= danio;
        if (this.vida <= 0) {
            this.estadoDeUnidad = new Muerto();
            reportarMuerte();
        }
    }

    public boolean estaHabilitado() {
        return estadoDeUnidad.estaHabilitado();
    }

    public EstadoDeUnidad verEstadoDeUnidad() {
        return this.estadoDeUnidad;
    }

    public int verVida() {
        return this.vida;
    }

    public int verPrecio() {
        return this.precio;
    }

    public boolean esMapeable() {
        return this.estadoDeUnidad.esMapleable();
    }

    public void moverUnidad(Point2D coordenada) throws UnidadNoMovibleException, CoordenadaInvalidaException {
        this.propietario.moverUnidad(this, coordenada);
    }

    public void atacarUnidad(Point2D coordenada) throws AtaqueIncorrectoException, CoordenadaInvalidaException {
        this.propietario.atacarConUnidadACoordenada(this, coordenada);
    }

    public boolean unidadesSonDelMismoEquipo(Jugador propietario) {
        return propietario == this.propietario;
    }

    public Jugador obtenerPropietario() {
        return this.propietario;
    }

    public abstract int verTamanio();

    public abstract int verAlcance();

    public int verVelocidad() {
        return 1;
    }

    public abstract boolean esMovible();

    public abstract void provocarDanio(Unidad unidad) throws AtaqueIncorrectoException;

    public abstract void ejecutarTareas();

    protected abstract void reportarMuerte();

}

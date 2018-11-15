package unidades;

import excepciones.main.OroInsuficienteException;
import excepciones.unidades.AtaqueIncorrectoException;
import excepciones.unidades.UnidadNoAgregadaException;
import main.Jugador;
import unidades.estados.unidades.EstadoDeUnidad;
import unidades.estados.unidades.Muerto;
import unidades.estados.unidades.Vivo;

public abstract class Unidad implements Dibujable {

    protected EstadoDeUnidad estadoDeUnidad;

    protected Jugador propietario;

    protected int vida;

    protected int precio;

    public Unidad() {
        this.estadoDeUnidad = new Vivo(); //TODO Los edificios tienen vida ? wtf
    }

    public void recibirDanio(int danio) {
        this.vida -= danio;
        if (this.vida <= 0)
            this.estadoDeUnidad = new Muerto();
    }

    public void cobrarCostoDeCreacion() throws OroInsuficienteException {
        this.propietario.cobrarOro(this);
    }

    public void devolverCostoDeCreacion() throws UnidadNoAgregadaException {
        this.propietario.devolverCostoDeUnidad(this);
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

    //ToDo: Las unidades de un mismo equipo no pueden atacarse.
    public boolean unidadesSonDelMismoEquipo(Jugador propietario) {
        return propietario == this.propietario;
    }

    public abstract int verTamanio();

    public abstract int verAlcance();

    public abstract boolean esMovible();

    public abstract void provocarDanio(Unidad unidad) throws AtaqueIncorrectoException;

    public abstract void ejecutarTareas();

}

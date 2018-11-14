package unidades;

import excepciones.main.OroInsuficienteException;
import excepciones.unidades.AtaqueIncorrectoException;
import main.Jugador;
import unidades.estados.unidades.EstadoDeUnidad;
import unidades.estados.unidades.Muerto;
import unidades.estados.unidades.Vivo;

public abstract class Unidad implements Dibujable {

    protected EstadoDeUnidad estadoDeUnidad;

    protected Jugador propietario;

    protected int vida;

    protected int oro;

    public Unidad() {
        this.estadoDeUnidad = new Vivo(); //TODO Los edificios tienen vida ? wtf
    }

    public void recibirDanio(int danio) {
        this.vida -= danio;
        if (this.vida <= 0)
            this.estadoDeUnidad = new Muerto();
    }

    public void cobrarCostoDeCreacion() throws OroInsuficienteException {
        this.propietario.cobrarOro(this.oro);
    }

    public abstract int verTamanio();

    public abstract int verAlcance();

    public abstract boolean esMovible();

    abstract public void provocarDanio(Unidad unidad) throws AtaqueIncorrectoException;

    abstract public void ejecutarTareas();

    public EstadoDeUnidad verEstadoDeUnidad() {
        return this.estadoDeUnidad;
    }

    public int verVida() {
        return this.vida;
    }

    public boolean esMapeable() {//TODO: Usar en el mapa
        return this.estadoDeUnidad.esMapleable();
    }


    //ToDo: Las unidades de un mismo equipo no pueden atacarse.
    public boolean unidadesSonDelMismoEquipo(Jugador propietario){
        return propietario == this.propietario;
    }
}

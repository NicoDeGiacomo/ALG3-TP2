package unidades;

import excepciones.main.OroInsuficienteException;
import main.Jugador;
import unidades.estados.EstadoDeUnidad;
import unidades.estados.Muerto;
import unidades.estados.Vivo;

public abstract class Unidad implements Dibujable {

    protected EstadoDeUnidad estadoDeUnidad;

    protected Jugador propietario;

    protected int vida;

    public Unidad() {
        this.estadoDeUnidad = new Vivo(); //TODO Los edificios tienen vida ? wtf
    }

    public void recibirDanio(int danio){
        this.vida -= danio;
        if (this.vida <= 0)
            this.estadoDeUnidad = new Muerto();
    }

    public abstract void provocarDanio(Unidad unidad);

    public abstract void ejecutarTareas();

    public abstract void cobrarCostoDeCreacion() throws OroInsuficienteException;

    public int verVida() {
        return this.vida;
    }

    public boolean esMapeable() {//TODO: Usar en el mapa
        return this.estadoDeUnidad.esMapleable();
    }

    /*
    ToDo: Las unidades de un mismo equipo no pueden atacarse.
    public boolean compararPropietario(Jugador propietario){
        return propietario == this.propietario;
    }*/
}

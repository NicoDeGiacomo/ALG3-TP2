package unidades.edificio;

import excepciones.main.OroInsuficienteException;
import unidades.Unidad;
import unidades.estados.EnConstruccion;
import unidades.estados.Vivo;

public abstract class Edificio extends Unidad {

    int tamanio;

    public abstract void crearUnidad() throws OroInsuficienteException;

    @Override
    public boolean esMovible() {
        return false;
    }

    @Override
    public int verAlcance(){
        return 1;
    }

    @Override
    public int verTamanio(){
        return this.tamanio;
    }

    public abstract boolean arreglar();

    public void comenzarConstruccion() {
        this.estadoDeUnidad = new EnConstruccion();
    }

    public void terminarConstruccion() {
        this.estadoDeUnidad = new Vivo();
    }
}

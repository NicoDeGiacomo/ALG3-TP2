package unidades.edificio;

import excepciones.unidades.AtaqueIncorrectoException;
import excepciones.unidades.CreacionDeCastilloException;
import excepciones.unidades.UnidadNoEspecificadaException;
import excepciones.main.OroInsuficienteException;
import unidades.Unidad;
import unidades.estados.unidades.EnConstruccion;
import unidades.estados.unidades.Vivo;
import unidades.milicia.Milicia;

public abstract class Edificio extends Unidad {

    int tamanio;
    int alcance = 1;

    public abstract Milicia crearUnidad() throws OroInsuficienteException, UnidadNoEspecificadaException;

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

    public abstract boolean arreglar();

    public void comenzarConstruccion() throws OroInsuficienteException, CreacionDeCastilloException {
        this.propietario.agregarUnidad(this);
        this.estadoDeUnidad = new EnConstruccion();
    }

    public void terminarConstruccion() {
        this.estadoDeUnidad = new Vivo();
    }


}

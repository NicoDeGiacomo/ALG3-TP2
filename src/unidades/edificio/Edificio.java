package unidades.edificio;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.AtaqueIncorrectoException;
import excepciones.unidades.CreacionDeCastilloException;
import excepciones.unidades.UnidadNoEspecificadaException;
import main.Jugador;
import unidades.Unidad;
import unidades.estados.unidades.EnConstruccion;
import unidades.estados.unidades.Vivo;
import unidades.milicia.Aldeano;

import java.awt.geom.Point2D;

public abstract class Edificio extends Unidad {

    int tamanio;
    int alcance = 1;

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

    public boolean terminoConstruccion() {
        return this.estadoDeUnidad.getClass() != EnConstruccion.class;
    }

    protected void reportarMuerte() {
        this.propietario.removerUnidad(this);
    }

    public abstract void crearUnidad() throws OroInsuficienteException, UnidadNoEspecificadaException, LimiteDePoblacionException, CoordenadaInvalidaException;

    public abstract boolean arreglar();

    public void turnoConstruido() {
        this.estadoDeUnidad = this.estadoDeUnidad.aumentarTurnoDeConstruccion();
    }
}

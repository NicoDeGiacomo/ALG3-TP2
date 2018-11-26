package unidades.estados.aldeano;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.CreacionDeCastilloException;
import main.Jugador;
import unidades.edificio.Edificio;
import unidades.milicia.Aldeano;

import java.awt.geom.Point2D;

public class Ocioso extends EstadoDeAldeano {

    public Ocioso(Jugador propietario) {
        super(propietario);
    }

    @Override
    public EstadoDeAldeano comenzarReparacion(Jugador propietario, Edificio edificio) {
        return new Reparando(this.propietario, edificio);
    }

    @Override
    public boolean esMovible(){
        return true;
    }


    @Override
    public EstadoDeAldeano comenzarConstruccion(Jugador propietario, Edificio edificio, Aldeano aldeano, Point2D pos) throws OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        return new Construyendo(this.propietario, edificio, aldeano, pos);
    }

    @Override
    public EstadoDeAldeano ejecutarTareas() {
        this.propietario.recolectarOro(this);
        return this;
    }

    public int obtenerRecollecion() {
        return 20;
    }
}

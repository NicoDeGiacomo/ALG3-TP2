package unidades.estados.aldeano;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.AldeanoOcupadoException;
import excepciones.unidades.CreacionDeCastilloException;
import main.Jugador;
import unidades.edificio.Edificio;
import unidades.milicia.Aldeano;

import java.awt.geom.Point2D;

public class Construyendo extends EstadoDeAldeano {
    private Edificio edificio;

    Construyendo(Jugador propietario, Edificio edificio, Aldeano aldeano, Point2D pos) throws OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        super(propietario);
        this.edificio = edificio;
        this.edificio.comenzarConstruccion(aldeano, pos);
    }

    @Override
    public boolean esMovible(){
        return false;
    }

    @Override
    public EstadoDeAldeano ejecutarTareas() {
        this.edificio.turnoConstruido();
        if (edificio.terminoConstruccion()){
            return new Ocioso(this.propietario);
        }
        return this;
    }

    @Override
    public EstadoDeAldeano comenzarReparacion(Jugador propietario, Edificio edificio) throws AldeanoOcupadoException {
        throw new AldeanoOcupadoException("El aldeano se encuentra construyendo.");
    }

    @Override
    public EstadoDeAldeano comenzarConstruccion(Jugador propietario, Edificio edificio, Aldeano aldeano, Point2D pos) throws AldeanoOcupadoException {
        throw new AldeanoOcupadoException("El aldeano se encuentra construyendo.");
    }
}

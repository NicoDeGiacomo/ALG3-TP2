package unidades.estados.aldeano;

import excepciones.unidades.AldeanoOcupadoException;
import main.Jugador;
import unidades.edificio.Edificio;
import unidades.milicia.Aldeano;

import java.awt.geom.Point2D;

public class Reparando extends EstadoDeAldeano {

    private Edificio edificio;

    public Reparando(Jugador propietario, Edificio edificio) {
        super(propietario);
        this.edificio = edificio;
    }

    @Override
    public EstadoDeAldeano ejecutarTareas() {
        if (this.edificio.arreglar()) {
            return new Ocioso(propietario);
        }
        return this;
    }

    @Override
    public EstadoDeAldeano comenzarReparacion(Jugador propietario, Edificio edificio) throws AldeanoOcupadoException {
        throw new AldeanoOcupadoException("El aldeano se encuentra reparando.");
    }

    @Override
    public EstadoDeAldeano comenzarConstruccion(Jugador propietario, Edificio edificio, Aldeano aldeano, Point2D pos) throws AldeanoOcupadoException {
        throw new AldeanoOcupadoException("El aldeano se encuentra reparando.");
    }
}

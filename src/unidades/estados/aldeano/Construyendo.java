package unidades.estados.aldeano;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.AldeanoOcupadoException;
import excepciones.unidades.CreacionDeCastilloException;
import main.Jugador;
import unidades.edificios.Edificio;
import unidades.milicias.Aldeano;

import java.awt.geom.Point2D;

public class Construyendo extends EstadoDeAldeano {
    private Edificio edificio;
    private int contadorDeTurnos = 0;

    Construyendo(Jugador propietario, Edificio edificio, Aldeano aldeano, Point2D pos) throws OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        super(propietario);
        this.edificio = edificio;
        this.edificio.comenzarConstruccion(aldeano, pos);
    }

    @Override
    public boolean esMovible() {
        return false;
    }

    @Override
    public EstadoDeAldeano ejecutarTareas() {
        this.contadorDeTurnos++;
        if (this.contadorDeTurnos == 3) {
            this.edificio.terminarConstruccion();
            return new Ocioso(this.propietario);
        }
        return this;
    }

    @Override
    public EstadoDeAldeano comenzarReparacion(Jugador propietario, Edificio edificio) throws AldeanoOcupadoException {
        throw new AldeanoOcupadoException("El Aldeano se encuentra Construyendo.");
    }

    @Override
    public EstadoDeAldeano comenzarConstruccion(Jugador propietario, Edificio edificio, Aldeano aldeano, Point2D pos) throws AldeanoOcupadoException {
        throw new AldeanoOcupadoException("El Aldeano se encuentra Construyendo.");
    }
}

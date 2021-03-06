package unidades.estados.aldeano;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.AldeanoOcupadoException;
import excepciones.unidades.CreacionDeCastilloException;
import main.Jugador;
import unidades.edificios.Edificio;
import unidades.milicias.Aldeano;

import java.awt.geom.Point2D;

public abstract class EstadoDeAldeano {

    Jugador propietario;

    public abstract EstadoDeAldeano ejecutarTareas();

    EstadoDeAldeano(Jugador propietario){
        this.propietario = propietario;
    }

    public abstract EstadoDeAldeano comenzarReparacion(Jugador propietario, Edificio edificio) throws AldeanoOcupadoException;

    public abstract EstadoDeAldeano comenzarConstruccion(Jugador propietario, Edificio edificio, Aldeano aldeano, Point2D pos) throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException;

    public abstract boolean esMovible();
}

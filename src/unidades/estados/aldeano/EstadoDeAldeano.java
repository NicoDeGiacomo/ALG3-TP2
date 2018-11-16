package unidades.estados.aldeano;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.FueraDeRangoException;
import excepciones.mapa.PosicionOcupadaException;
import excepciones.unidades.AldeanoOcupadoException;
import excepciones.unidades.CreacionDeCastilloException;
import main.Jugador;
import unidades.edificio.Edificio;
import unidades.milicia.Aldeano;

import java.awt.geom.Point2D;

public abstract class EstadoDeAldeano {

    Jugador propietario;

    public abstract EstadoDeAldeano ejecutarTareas();

    EstadoDeAldeano(Jugador propietario){
        this.propietario = propietario;
    }

    public abstract EstadoDeAldeano comenzarReparacion(Jugador propietario, Edificio edificio) throws AldeanoOcupadoException;

    public abstract EstadoDeAldeano comenzarConstruccion(Jugador propietario, Edificio edificio, Aldeano aldeano, Point2D pos) throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, FueraDeRangoException, PosicionOcupadaException;
}

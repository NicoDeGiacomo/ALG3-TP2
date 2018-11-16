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

public class Construyendo extends EstadoDeAldeano {
    private Edificio edificio;
    private int contadorDeTurnos;

    Construyendo(Jugador propietario, Edificio edificio, Aldeano aldeano, Point2D pos) throws OroInsuficienteException, CreacionDeCastilloException, FueraDeRangoException, PosicionOcupadaException {
        super(propietario);
        this.edificio = edificio;
        this.contadorDeTurnos = 0;
        this.edificio.comenzarConstruccion(aldeano, pos);
    }

    @Override
    public EstadoDeAldeano ejecutarTareas() {
        this.contadorDeTurnos++;
        if (this.contadorDeTurnos == 3){
            this.edificio.terminarConstruccion();
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

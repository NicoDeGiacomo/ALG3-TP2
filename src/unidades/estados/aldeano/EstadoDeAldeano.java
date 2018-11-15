package unidades.estados.aldeano;

import excepciones.main.OroInsuficienteException;
import excepciones.unidades.AldeanoOcupadoException;
import main.Jugador;
import unidades.edificio.Edificio;

public abstract class EstadoDeAldeano {

    Jugador propietario;

    public abstract EstadoDeAldeano ejecutarTareas();

    EstadoDeAldeano(Jugador propietario){
        this.propietario = propietario;
    }

    public abstract EstadoDeAldeano comenzarReparacion(Jugador propietario, Edificio edificio) throws AldeanoOcupadoException;

    public abstract EstadoDeAldeano comenzarConstruccion(Jugador propietario, Edificio edificio) throws AldeanoOcupadoException, OroInsuficienteException;
}

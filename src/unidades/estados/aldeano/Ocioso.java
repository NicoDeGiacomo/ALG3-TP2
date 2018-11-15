package unidades.estados.aldeano;

import excepciones.main.OroInsuficienteException;
import excepciones.unidades.CreacionDeCastilloException;
import main.Jugador;
import unidades.edificio.Edificio;

public class Ocioso extends EstadoDeAldeano {

    public Ocioso(Jugador propietario) {
        super(propietario);
    }

    @Override
    public EstadoDeAldeano comenzarReparacion(Jugador propietario, Edificio edificio) {
        return new Reparando(this.propietario, edificio);
    }

    @Override
    public EstadoDeAldeano comenzarConstruccion(Jugador propietario, Edificio edificio) throws OroInsuficienteException, CreacionDeCastilloException {
        return new Construyendo(this.propietario, edificio);
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

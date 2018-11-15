package unidades.estados.aldeano;

import excepciones.main.OroInsuficienteException;
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
    public EstadoDeAldeano comenzarConstruccion(Jugador propietario, Edificio edificio) throws OroInsuficienteException {
        return new Construyendo(this.propietario, edificio);
    }

    @Override
    public EstadoDeAldeano ejecutarTareas() {
        this.propietario.recolectarOro(20);
        return this;
    }
}

package unidades.estados;

import main.Jugador;
import unidades.edificio.Edificio;
import unidades.milicia.Aldeano;

public class Ocioso extends EstadoDeAldeano{

    public Ocioso(Jugador propietario) {
        super(propietario);
    }

    @Override
    public EstadoDeAldeano comenzarReparacion(Jugador propietario, Edificio edificio) {
        return new Reparando(this.propietario, edificio);
    }

    @Override
    public EstadoDeAldeano ejecutarTareas() {
        this.propietario.recolectarOro(20);
        return this;
    }
}

package unidades.estados;

import main.Jugador;
import unidades.edificio.Edificio;

public class Construyendo extends EstadoDeAldeano {
    private Edificio edificio;

    private int contador;

    public Construyendo(Jugador propietario, Edificio edificio) {
        super(propietario);
        this.edificio = edificio;
        this.contador = 0;
        this.edificio.comenzarConstruccion();
    }

    @Override
    public EstadoDeAldeano ejecutarTareas() {
        this.contador ++;
        if (this.contador == 3){
            this.edificio.terminarConstruccion();
            return new Ocioso(this.propietario);
        }
        return this;
    }

    @Override
    public EstadoDeAldeano comenzarReparacion(Jugador propietario, Edificio edificio) {
        return null;
    }

    @Override
    public EstadoDeAldeano comenzarConstruccion(Jugador propietario, Edificio edificio) {
        return null;
    }
}

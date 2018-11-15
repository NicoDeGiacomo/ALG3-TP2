package unidades.estados.aldeano;

import excepciones.main.OroInsuficienteException;
import excepciones.unidades.AldeanoOcupadoException;
import excepciones.unidades.CreacionDeCastilloException;
import main.Jugador;
import unidades.edificio.Edificio;

public class Construyendo extends EstadoDeAldeano {
    private Edificio edificio;
    private int contadorDeTurnos;

    Construyendo(Jugador propietario, Edificio edificio) throws OroInsuficienteException, CreacionDeCastilloException {
        super(propietario);
        this.edificio = edificio;
        this.contadorDeTurnos = 0;
        this.edificio.comenzarConstruccion();
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
    public EstadoDeAldeano comenzarConstruccion(Jugador propietario, Edificio edificio) throws AldeanoOcupadoException {
        throw new AldeanoOcupadoException("El aldeano se encuentra construyendo.");
    }
}

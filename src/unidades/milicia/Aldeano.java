package unidades.milicia;

import excepciones.unidades.AldeanoOcupadoException;
import main.Jugador;
import unidades.edificio.Edificio;
import unidades.estados.aldeano.EstadoDeAldeano;
import unidades.estados.aldeano.Ocioso;

public class Aldeano extends Milicia {

    private EstadoDeAldeano estado;

    public Aldeano(Jugador propietario){
        super();
        this.propietario = propietario;
        this.vida = 50;
        this.estado = new Ocioso(this.propietario);
        this.danio = 0;
        this.oro = 25;
    }


    @Override
    public void ejecutarTareas() {
        this.estado = this.estado.ejecutarTareas();
    }


    void reparar(Edificio edificio) throws AldeanoOcupadoException {
        this.estado = this.estado.comenzarReparacion(this.propietario, edificio);
    }

    void construir(Edificio edificio) throws AldeanoOcupadoException {
        this.estado = this.estado.comenzarConstruccion(this.propietario, edificio);
    }

    public void arreglar(Edificio edificio) {
        edificio.arreglar();
    }

    public EstadoDeAldeano verEstadoDeAldeano() {
        return this.estado;
    }
}

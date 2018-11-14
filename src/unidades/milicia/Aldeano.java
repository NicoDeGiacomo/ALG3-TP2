package unidades.milicia;

import excepciones.unidades.AldeanoOcupadoException;
import excepciones.unidades.AtaqueIncorrectoException;
import main.Jugador;
import unidades.Unidad;
import unidades.edificio.Edificio;
import unidades.estados.aldeano.EstadoDeAldeano;
import unidades.estados.aldeano.Ocioso;

public class Aldeano extends Milicia {

    private EstadoDeAldeano estado;

    public Aldeano(Jugador propietario) {
        super();
        this.propietario = propietario;
        this.vida = 50;
        this.estado = new Ocioso(this.propietario);
        this.oro = 25;
    }


    @Override
    public void ejecutarTareas() {
        this.estado = this.estado.ejecutarTareas();
    }

    @Override
    public void provocarDanio(Unidad unidad) throws AtaqueIncorrectoException {
        throw new AtaqueIncorrectoException("El aldeano no puede atacar.");
    }


    public void reparar(Edificio edificio) throws AldeanoOcupadoException {
        this.estado = this.estado.comenzarReparacion(this.propietario, edificio);
    }

    public void construir(Edificio edificio) throws AldeanoOcupadoException {
        this.estado = this.estado.comenzarConstruccion(this.propietario, edificio);
    }

    public void arreglar(Edificio edificio) {
        edificio.arreglar();
    }
}

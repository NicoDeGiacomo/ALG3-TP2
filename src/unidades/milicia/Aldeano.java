package unidades.milicia;

import excepciones.main.OroInsuficienteException;
import excepciones.unidades.AldeanoOcupadoException;
import main.Jugador;
import unidades.Unidad;
import unidades.edificio.Edificio;
import unidades.estados.EstadoDeAldeano;
import unidades.estados.Ocioso;

public class Aldeano extends Milicia {

    private EstadoDeAldeano estado;

    public Aldeano(Jugador propietario){
        super();
        this.propietario = propietario;
        this.vida = 50;
        this.estado = new Ocioso(this.propietario);
        this.danio = 0;

    }

    @Override
    public void provocarDanio(Unidad unidad) {

    }

    @Override
    public void ejecutarTareas() {
        this.estado = this.estado.ejecutarTareas();
    }

    @Override
    public void cobrarCostoDeCreacion() throws OroInsuficienteException {
        this.propietario.cobrarOro(25);
    }

    public void reparar(Edificio edificio) throws AldeanoOcupadoException {
        this.estado = this.estado.comenzarReparacion(this.propietario, edificio);
    }

    public void construir(Edificio edificio) throws AldeanoOcupadoException {
        this.estado = this.estado.comenzarConstruccion(this.propietario, edificio);
    }

    public void sumarOro(){

    }

    public void arreglar(Edificio edificio) {
        edificio.arreglar();
    }
}

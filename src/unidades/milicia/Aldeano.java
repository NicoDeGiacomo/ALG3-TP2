package unidades.milicia;

import excepciones.main.OroInsuficienteException;
import main.Jugador;
import unidades.Unidad;
import unidades.edificio.Edificio;
import unidades.estados.Construyendo;
import unidades.estados.EstadoDeAldeano;
import unidades.estados.Ocioso;
import unidades.estados.Reparando;

public class Aldeano extends Milicia {

    private EstadoDeAldeano estado;

    public Aldeano(Jugador propietario){
        super();
        this.vida = 50;
        this.estado = new Ocioso(this.propietario);
        this.propietario = propietario;
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

    public void reparar(Edificio edificio) {
        this.estado = this.estado.comenzarReparacion(this.propietario, edificio);
        //this.estado = new Reparando(this.propietario, edificio);
    }

    public void construir(Edificio edificio) {
        this.estado = this.estado.comenzarConstruccion(this.propietario, edificio);
        //this.estado = new Construyendo(propietario, edificio);
    }

    public void sumarOro(){

    }

    public void arreglar(Edificio edificio) {
        edificio.arreglar();
    }
}

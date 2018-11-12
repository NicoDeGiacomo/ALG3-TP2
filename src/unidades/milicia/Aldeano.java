package unidades.milicia;

import unidades.Unidad;
import unidades.edificio.Edificio;
import unidades.estados.EstadoDeAldeano;

public class Aldeano extends Milicia {

    EstadoDeAldeano estado;
    private int vida;

    public Aldeano(){
        vida = 50;
    }

    @Override
    public int verVida() {
        return vida;
    }

    @Override
    public void recibirDanio(int danio) {
        vida = vida - danio;
    }

    @Override
    public void provocarDanio(Unidad unidad) {
    }

    @Override
    public void ejecutarTareas() {

    }

    @Override
    public void cobrarCostoDeCreacion() {
    }

    public void reparar(Edificio edificio) {
        //if( estado.verificarEstado()) arreglar( edificio );
    }

    public void construir(Edificio edificio) {

    }

    public void sumarOro(){

    }

    public void arreglar(Edificio edificio) {
        edificio.arreglar( this);
    }
}

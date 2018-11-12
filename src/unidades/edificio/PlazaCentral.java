package unidades.edificio;

import unidades.Unidad;
import unidades.milicia.Aldeano;

public class PlazaCentral extends Edificio {

    private int vida;

    public PlazaCentral(){
        vida = 450;
    }

    @Override
    public int verVida() {
        return vida;
    }

    @Override
    public void recibirDanio(int danio) {
        vida -= danio;
    }

    @Override
    public void provocarDanio(Unidad unidad) {

    }

    @Override
    public void ejecutarTareas() {

    }

    @Override
    public void cobrarCostoDeCreacion() {}

    public void arreglar(Unidad unidad){
        //ToDo: Vida maxima? Como verificar si tiene 999 y tenes que curar 1.
        if(vida < 450){
            vida += 25 ;
        }
    }

    @Override
    public int verTamanio() {
        return 4;
    }

    @Override
    public Unidad crearUnidad(){
        return new Aldeano();
    }

}

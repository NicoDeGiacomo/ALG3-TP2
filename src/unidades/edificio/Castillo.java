package unidades.edificio;

import unidades.Unidad;
import unidades.milicia.ArmaDeAsedio;


public class Castillo extends Edificio{
    private int vida;
    private int danio;

    public Castillo(){
        vida = 1000; danio = 20;
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
        unidad.recibirDanio(danio);
    }

    @Override
    public void arreglar(Unidad unidad){
        //ToDo: Vida maxima? Como verificar si tiene 999 y tenes que curar 1.
        if(vida < 1000){
            vida += 15 ;
        }
    }

    @Override
    public void ejecutarTareas() {
    }

    @Override
    public void cobrarCostoDeCreacion() {
    }

    @Override
    public int verTamanio() {
        return 8;
    }

    @Override
    public Unidad crearUnidad(){
        return new ArmaDeAsedio();
    }


}

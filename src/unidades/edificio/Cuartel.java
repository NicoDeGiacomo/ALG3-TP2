package unidades.edificio;

import excepciones.main.OroInsuficienteException;
import unidades.Unidad;
import unidades.milicia.Arquero;
import unidades.milicia.Espadachin;

public class Cuartel extends Edificio {

    public Cuartel(){
        this.vida = 250;
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
        if(vida < 250){
            vida += 50 ;
        }
    }

    @Override
    public int verTamanio() {
        return 4;
    }

    @Override
    public void crearUnidad() throws OroInsuficienteException{
        //TODO
    }

    @Override
    public boolean arreglar() {
        return false;
    }

    public void crearEspadachin() throws OroInsuficienteException {
        this.propietario.agregarUnidad(new Espadachin());
    }

    public void crearArquero() throws OroInsuficienteException {
        this.propietario.agregarUnidad(new Arquero());
    }

}

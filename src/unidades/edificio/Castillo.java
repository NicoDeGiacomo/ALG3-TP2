package unidades.edificio;

import excepciones.main.OroInsuficienteException;
import unidades.Unidad;
import unidades.milicia.ArmaDeAsedio;
import unidades.milicia.Arquero;


public class Castillo extends Edificio {
    private int danio;

    public Castillo(){
        this.vida = 1000;
        this.danio = 20;
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
    public boolean arreglar(){
        this.vida += 15;

        if (this.vida >= 1000){
            this.vida = 1000;
            return true;
        }

        return false;
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
    public void crearUnidad() throws OroInsuficienteException {
        this.propietario.agregarUnidad(new ArmaDeAsedio());
    }


}

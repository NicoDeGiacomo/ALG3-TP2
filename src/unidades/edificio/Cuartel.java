package unidades.edificio;

import excepciones.main.OroInsuficienteException;
import main.Jugador;
import unidades.Unidad;
import unidades.milicia.Arquero;
import unidades.milicia.Espadachin;

public class Cuartel extends Edificio {

    public Cuartel(Jugador propietario){
        super();
        this.propietario = propietario;
        this.vida = 250;
        this.tamanio = 4;
    }

    @Override
    public void provocarDanio(Unidad unidad) {

    }

    @Override
    public void ejecutarTareas() {

    }

    @Override
    public void cobrarCostoDeCreacion() {}

    @Override
    public boolean arreglar(){
        this.vida += 50;
        if (this.vida >= 250){
            this.vida = 250;
            return true;
        }
        return false;
    }

    @Override
    public void crearUnidad() throws OroInsuficienteException{
        //TODO
    }


    public void crearEspadachin() throws OroInsuficienteException {
        this.propietario.agregarUnidad(new Espadachin(this.propietario));
    }

    public void crearArquero() throws OroInsuficienteException {
        this.propietario.agregarUnidad(new Arquero(this.propietario));
    }

}

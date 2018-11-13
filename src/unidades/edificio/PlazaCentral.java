package unidades.edificio;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.EspacioInsuficienteException;
import main.Jugador;
import unidades.Unidad;
import unidades.milicia.Aldeano;

public class PlazaCentral extends Edificio {

    public PlazaCentral(Jugador propietario){
        super();
        this.propietario = propietario;
        this.vida = 450;
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
        this.vida += 25;
        if (this.vida >= 450){
            this.vida = 450;
            return true;
        }
        return false;
    }

    @Override
    public void crearUnidad() throws OroInsuficienteException, EspacioInsuficienteException {
        this.propietario.agregarUnidad(new Aldeano(this.propietario), this);
    }

}

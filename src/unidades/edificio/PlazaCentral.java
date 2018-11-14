package unidades.edificio;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.EspacioInsuficienteException;
import main.Jugador;
import unidades.milicia.Aldeano;

public class PlazaCentral extends Edificio {

    public PlazaCentral(Jugador propietario){
        super();
        this.propietario = propietario;
        this.vida = 450;
        this.tamanio = 4;
        this.oro = 100;
    }

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

    @Override
    public void ejecutarTareas() {
    }
}

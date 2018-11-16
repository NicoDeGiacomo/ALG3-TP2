package unidades.edificio;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import main.Jugador;
import unidades.milicia.Aldeano;

public class PlazaCentral extends Edificio {

    public PlazaCentral(Jugador propietario){
        super();
        this.propietario = propietario;
        this.vida = 450;
        this.tamanio = 4;
        this.precio = 100;
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
    public void crearUnidad() throws OroInsuficienteException, LimiteDePoblacionException {
        Aldeano aldeano = new Aldeano(this.propietario);
        this.propietario.agregarUnidad(aldeano, this);
    }

    @Override
    public void ejecutarTareas() {
    }
}

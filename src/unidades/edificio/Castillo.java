package unidades.edificio;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.EspacioInsuficienteException;
import main.Jugador;
import unidades.Unidad;
import unidades.milicia.ArmaDeAsedio;


public class Castillo extends Edificio {
    private int danio;

    public Castillo(Jugador propietario){
        super();
        this.propietario = propietario;
        this.vida = 1000;
        this.danio = 20;
        this.tamanio = 8;
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
    public void crearUnidad() throws OroInsuficienteException, EspacioInsuficienteException {
        this.propietario.agregarUnidad(new ArmaDeAsedio(this.propietario), this);
    }


}

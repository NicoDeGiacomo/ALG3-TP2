package unidades.edificio;

import excepciones.main.OroInsuficienteException;
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
        this.tamanio = 16;
        this.alcance = 3;
    }

    @Override
    public void provocarDanio(Unidad unidad) {
        if(unidad.unidadesSonDelMismoEquipo(this.propietario)) return;
        unidad.recibirDanio(this.danio);
    }

    @Override
    public void ejecutarTareas() {
        //TODO: El edificio debe atacar a todas las unidades enemigas a su alcance!!
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
    public void crearUnidad() throws OroInsuficienteException {
        this.propietario.agregarUnidad(new ArmaDeAsedio(this.propietario), this);
    }

    @Override
    public void cobrarCostoDeCreacion()  {
    }

}

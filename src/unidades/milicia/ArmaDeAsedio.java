package unidades.milicia;

import main.Jugador;
import unidades.Unidad;

public class ArmaDeAsedio extends Milicia{

    private Unidad armaMontada;

    public ArmaDeAsedio(Jugador propietario){
        super();
        this.propietario = propietario;
        vida = 150;
        danio = 75;
        armaMontada = null;
    }

    @Override
    public void provocarDanio(Unidad unidad) {
        if ( !unidad.esMovible() && armaMontada != null)  unidad.recibirDanio(danio);
    }

    //ToDo: Estados de arma de asedio
    void montarArma(Unidad unidad){
        armaMontada =  unidad ;
    }

    public Unidad desmontarArma(){
        if (armaMontada != null) {
            Unidad unidad = armaMontada;
            armaMontada = null;
            return unidad;
        }
        return null;
    }

    @Override
    public void ejecutarTareas() {

    }

    @Override
    public void cobrarCostoDeCreacion() {

    }
}















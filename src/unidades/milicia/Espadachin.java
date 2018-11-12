package unidades.milicia;

import unidades.Unidad;

public class Espadachin extends Milicia {
    private int danio;
    private int danioAEdificios ;

    public Espadachin(){
        vida = 100;
        danio = 25;
        danioAEdificios = 15;
    }

    @Override
    public void recibirDanio(int danio) {
        vida -= danio;
    }

    @Override
    public void provocarDanio(Unidad unidad) {
        if ( !unidad.esMovible() )  unidad.recibirDanio(danioAEdificios);
        else unidad.recibirDanio(danio);
    }

    @Override
    public void ejecutarTareas() {

    }

    @Override
    public void cobrarCostoDeCreacion() {

    }
}

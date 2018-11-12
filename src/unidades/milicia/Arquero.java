package unidades.milicia;

import unidades.Unidad;

public class Arquero extends Milicia {
    private int danio;
    private int danioAEdificios;

    public Arquero(){
        this.vida = 75;
        this.danio = 15;
        this.danioAEdificios = 10;
    }

    @Override
    public void recibirDanio(int danio) {
        vida -= danio;
        }

    @Override
    public void provocarDanio(Unidad unidad) {
        if ( !unidad.esMovible() ) unidad.recibirDanio(danioAEdificios);
        else unidad.recibirDanio(danio);
    }

    @Override
    public void ejecutarTareas() {

    }

    @Override
    public void cobrarCostoDeCreacion() {

    }
}


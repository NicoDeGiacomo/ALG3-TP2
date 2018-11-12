package unidades.milicia;

import unidades.Unidad;

public class Arquero extends Milicia {
    private int danio;
    private int vida;
    private int danioAEdificios;

    public Arquero(){
        vida = 75;
        danio = 15;
        danioAEdificios = 10;
    }

    @Override
    public int verVida() {
        return vida;
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


package unidades.edificio;

import unidades.Unidad;

public abstract class Edificio extends Unidad {

    @Override
    public boolean esMovible() {
        return false;
    }

    @Override
    public int verAlcance(){
        return 1;
    }

    void crearUnidad() {}
}

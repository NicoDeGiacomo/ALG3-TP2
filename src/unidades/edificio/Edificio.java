package unidades.edificio;

import unidades.Unidad;

public abstract class Edificio extends Unidad {

    @Override
    public boolean esMovible() {
        return false;
    }

    void crearUnidad() {}
}

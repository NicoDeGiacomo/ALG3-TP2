package unidades.edificio;

import unidades.Unidad;

public abstract class Edificio extends Unidad {

    public abstract Unidad crearUnidad();

    @Override
    public boolean esMovible() {
        return false;
    }

    public abstract void arreglar(Unidad unidad);


}

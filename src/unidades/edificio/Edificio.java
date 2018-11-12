package unidades.edificio;

import unidades.Unidad;

public abstract class Edificio extends Unidad {

    public abstract Unidad crearUnidad();

    @Override
    public boolean esMovible() {
        return false;
    }

    @Override
    public int verAlcance(){
        return 1;
    }

    public abstract void arreglar(Unidad unidad);

}

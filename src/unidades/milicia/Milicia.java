package unidades.milicia;

import unidades.Unidad;

public abstract class Milicia extends Unidad {

    @Override
    public boolean esMovible() {
        return true;
    }

    @Override
    public int verTamanio(){
        return 1;
    }

}

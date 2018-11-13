package unidades.milicia;

import unidades.Unidad;

public abstract class Milicia extends Unidad {

    int danio;
    int danioAEdificios ;

    @Override
    public boolean esMovible() {
        return true;
    }

    @Override
    public int verTamanio(){
        return 1;
    }

    @Override
    public int verAlcance(){
        return 1;
    }

    public void provocarDanio(Unidad unidad) {
        if ( !unidad.esMovible() ) unidad.recibirDanio(this.danioAEdificios);
        else unidad.recibirDanio(this.danio);
    }

}

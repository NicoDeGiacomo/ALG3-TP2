package unidades.milicia;

import excepciones.unidades.AtaqueIncorrectoException;
import unidades.Unidad;

public abstract class Milicia extends Unidad {

    int danio;
    int danioAEdificios;
    int alcance = 1;

    @Override
    public boolean esMovible() {
        return true;
    }

    @Override
    public int verTamanio() {
        return 1;
    }

    @Override
    public int verAlcance() {
        return this.alcance;
    }

    public void provocarDanio(Unidad unidad) throws AtaqueIncorrectoException {
        if (!unidad.esMovible())
            unidad.recibirDanio(this.danioAEdificios);
        else
            unidad.recibirDanio(this.danio);
    }

}

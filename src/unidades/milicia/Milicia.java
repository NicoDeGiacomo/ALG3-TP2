package unidades.milicia;

import excepciones.unidades.AtaqueIncorrectoException;
import main.Jugador;
import unidades.Unidad;

public abstract class Milicia extends Unidad {

    int danio;
    int danioAEdificios;
    int alcance = 1;

    public Milicia(Jugador propietario) {
        super(propietario);
    }

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
        if (unidad.unidadesSonDelMismoEquipo(this.propietario))
            return;
        if (!unidad.esMovible())
            unidad.recibirDanio(this.danioAEdificios);
        else
            unidad.recibirDanio(this.danio);
    }

    protected void reportarMuerte() {
        this.propietario.removerUnidad(this);
    }

}

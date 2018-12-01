package unidades.milicias;

import excepciones.unidades.AtaqueIncorrectoException;
import javafx.scene.media.Media;
import main.Jugador;
import unidades.Unidad;

import java.io.File;
import java.util.Random;

public abstract class Milicia extends Unidad {

    int danio;
    int danioAEdificios;
    int alcance = 1;
    private final int CANTIDAD_SONIDOS_MUERTE = 6;

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

    @Override
    public Media obtenerSonidoDeMuerte() {
        return new Media(new File("src/assets/sounds/unidades/milicias/muerte/muerte" + new Random().nextInt(CANTIDAD_SONIDOS_MUERTE) + 1 + ".wav").toURI().toString());
    }
}

package unidades.edificio;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.unidades.UnidadNoEspecificadaException;
import main.Jugador;
import unidades.milicia.Arquero;
import unidades.milicia.Espadachin;

public class Cuartel extends Edificio {

    public Cuartel(Jugador propietario) {
        super();
        this.propietario = propietario;
        this.vida = 250;
        this.tamanio = 4;
        this.precio = 50;
    }

    @Override
    public boolean arreglar() {
        this.vida += 50;
        if (this.vida >= 250) {
            this.vida = 250;
            return true;
        }
        return false;
    }

    @Override
    public void crearUnidad() throws UnidadNoEspecificadaException {
        throw new UnidadNoEspecificadaException("El cuartel puede crear mas de una unidad. Utilizar metodos: crearEspadachin / crearArquero");
    }

    void crearEspadachin() throws OroInsuficienteException, LimiteDePoblacionException {
        Espadachin espadachin = new Espadachin(this.propietario);
        this.propietario.agregarUnidad(espadachin, this);
    }

    void crearArquero() throws OroInsuficienteException, LimiteDePoblacionException {
        Arquero arquero = new Arquero(this.propietario);
        this.propietario.agregarUnidad(arquero, this);
    }

    @Override
    public void ejecutarTareas() {
    }
}

package unidades.edificio;

import excepciones.unidades.UnidadNoEspecificadaException;
import excepciones.main.OroInsuficienteException;
import main.Jugador;
import unidades.milicia.Arquero;
import unidades.milicia.Espadachin;

public class Cuartel extends Edificio {

    public Cuartel(Jugador propietario) {
        super();
        this.propietario = propietario;
        this.vida = 250;
        this.tamanio = 4;
        this.oro = 50;
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

    void crearEspadachin() throws OroInsuficienteException {
        this.propietario.agregarUnidad(new Espadachin(this.propietario), this);
    }

    void crearArquero() throws OroInsuficienteException {
        this.propietario.agregarUnidad(new Arquero(this.propietario), this);
    }

    @Override
    public void ejecutarTareas() {
    }
}

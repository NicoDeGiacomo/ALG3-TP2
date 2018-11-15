package unidades.edificio;

import excepciones.unidades.UnidadNoEspecificadaException;
import excepciones.main.OroInsuficienteException;
import main.Jugador;
import unidades.milicia.Arquero;
import unidades.milicia.Espadachin;
import unidades.milicia.Milicia;

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
    public Milicia crearUnidad() throws UnidadNoEspecificadaException {
        throw new UnidadNoEspecificadaException("El cuartel puede crear mas de una unidad. Utilizar metodos: crearEspadachin / crearArquero");
    }

    public Milicia crearEspadachin() throws OroInsuficienteException {
        Espadachin espadachin = new Espadachin(this.propietario);
        this.propietario.agregarUnidad(espadachin);
        return espadachin;
    }

    public Milicia crearArquero() throws OroInsuficienteException {
        Arquero arquero = new Arquero(this.propietario);
        this.propietario.agregarUnidad(arquero);
        return arquero;
    }

    @Override
    public void ejecutarTareas() {
    }
}

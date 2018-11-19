package unidades.edificio;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.CreacionDeCastilloException;
import main.Jugador;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.milicia.Aldeano;
import unidades.milicia.ArmaDeAsedio;

import java.awt.geom.Point2D;
import java.util.List;


public class Castillo extends Edificio {
    private int danio;

    public Castillo(Jugador propietario) {
        super();
        this.propietario = propietario;
        this.vida = 1000;
        this.danio = 20;
        this.tamanio = 16;
        this.alcance = 3;
    }

    @Override
    public void provocarDanio(Unidad unidad) {
        if (unidad.unidadesSonDelMismoEquipo(this.propietario))
            return;
        unidad.recibirDanio(this.danio);
    }

    @Override
    public void ejecutarTareas() {
        List<Dibujable> unidades = propietario.unidadesCercanas(this);
        for (Dibujable unidad : unidades)
            this.provocarDanio((Unidad) unidad);
    }

    @Override
    public boolean arreglar() {
        this.vida += 15;
        if (this.vida >= 1000) {
            this.vida = 1000;
            return true;
        }
        return false;
    }

    @Override
    public void crearUnidad() throws OroInsuficienteException, LimiteDePoblacionException, CoordenadaInvalidaException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(this.propietario);
        this.propietario.agregarUnidad(armaDeAsedio, this);
    }

    @Override
    public void comenzarConstruccion(Aldeano aldeano, Point2D pos) throws CreacionDeCastilloException {
        throw new CreacionDeCastilloException("No se puede construir un castillo.");
    }

}

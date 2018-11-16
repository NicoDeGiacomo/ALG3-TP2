package unidades.edificio;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.unidades.CreacionDeCastilloException;
import main.Jugador;
import unidades.Unidad;
import unidades.milicia.Aldeano;
import unidades.milicia.ArmaDeAsedio;

import java.awt.geom.Point2D;


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
        //TODO: El edificio debe atacar a todas las unidades enemigas a su alcance!!
        //Una lista de las unidades proximas al castillo?
        //for (Unidad unidad : unidades ) this.provocarDanio(unidad);
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
    public void crearUnidad() throws OroInsuficienteException, LimiteDePoblacionException {
        ArmaDeAsedio armaDeAsedio = new ArmaDeAsedio(this.propietario);
        this.propietario.agregarUnidad(armaDeAsedio, this);
    }

    @Override
    public void comenzarConstruccion(Aldeano aldeano, Point2D pos) throws CreacionDeCastilloException {
        throw new CreacionDeCastilloException("No se puede construir un castillo.");
    }

}

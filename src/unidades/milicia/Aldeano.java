package unidades.milicia;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.AldeanoOcupadoException;
import excepciones.unidades.AtaqueIncorrectoException;
import excepciones.unidades.CreacionDeCastilloException;
import javafx.scene.paint.Color;
import main.Jugador;
import unidades.Unidad;
import unidades.edificio.Edificio;
import unidades.estados.aldeano.EstadoDeAldeano;
import unidades.estados.aldeano.Ocioso;

import java.awt.geom.Point2D;

public class Aldeano extends Milicia {

    private EstadoDeAldeano estado;

    public Aldeano(Jugador propietario) {
        super(propietario);
        this.vida = 50;
        this.estado = new Ocioso(this.propietario);
        this.precio = 25;
    }

    @Override
    public void ejecutarTareas() {
        this.estado = this.estado.ejecutarTareas();
    }

    @Override
    public void provocarDanio(Unidad unidad) throws AtaqueIncorrectoException {
        throw new AtaqueIncorrectoException("El aldeano no puede atacar.");
    }

    void reparar(Edificio edificio) throws AldeanoOcupadoException {
        this.estado = this.estado.comenzarReparacion(this.propietario, edificio);
    }

    void construir(Edificio edificio, Point2D pos) throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        this.estado = this.estado.comenzarConstruccion(this.propietario, edificio, this, pos);
    }

    @Override
    public Color obtenerColor() {
        return Color.WHITE;
    }

    @Override
    public boolean mostrarMenu() {
        return false;
    }

}

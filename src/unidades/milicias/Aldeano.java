package unidades.milicias;

import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.unidades.AldeanoOcupadoException;
import excepciones.unidades.AtaqueIncorrectoException;
import excepciones.unidades.CreacionDeCastilloException;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import main.Jugador;
import ui.Menu;
import unidades.Unidad;
import unidades.edificios.Edificio;
import unidades.estados.aldeano.EstadoDeAldeano;
import unidades.estados.aldeano.Ocioso;

import java.awt.geom.Point2D;
import java.io.File;

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

    @Override
    public boolean esMovible(){
        return this.estado.esMovible();
    }

    public void reparar(Edificio edificio) throws AldeanoOcupadoException {
        this.estado = this.estado.comenzarReparacion(this.propietario, edificio);
    }

    public void construir(Edificio edificio, Point2D pos) throws AldeanoOcupadoException, OroInsuficienteException, CreacionDeCastilloException, CoordenadaInvalidaException {
        this.estado = this.estado.comenzarConstruccion(this.propietario, edificio, this, pos);
    }

    @Override
    public Color obtenerColor() {
        return Color.WHITE;
    }

    @Override
    public boolean mostrarMenu(Point2D point2D) {
        return Menu.mostrarMenuDeAldeano(this, point2D);
    }

    @Override
    public Media obtenerSonidoDeAtaque() {
        return null;
    }

    @Override
    public Media obtenerSonidoDeMovimiento() {
        return new Media(new File("src/assets/sounds/unidades/milicias/aldeanos/movimiento.wav").toURI().toString());
    }

    @Override
    public Media obtenerSonidoDeCreacion() {
        return new Media(new File("src/assets/sounds/unidades/milicias/aldeanos/creacion.wav").toURI().toString());
    }
}

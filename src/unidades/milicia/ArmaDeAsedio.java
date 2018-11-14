package unidades.milicia;

import excepciones.unidades.ArmaDeAsedioYaDesmontadaException;
import excepciones.unidades.ArmaDeAsedioYaMontadaException;
import excepciones.unidades.AtaqueIncorrectoException;
import main.Jugador;
import unidades.Unidad;
import unidades.estados.armaDeAsedio.EstadoDeArmaDeAsedio;
import unidades.estados.armaDeAsedio.ArmaDesmontada;

public class ArmaDeAsedio extends Milicia{

    private EstadoDeArmaDeAsedio estadoDeArma;

    public ArmaDeAsedio(Jugador propietario){
        super();
        this.estadoDeArma = new ArmaDesmontada();
        this.propietario = propietario;
        this.vida = 150;
        this.danioAEdificios = 75;
        this.precio = 200;
    }

    @Override
    public boolean esMovible() {
        return estadoDeArma.esMovible();
    }

    void montarArma() throws ArmaDeAsedioYaMontadaException {
        this.estadoDeArma =  this.estadoDeArma.montarArma();
    }

    void desmontarArma() throws ArmaDeAsedioYaDesmontadaException {
        this.estadoDeArma = this.estadoDeArma.desmontarArma();
    }

    @Override
    public void provocarDanio(Unidad unidad) throws AtaqueIncorrectoException {
        if (unidad.esMovible())
            throw new AtaqueIncorrectoException("El arma de asedio solo puede atacar edificios");
        this.estadoDeArma.provocarDanio(unidad, this.danioAEdificios);
    }

    @Override
    public void ejecutarTareas() {
    }


}















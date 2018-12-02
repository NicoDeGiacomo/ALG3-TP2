package unidades.estados.armaDeAsedio;

import excepciones.unidades.ArmaDeAsedioYaDesmontadaException;
import excepciones.unidades.ArmaDeAsedioYaMontadaException;
import unidades.Unidad;

public class ArmaDesmontada extends EstadoDeArmaDeAsedio {
    @Override
    public boolean esMovible() {
        return true;
    }

    @Override
    public void provocarDanio(Unidad unidad, int danio) {

    }

    @Override
    public EstadoDeArmaDeAsedio desmontarArma() throws ArmaDeAsedioYaDesmontadaException {
        throw new ArmaDeAsedioYaDesmontadaException("El Arma de Asedio se encuentra Desmontada.");
    }

    @Override
    public EstadoDeArmaDeAsedio montarArma() {
        return new ArmaMontada();
    }
}

package unidades.estados.armaDeAsedio;

import excepciones.unidades.ArmaDeAsedioYaDesmontadaException;
import excepciones.unidades.ArmaDeAsedioYaMontadaException;
import unidades.Unidad;

public class ArmaMontada extends EstadoDeArmaDeAsedio {
    @Override
    public boolean esMovible() {
        return false;
    }

    @Override
    public void provocarDanio(Unidad unidad, int danio) {
        if ( !unidad.esMovible() )  unidad.recibirDanio(danio);
    }


    @Override
    public EstadoDeArmaDeAsedio desmontarArma() {
        return new ArmaDesmontada();
    }

    @Override
    public EstadoDeArmaDeAsedio montarArma() throws ArmaDeAsedioYaMontadaException {
        throw new ArmaDeAsedioYaMontadaException("El arma de asedio se encuentra montada.");
    }
}

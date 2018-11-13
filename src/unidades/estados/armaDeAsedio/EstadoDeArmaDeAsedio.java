package unidades.estados.armaDeAsedio;

import excepciones.unidades.ArmaDeAsedioYaDesmontadaException;
import excepciones.unidades.ArmaDeAsedioYaMontadaException;
import unidades.Unidad;

public abstract class  EstadoDeArmaDeAsedio {

    public abstract boolean esMovible() ;

    public abstract void provocarDanio(Unidad unidad, int danio);

    public abstract EstadoDeArmaDeAsedio desmontarArma() throws ArmaDeAsedioYaDesmontadaException;

    public abstract EstadoDeArmaDeAsedio montarArma()throws ArmaDeAsedioYaMontadaException;
}

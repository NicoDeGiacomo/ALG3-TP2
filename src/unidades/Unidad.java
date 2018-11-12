package unidades;

import main.Jugador;

public abstract class Unidad implements Dibujable {

    Jugador propietario;

    public abstract void recibirDanio(int danio);

    public abstract void provocarDanio(Unidad unidad);

    public abstract void ejecutarTareas();

    public abstract void cobrarCostoDeCreacion();

    public abstract int verVida() ;

}

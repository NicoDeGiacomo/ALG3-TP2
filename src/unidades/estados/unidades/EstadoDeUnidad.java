package unidades.estados.unidades;

public interface EstadoDeUnidad {
    boolean esMapleable();

    default EstadoDeUnidad aumentarTurnoDeConstruccion(){
        return this;
    }


    default boolean estaHabilitado(){
        return true;
    }
}

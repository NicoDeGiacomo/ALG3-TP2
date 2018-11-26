package unidades.estados.unidades;

public interface EstadoDeUnidad {
    boolean esMapleable();

    default boolean estaHabilitado(){
        return true;
    }
}

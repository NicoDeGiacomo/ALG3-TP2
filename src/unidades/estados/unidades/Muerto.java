package unidades.estados.unidades;

public class Muerto implements EstadoDeUnidad {
    @Override
    public boolean esMapleable() {
        return false;
    }
}

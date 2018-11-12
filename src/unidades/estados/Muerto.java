package unidades.estados;

public class Muerto implements EstadoDeUnidad {
    @Override
    public boolean esMapleable() {
        return false;
    }
}

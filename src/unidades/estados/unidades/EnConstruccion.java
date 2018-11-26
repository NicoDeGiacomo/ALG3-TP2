package unidades.estados.unidades;

public class EnConstruccion implements EstadoDeUnidad {

    @Override
    public boolean esMapleable() {
        return true;
    }

    @Override
    public boolean estaHabilitado() {
        return false;
    }

}

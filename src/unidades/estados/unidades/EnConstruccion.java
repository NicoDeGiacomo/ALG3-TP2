package unidades.estados.unidades;

public class EnConstruccion implements EstadoDeUnidad {

    int turnosConstruidos = 0;

    @Override
    public boolean esMapleable() {
        return true;
    }

    @Override
    public EstadoDeUnidad aumentarTurnoDeConstruccion(){
        turnosConstruidos++;
        if (turnosConstruidos == 3) return new Vivo();
        return this;
    }

    @Override
    public boolean estaHabilitado(){
        return false;
    }


}

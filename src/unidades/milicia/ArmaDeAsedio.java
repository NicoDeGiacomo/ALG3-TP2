package unidades.milicia;

import unidades.Unidad;

public class ArmaDeAsedio extends Milicia{
    private int danio;
    private int vida;
    private Unidad armaMontada;

    public ArmaDeAsedio(){
        vida = 150;
        danio = 75;
        armaMontada = null;
    }

    @Override
    public int verVida() {
        return vida;
    }

    @Override
    public void recibirDanio(int danio) {
        vida -= danio;
    }

    @Override
    public void provocarDanio(Unidad unidad) {
        if ( !unidad.esMovible() && armaMontada != null)  unidad.recibirDanio(danio);
    }

    public void montarArma(Unidad unidad){
        armaMontada =  unidad ;
    }

    public Unidad desmontarArma(){
        if (armaMontada != null) {
            Unidad unidad = armaMontada;
            armaMontada = null;
            return unidad;
        }
        return null;
    }

    @Override
    public void ejecutarTareas() {

    }



}















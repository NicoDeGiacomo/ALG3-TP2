package unidades.milicia;


import main.Jugador;

public class Arquero extends Milicia {

    public Arquero(Jugador propietario){
        super();
        this.propietario = propietario;
        this.vida = 75;
        this.danio = 15;
        this.danioAEdificios = 10;
        this.precio = 75;
        this.alcance = 3;
    }

    @Override
    public void ejecutarTareas() {

    }


}


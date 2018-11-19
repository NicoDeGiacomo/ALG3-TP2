package main;

import excepciones.mapa.FueraDeRangoException;
import excepciones.mapa.NoEsMovibleException;
import excepciones.mapa.PosicionOcupadaException;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.edificio.Castillo;

import java.awt.geom.Point2D;
import java.util.*;

public class Mapa {
    private static final int TAMANIO = 100;
    private Dibujable[][] mapa = new Dibujable[TAMANIO][TAMANIO];

    private int obtenerTamanioMapeable(Dibujable dibujable) {
        return (dibujable.verTamanio() < 1) ? (1) : ((int) Math.sqrt(dibujable.verTamanio()));
    }

    private boolean estaOcupado(Point2D coordenada) throws FueraDeRangoException {
        return obtenerDibujable(coordenada) != null;
    }

    public void colocarUnidad(Unidad unidad, Point2D coordenada) throws FueraDeRangoException, PosicionOcupadaException {
        if (unidad == null) return;

        int tamanioUnidad = obtenerTamanioMapeable(unidad);

        for (int i = 0; i < tamanioUnidad; i++) {
            for (int j = 0; j < tamanioUnidad; j++) {

                validarCoordenada(coordenada);

                validarCoordenada(new Point2D.Double(coordenada.getX() + i, coordenada.getY() + j));
            }
        }

        for (int i = 0; i < tamanioUnidad; i++) {
            for (int j = 0; j < tamanioUnidad; j++) {
                mapa[(int) coordenada.getX() + i][(int) coordenada.getY() + j] = unidad;
            }
        }
    }

    Dibujable obtenerDibujable(Point2D coordenada) throws FueraDeRangoException {
        validarCoordenadaEnMapa(coordenada);

        Dibujable dibujable = mapa[(int) coordenada.getX()][(int) coordenada.getY()];

        if (dibujable == null || !((Unidad) dibujable).esMapeable()) {
            quitarUnidad((Unidad) dibujable);
            return null;
        }

        return dibujable;
    }

    List<Point2D> obtenerCoordenadas(Unidad unidad) {
        List<Point2D> coordenadas = new ArrayList<>();

        if (unidad == null) return coordenadas;

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                if (mapa[i][j] == unidad) {
                    coordenadas.add(new Point2D.Double(i, j));
                }
            }
        }

        return coordenadas;
    }

    List<Point2D> obtenerCoordenadasCercanas(Unidad unidad) {
        List<Point2D> coordenadasAlRededor = new ArrayList<>();
        List<Point2D> coordenadasUnidad = obtenerCoordenadas(unidad);

        //TODO: Si no tiene coordenada en el mapa, hay que tirar excepcion? Tecnicamente no está y no va a hacer nada.
        if (unidad == null || coordenadasUnidad.size() == 0) return coordenadasAlRededor;

        Point2D coordenadaOrigen = obtenerCoordenadas(unidad).get(0);
        int alcance = unidad.verAlcance();
        int tamanio = obtenerTamanioMapeable(unidad);

        for (int i = -alcance; i <   tamanio + alcance; i++) {
            for (int j = -alcance; j < tamanio + alcance; j++) {
                Point2D chequeo = new Point2D.Double(coordenadaOrigen.getX() + i, coordenadaOrigen.getY() + j);
                if (coordenadaEnMapa(chequeo) && !(coordenadasUnidad.contains(chequeo)) ) {
                    coordenadasAlRededor.add(chequeo);
                }
            }
        }

        return coordenadasAlRededor;
    }

    boolean estaAlAlcance(Point2D origen, Point2D destino) throws FueraDeRangoException {
        validarCoordenadaEnMapa(origen);
        validarCoordenadaEnMapa(destino);

        Unidad atacante = (Unidad) obtenerDibujable(origen);
        if(atacante == null) return false;

        List<Point2D> coodenadas = obtenerCoordenadasCercanas(atacante);
        return coodenadas.contains(destino);
    }

    List<Dibujable> dibujablesAlAlcance(Unidad unidad) {
        List<Dibujable> unidades = new ArrayList<>();
        if (unidad == null) return unidades;

        List<Point2D> coordenadasCercanas = obtenerCoordenadasCercanas(unidad);

        for (Point2D coordenadasCercana : coordenadasCercanas) {
            try {
                if (!(unidades.contains(obtenerDibujable(coordenadasCercana))) && obtenerDibujable(coordenadasCercana) != null) {
                    unidades.add(obtenerDibujable(coordenadasCercana));
                }
            } catch (FueraDeRangoException ignored) {
            }
        }

        return unidades;
    }

    private List<Castillo> encontrarCastillos() {
        List<Castillo> castillos = new ArrayList<>();

        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                Point2D coordenada = new Point2D.Double(i, j);
                Dibujable buffer = null;

                try {
                    buffer = obtenerDibujable(coordenada);
                } catch (FueraDeRangoException ignored) {
                }

                if ((buffer != null) && (buffer.getClass() == Castillo.class) && (!castillos.contains(buffer))) {
                    castillos.add((Castillo) buffer);
                }
            }
        }

        return castillos;
    }

    void colocarUnidadesEnExtremo(Unidad castillo, Unidad plazaCentral) {
        if (castillo == null) return;

        List<Castillo> castillos = encontrarCastillos();
        int xOrigen,
                yOrigen,
                xDestinoCastillo,
                yDestinoCastillo,
                xDestinoPlaza,
                yDestinoPlaza,
                cuadranteOrigen,
                cuadranteDestino;
        Random random = new Random();

        if (castillos.size() > 1) {
            return;
        } else if (castillos.size() == 1) {
            Point2D origenCastillo = obtenerCoordenadas(castillos.get(0)).get(0);
            xOrigen = (int) origenCastillo.getX();
            yOrigen = (int) origenCastillo.getY();

            if ((xOrigen < TAMANIO / 2) && (yOrigen >= TAMANIO / 2)) {
                cuadranteOrigen = 2;
            } else if (yOrigen < TAMANIO / 2) {
                cuadranteOrigen = 1;
            } else {
                cuadranteOrigen = 3;
            }

            cuadranteDestino = (cuadranteOrigen % 2) == 0 ? (cuadranteOrigen + 3) % 4 : (cuadranteOrigen + 1) % 4;
        } else {
            cuadranteDestino = random.nextInt(4);
        }

        xDestinoCastillo = random.nextInt(TAMANIO / 2) + ((TAMANIO / 2) * (cuadranteDestino % 2));
        yDestinoCastillo = random.nextInt(TAMANIO / 2) - ((TAMANIO / 2) * ((cuadranteDestino % 2 - (cuadranteDestino > 1 ? cuadranteDestino - 1 : cuadranteDestino))));

        xDestinoCastillo = xDestinoCastillo / 2 + 8 >= TAMANIO / 2 ? xDestinoCastillo - 8 : xDestinoCastillo;
        yDestinoCastillo = yDestinoCastillo / 2 + 8 >= TAMANIO / 2 ? yDestinoCastillo - 8 : yDestinoCastillo;

        xDestinoPlaza = cuadranteDestino % 2 == 0 ? xDestinoCastillo + 8 : xDestinoCastillo - 8;
        yDestinoPlaza = cuadranteDestino - 2 < 0 ? yDestinoCastillo + 8 : yDestinoCastillo - 8;

        Point2D coordenadaCastillo = new Point2D.Double(xDestinoCastillo, yDestinoCastillo),
                coordenadaPlaza = new Point2D.Double(xDestinoPlaza, yDestinoPlaza);

        try {
            colocarUnidad(castillo, coordenadaCastillo);
            colocarUnidad(plazaCentral, coordenadaPlaza);
        } catch (FueraDeRangoException | PosicionOcupadaException ignored) {
        }
    }

    public void agregarUnidadCercana(Unidad unidad, Unidad creador) {
        if(creador == null) return;
        List<Point2D> lugaresLibres = obtenerCoordenadasCercanas(creador);
        Iterator<Point2D> iter = lugaresLibres.iterator();

        while (iter.hasNext()) {
            Dibujable dibujable = null;
            try {
                dibujable = obtenerDibujable(iter.next());
            } catch (FueraDeRangoException ignored) {}

            if (dibujable != null)
                iter.remove();
        }

        if(lugaresLibres.size() == 0) {
            //TODO: Revisar esto!
            return;//throw new PosicionOcupadaException("No hay espacio para crear una nueva Unidad!");
        }

        Random random = new Random();

        try {
            colocarUnidad(unidad, lugaresLibres.get(random.nextInt(lugaresLibres.size())));
        } catch (FueraDeRangoException | PosicionOcupadaException ignored) {
        }
    }

    public void agregarUnidadCercana(Unidad unidad, Unidad creador, Point2D coordenadaDestino) throws FueraDeRangoException, PosicionOcupadaException {
        validarCoordenada(coordenadaDestino);

        boolean estaCerca = false;

        List<Point2D> coordenadas = obtenerCoordenadas(unidad); //TODO: GASTI - FALLAR SI LA UNIDAD CREADORA NO ESTA AGREGADA

        for (Point2D coordenada : coordenadas) {
            if (Math.floor(coordenada.distance(coordenadaDestino)) <= 1) {
                estaCerca = true;
            }
        }

        if(estaCerca) {
            colocarUnidad(creador, coordenadaDestino);
        }
    }

    void moverUnidad(Unidad unidad, Point2D destino) throws NoEsMovibleException, FueraDeRangoException, PosicionOcupadaException {
        if (unidad == null || !unidad.esMovible()) {
            throw new NoEsMovibleException("La Unidad que se trata de mover no es Movible!");
        }

        if (estaOcupado(destino)) {
            throw new PosicionOcupadaException("La coordenada de Destino ya se encuentra ocupada!");
        }

        Point2D origen = obtenerCoordenadas(unidad).get(0);

        if (Math.floor(origen.distance(destino)) > 1) {
            throw new FueraDeRangoException("Las Unidades se pueden mover a lo sumo 1 casillero por turno!");
        }

        quitarUnidad(unidad);
        colocarUnidad(unidad, destino);
    }

    void quitarUnidad(Unidad unidad) {
        List<Point2D> coordenadas = obtenerCoordenadas(unidad);

        for (Point2D coordenada : coordenadas) {
            mapa[(int) coordenada.getX()][(int) coordenada.getY()] = null;
        }
    }

    private void validarCoordenada(Point2D coordenada) throws FueraDeRangoException, PosicionOcupadaException {
        validarCoordenadaEnMapa(coordenada);

        if (estaOcupado(coordenada)) {
            throw new PosicionOcupadaException("Ya existe una Unidad en (" + (coordenada.getX()) + ", " + (coordenada.getY()) + ")");
        }
    }

    private void validarCoordenadaEnMapa(Point2D coordenada) throws FueraDeRangoException {
        if(coordenada == null) {
            throw new FueraDeRangoException("No se puede validar una coordenada NULL!");
        }

        if (!coordenadaEnMapa(coordenada)) {
            throw new FueraDeRangoException("Posición (" + coordenada.getX() + ", " + coordenada.getY() + ") fuera del Margen del Mapa!");
        }
    }

    private boolean coordenadaEnMapa(Point2D coordenada) {
        return (!(coordenada.getX() >= TAMANIO)) && (!(coordenada.getY() >= TAMANIO)) && (!(coordenada.getX() < 0)) && (!(coordenada.getY() < 0));
    }


}

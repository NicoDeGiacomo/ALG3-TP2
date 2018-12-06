package ui;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.mapa.UnidadNoMovibleException;
import excepciones.unidades.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Mapa;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.edificios.Castillo;
import unidades.edificios.Cuartel;
import unidades.edificios.Edificio;
import unidades.edificios.PlazaCentral;
import unidades.milicias.Aldeano;
import unidades.milicias.ArmaDeAsedio;
import unidades.milicias.Arquero;
import unidades.milicias.Espadachin;

import java.awt.geom.Point2D;

public class Menu {

    static private boolean answer = false;
    static private Point2D posAnswer;

    static void mostarVida(Unidad unidad) {
        Stage window = new Stage();

        mostrarMenu(window, "Unidad enemiga", unidad.verVida());
    }

    public static boolean mostrarMenuDeCastillo(Castillo castillo) {
        Stage window = new Stage();

        Button crearUnidad = crearBotonDeCreacion(castillo, window);

        Button destruir = crearBotonDeDestruccion(castillo, window);

        mostrarMenu(window, "Menu de Castillo", castillo.verVida(), crearUnidad, destruir);
        return answer;
    }

    public static boolean mostrarMenuDePlazaCentral(PlazaCentral plazaCentral) {
        Stage window = new Stage();

        Button crearUnidad = crearBotonDeCreacion(plazaCentral, window);

        Button destruir = crearBotonDeDestruccion(plazaCentral, window);

        return mostrarMenu(window, "Menu de Plaza Central", plazaCentral.verVida(), crearUnidad, destruir);
    }

    public static boolean mostrarMenuDeCuartel(Cuartel cuartel) {
        Stage window = new Stage();

        Button crearArquero = new Button("Crear Arquero");
        crearArquero.setOnAction(e -> {
            try {
                cuartel.crearArquero();
                answer = true;
            } catch (ErrorDeConstruccionException error) {
                Alerta.displayError("Error al Crear la Unidad.", error.getMessage());
                answer = false;
            }
            window.close();
        });

        Button crearEspadachin = new Button("Crear Espadachín");
        crearEspadachin.setOnAction(e -> {
            try {
                cuartel.crearEspadachin();
                answer = true;
            } catch (ErrorDeConstruccionException error) {
                Alerta.displayError("Error al Crear la Unidad.", error.getMessage());
                answer = false;
            }
            window.close();
        });

        Button destruir = crearBotonDeDestruccion(cuartel, window);

        return mostrarMenu(window, "Menu de Cuartel", cuartel.verVida(), crearArquero, crearEspadachin, destruir);
    }

    public static boolean mostrarMenuDeAldeano(Aldeano aldeano, Point2D point2D) {
        Stage window = new Stage();

        Button crearCuartel = new Button("Construir Cuartel");
        crearCuartel.setOnAction(e -> {
            Point2D coordenada = mostrarGrillaDeCoordenadas(point2D, aldeano.verAlcance());
            if (coordenada == null) {
                answer = false;
                return;
            }

            try {
                aldeano.construir(new Cuartel(aldeano.obtenerPropietario()), coordenada);
                answer = true;
            } catch (AldeanoOcupadoException | OroInsuficienteException | CreacionDeCastilloException | CoordenadaInvalidaException error) {
                Alerta.displayError("Error al Crear Edificio", error.getMessage());
                answer = false;
            }
            window.close();
        });

        Button crearPlazaCentral = new Button("Construir Plaza Central");
        crearPlazaCentral.setOnAction(e -> {
            Point2D coordenada = mostrarGrillaDeCoordenadas(point2D, aldeano.verVelocidad());
            if (coordenada == null) {
                answer = false;
                return;
            }

            try {
                aldeano.construir(new PlazaCentral(aldeano.obtenerPropietario()), coordenada);
                answer = true;
            } catch (AldeanoOcupadoException | OroInsuficienteException | CreacionDeCastilloException | CoordenadaInvalidaException error) {
                Alerta.displayError("Error al Crear Edificio", error.getMessage());
                answer = false;
            }
            window.close();
        });

        Button moverAldeano = crearBotonDeMovimiento(aldeano, point2D, window);

        Button destruir = crearBotonDeDestruccion(aldeano, window);

        return mostrarMenu(window, "Menu de Aldeano", aldeano.verVida(), crearCuartel, crearPlazaCentral, moverAldeano, destruir);
    }

    public static boolean mostrarMenuDeArquero(Arquero arquero, Point2D point2D) {
        Stage window = new Stage();

        Button moverArquero = crearBotonDeMovimiento(arquero, point2D, window);

        Button atacarUnidad = crearBotonDeAtaque(arquero, point2D, window);

        Button destruir = crearBotonDeDestruccion(arquero, window);

        return mostrarMenu(window, "Menu de Arquero", arquero.verVida(), moverArquero, atacarUnidad, destruir);
    }

    public static boolean mostrarMenuDeEspadachin(Espadachin espadachin, Point2D point2D) {
        Stage window = new Stage();

        Button moverEspadachin = crearBotonDeMovimiento(espadachin, point2D, window);

        Button atacarUnidad = crearBotonDeAtaque(espadachin, point2D, window);

        Button destruir = crearBotonDeDestruccion(espadachin, window);

        return mostrarMenu(window, "Menu de Espadachín", espadachin.verVida(), moverEspadachin, atacarUnidad, destruir);
    }

    public static boolean mostrarMenuDeArmaDeAsedio(ArmaDeAsedio armaDeAsedio, Point2D point2D) {
        Stage window = new Stage();

        Button moverArmaDeAsedio = crearBotonDeMovimiento(armaDeAsedio, point2D, window);

        Button atacarUnidad = crearBotonDeAtaque(armaDeAsedio, point2D, window);

        Button montar = new Button("Montar");
        montar.setOnAction(e -> {
            try {
                armaDeAsedio.montarArma();
                answer = true;
            } catch (ArmaDeAsedioYaMontadaException error) {
                Alerta.displayError("El Arma de Asedio ya está montada!", error.getMessage());
                answer = false;
            }
            window.close();
        });

        Button desmontar = new Button("Desmontar");
        desmontar.setOnAction(e -> {
            try {
                armaDeAsedio.desmontarArma();
                answer = true;
            } catch (ArmaDeAsedioYaDesmontadaException error) {
                Alerta.displayError("El Arma de Asedio ya está desmontada!", error.getMessage());
                answer = false;
            }
            window.close();
        });

        Button destruir = crearBotonDeDestruccion(armaDeAsedio, window);

        return mostrarMenu(window, "Menu de Arma de Asedio", armaDeAsedio.verVida(), moverArmaDeAsedio, atacarUnidad, montar, desmontar, destruir);
    }

    private static Point2D mostrarGrillaDeCoordenadas(Point2D point2D, int distancia) {
        answer = false;
        posAnswer = null;
        Stage window = new Stage();

        GridPane grid = new GridPane();
        Pantalla.addStyleClass(grid, "grid-pane");

        for (int i = -distancia; i <= distancia; i++) {
            for (int j = -distancia; j <= distancia; j++) {
                Point2D show = new Point2D.Double(point2D.getX() + i, point2D.getY() + j);
                String valor = String.format("(%d; %d)", (long) show.getX() + 1, (long) show.getY() + 1);

                Button button = new Button(valor);
                Pantalla.addStyleClass(button, "boton-coord");
                button.setOnAction(e -> {
                    posAnswer = show;
                    window.close();
                });

                try {
                    Mapa.validarCoordenadaEnMapa(show);
                    Dibujable dibujable = Mapa.obtenerInstancia().obtenerDibujable(show);
                    if (i == 0 && j == 0)
                        button.setDisable(true);
                    else if (dibujable != null)
                        button.setStyle(String.format("-fx-background-color: %s;", ((Unidad) dibujable).obtenerPropietario().obtenerColor().toString().replace("0x", "")));

                    button.setAlignment(Pos.CENTER);
                    grid.add(button, i + distancia, j + distancia);
                } catch (CoordenadaInvalidaException ignored) {
                }
            }
        }

        ScrollPane scrollPane = new ScrollPane(grid);
        Scene scene = new Scene(scrollPane);
        Pantalla.addStyleSheet(scene);
        window.setScene(scene);
        window.showAndWait();

        return posAnswer;
    }

    static private boolean mostrarMenu(Stage window, String titulo, int vida, Button... buttons) {
        answer = false;

        Label vidaActual = new Label(String.format("Vida: %s", vida));


        VBox layout = new VBox(10);
        layout.getChildren().add(vidaActual);
        layout.setAlignment(Pos.CENTER);

        if (buttons != null && buttons.length != 0) {
            Label label = new Label();
            label.setText("Elija la Acción a realizar:");
            layout.getChildren().add(label);
            for (Button button : buttons) {
                layout.getChildren().add(button);
            }
        }

        Scene scene = new Scene(layout);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(titulo);
        window.setMinWidth(300);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

    private static Button crearBotonDeMovimiento(Unidad unidad, Point2D point2D, Stage window) {
        answer = false;
        Button mover = new Button("Mover Unidad");
        mover.setOnAction(e -> {
            Point2D coordenada = mostrarGrillaDeCoordenadas(point2D, unidad.verVelocidad());
            if (coordenada == null) {
                answer = false;
                return;
            }

            try {
                Mapa.obtenerInstancia().moverUnidad(unidad, coordenada);
                Sonido.reproducirSonido(unidad.obtenerSonidoDeMovimiento());
                answer = true;
            } catch (UnidadNoMovibleException | CoordenadaInvalidaException error) {
                Alerta.displayError("Error al mover la Unidad.", error.getMessage());
                answer = false;
            }

            window.close();
        });

        return mover;
    }

    private static Button crearBotonDeAtaque(Unidad unidad, Point2D point2D, Stage window) {
        answer = false;
        Button atacar = new Button("Atacar");
        atacar.setOnAction(e -> {
            Point2D coordenada = mostrarGrillaDeCoordenadas(point2D, unidad.verAlcance());
            if (coordenada == null) {
                answer = false;
                return;
            }

            try {
                Unidad atacada = (Unidad) Mapa.obtenerInstancia().obtenerDibujable(coordenada);
                if (atacada == null)
                    throw new AtaqueIncorrectoException("No hay una Unidad Enemiga en esa Coordenada!");

                unidad.provocarDanio(atacada);
                Sonido.reproducirSonido(unidad.obtenerSonidoDeAtaque());
                if (atacada.verVida() <= 0)
                    Sonido.reproducirSonido(unidad.obtenerSonidoDeMuerte());
                answer = true;
            } catch (AtaqueIncorrectoException | CoordenadaInvalidaException error) {
                Alerta.displayError("Error al Atacar.", error.getMessage());
                answer = false;
            }

            window.close();
        });

        return atacar;
    }

    private static Button crearBotonDeCreacion(Edificio edificio, Stage window) {
        answer = false;
        Button crearUnidad = new Button("Crear Unidad");
        crearUnidad.setOnAction(e -> {
            try {
                Sonido.reproducirSonido(edificio.crearUnidad().obtenerSonidoDeCreacion());
                answer = true;
            } catch (ErrorDeConstruccionException | OroInsuficienteException | UnidadNoEspecificadaException | LimiteDePoblacionException | CoordenadaInvalidaException error) {
                Alerta.displayError("Error al crear la Unidad.", error.getMessage());
                answer = false;
            }
            window.close();
        });

        return crearUnidad;
    }

    private static Button crearBotonDeDestruccion(Unidad unidad, Stage window) {
        answer = false;

        Button destruirUnidad = new Button("Destruir");
        destruirUnidad.setOnAction(e -> {
            Sonido.reproducirSonido(unidad.obtenerSonidoDeMuerte());
            unidad.destruir();
            window.close();
            answer = true;
        });

        return destruirUnidad;
    }
}

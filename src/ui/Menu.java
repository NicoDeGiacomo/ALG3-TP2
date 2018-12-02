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
import unidades.Unidad;
import unidades.edificios.Castillo;
import unidades.edificios.Cuartel;
import unidades.edificios.Edificio;
import unidades.edificios.PlazaCentral;
import unidades.milicias.*;

import java.awt.geom.Point2D;

public class Menu {

    static private boolean answer = false;
    static private Point2D posAnswer;

    public static boolean mostrarMenuDeCastillo(Castillo castillo) {
        Stage window = new Stage();

        Button crearUnidad = crearBotonDeCreacion(castillo, window);

        Button destruirCastillo = crearBotonDeDestruccion(castillo, window);

        mostrarMenu(window, "Menu de Castillo", castillo.verVida(), crearUnidad, destruirCastillo);
        return answer;
    }

    public static boolean mostrarMenuDePlazaCentral(PlazaCentral plazaCentral) {
        Stage window = new Stage();

        Button crearUnidad = crearBotonDeCreacion(plazaCentral, window);

        Button destruirPlazaCentral = crearBotonDeDestruccion(plazaCentral, window);

        return mostrarMenu(window, "Menu de Plaza Central", plazaCentral.verVida(), crearUnidad, destruirPlazaCentral);
    }

    public static boolean mostrarMenuDeCuartel(Cuartel cuartel) {
        Stage window = new Stage();

        Button crearArquero = new Button("Crear Arquero");
        crearArquero.setOnAction(e -> {
            try {
                cuartel.crearArquero();
                answer = true;
            } catch (ErrorDeConstruccionException error) {
                Alerta.displayError("Error al crear la unidad", error.getMessage());
                answer = false;
            }
            window.close();
        });

        Button crearEspadachin = new Button("Crear Espadachin");
        crearEspadachin.setOnAction(e -> {
            try {
                cuartel.crearEspadachin();
                answer = true;
            } catch (ErrorDeConstruccionException error) {
                Alerta.displayError("Error al crear la unidad", error.getMessage());
                answer = false;
            }
            window.close();
        });

        Button destruirCuartel = crearBotonDeDestruccion(cuartel, window);

        return mostrarMenu(window, "Menu de Cuartel", cuartel.verVida(), crearArquero, crearEspadachin, destruirCuartel);
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
                Alerta.displayError("Error al crear edificios", error.getMessage());
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
                Alerta.displayError("Error al crear edificios", error.getMessage());
                answer = false;
            }
            window.close();
        });

        Button moverAldeano = crearBotonDeMovimiento(aldeano, point2D, window);

        Button destruirAldeano = crearBotonDeMuerte(aldeano, window);

        return mostrarMenu(window, "Menu de Aldeano", aldeano.verVida(), crearCuartel, crearPlazaCentral, moverAldeano, destruirAldeano);
    }

    public static boolean mostrarMenuDeArquero(Arquero arquero, Point2D point2D) {
        Stage window = new Stage();

        Button moverArquero = crearBotonDeMovimiento(arquero, point2D, window);

        Button atacarUnidad = crearBotonDeAtaque(arquero, point2D, window);

        Button destruirArquero = crearBotonDeMuerte(arquero, window);

        return mostrarMenu(window, "Menu de Arquero", arquero.verVida(), moverArquero, atacarUnidad, destruirArquero);
    }

    public static boolean mostrarMenuDeEspadachin(Espadachin espadachin, Point2D point2D) {
        Stage window = new Stage();

        Button moverEspadachin = crearBotonDeMovimiento(espadachin, point2D, window);

        Button atacarUnidad = crearBotonDeAtaque(espadachin, point2D, window);

        Button destruirEspadachin = crearBotonDeMuerte(espadachin, window);

        return mostrarMenu(window, "Menu de Espadachin", espadachin.verVida(), moverEspadachin, atacarUnidad, destruirEspadachin);
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
                Alerta.displayError("El arma ya esta montada.", error.getMessage());
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
                Alerta.displayError("El arma ya esta desmontada.", error.getMessage());
                answer = false;
            }
            window.close();
        });

        Button destruirArmaDeAsedio = crearBotonDeMuerte(armaDeAsedio, window);

        return mostrarMenu(window, "Menu de Arma de Asedio", armaDeAsedio.verVida(), moverArmaDeAsedio, atacarUnidad, montar, desmontar, destruirArmaDeAsedio);
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
                    grid.add(button, i + distancia, j + distancia);
                } catch (CoordenadaInvalidaException ignored) {
                }

                if (i == 0 && j == 0) {
                    button.setDisable(true);
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
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(titulo);
        window.setMinWidth(300);
        Label label = new Label();
        label.setText("Elija la acción a realizar:");
        VBox layout = new VBox(10);

        Label vidaActual = new Label(String.format("Vida: %s", vida));

        layout.getChildren().add(vidaActual);
        layout.getChildren().add(label);
        for (Button button : buttons) {
            layout.getChildren().add(button);
        }
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

    private static Button crearBotonDeMovimiento(Unidad unidad, Point2D point2D, Stage window) {
        answer = false;
        Button mover = new Button("Mover unidad");
        mover.setOnAction(e -> {
            Point2D coordenada = mostrarGrillaDeCoordenadas(point2D, unidad.verVelocidad());
            if (coordenada == null) {
                answer = false;
                return;
            }

            try {
                unidad.moverUnidad(coordenada);
                Sonido.reproducirSonido(unidad.obtenerSonidoDeMovimiento());
                answer = true;
            } catch (UnidadNoMovibleException | CoordenadaInvalidaException error) {
                Alerta.displayError("Error al mover la unidad", error.getMessage());
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
                unidad.atacarUnidad(coordenada);
                Sonido.reproducirSonido(unidad.obtenerSonidoDeAtaque());
                answer = true;
                if (atacada.verVida() <= 0)
                    Sonido.reproducirSonido(unidad.obtenerSonidoDeMuerte());
            } catch (AtaqueIncorrectoException | CoordenadaInvalidaException error) {
                Alerta.displayError("Error al atacar", error.getMessage());
                answer = false;
            }

            window.close();
        });

        return atacar;
    }

    private static Button crearBotonDeCreacion(Edificio edificio, Stage window) {
        answer = false;
        Button crearUnidad = new Button("Crear unidad");
        crearUnidad.setOnAction(e -> {
            try {
                Sonido.reproducirSonido(edificio.crearUnidad().obtenerSonidoDeCreacion());
                answer = true;
            } catch (ErrorDeConstruccionException | OroInsuficienteException | UnidadNoEspecificadaException | LimiteDePoblacionException | CoordenadaInvalidaException error) {
                Alerta.displayError("Error al crear la unidad", error.getMessage());
                answer = false;
            }
            window.close();
        });

        return crearUnidad;
    }

    private static Button crearBotonDeMuerte(Milicia unidad, Stage window) {
        answer = false;

        Button destruirUnidad = new Button("Destruir");
        destruirUnidad.setOnAction(e -> {
            Sonido.reproducirSonido(unidad.obtenerSonidoDeMuerte());
            unidad.obtenerPropietario().removerUnidad(unidad);
            window.close();
            answer = true;
        });

        return destruirUnidad;
    }

    private static Button crearBotonDeDestruccion(Edificio unidad, Stage window) {
        answer = false;

        Button destruirUnidad = new Button("Destruir");
        destruirUnidad.setOnAction(e -> {
            Sonido.reproducirSonido(unidad.obtenerSonidoDeMuerte());
            unidad.obtenerPropietario().removerUnidad(unidad);
            window.close();
            answer = true;
        });

        return destruirUnidad;
    }

}

package ui;

import excepciones.main.ComienzoDePartidaException;
import excepciones.main.NombreRepetidoException;
import excepciones.main.NumeroDeJugadoresException;
import excepciones.mapa.CoordenadaInvalidaException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.AlgoEmpires;
import main.Mapa;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.edificio.Castillo;
import unidades.edificio.PlazaCentral;

import java.awt.geom.Point2D;
import java.util.List;

public class Pantalla extends Application {

    private Mapa mapa;
    private AlgoEmpires algoEmpires;

    private GridPane gridPane;
    private Scene menuPrincipal, menuDeJuego;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        this.stage.setTitle("Algo Empires");

        this.menuPrincipal = crearMenuPrincipal();
        this.menuDeJuego = crearMenuDeJuego();

        this.stage.setScene(this.menuPrincipal);
        this.menuDeJuego.getStylesheets().add("style.css");
        this.menuPrincipal.getStylesheets().add("style.css");
        this.stage.show();
    }

    private Scene crearMenuDeJuego() {
        this.gridPane = new GridPane();
        this.gridPane.getStyleClass().add("grid-pane");

        return new Scene(this.gridPane, 850, 850);
    }

    private Scene crearMenuPrincipal() {
        Label label = new Label("Ingrese los nombres de los jugadores:");
        label.getStyleClass().add("label");

        TextField nombre1 = new TextField();
        TextField nombre2 = new TextField();

        Button button = new Button("Comenzar juego");

        VBox layout = new VBox();
        layout.getStyleClass().add("vbox");
        layout.getChildren().addAll(label, nombre1, nombre2, button);

        button.setOnAction(e -> {
            try {
                crearJuego(nombre1.getText(), nombre2.getText());
            } catch (NombreRepetidoException | NumeroDeJugadoresException | ComienzoDePartidaException error) {
                Alerta.display("Error al crear partida", error.getMessage());
                return;
            }
            stage.setScene(menuDeJuego);
            actualizarMapa();
        });

        return new Scene(layout, 850, 850);
    }

    private void actualizarMapa() {
        //Pinto el mapa entero de verde
        for (int i = 0; i <= Mapa.TAMANIO; i++) {
            for (int j = 0; j <= Mapa.TAMANIO; j++) {
                Rectangle rectangle = new Rectangle(15, 15);
                rectangle.setFill(Color.GREEN);
                this.gridPane.add(rectangle, i, j);
            }
        }

        //Pinto las unidades de rojo -> Para mas tarde que Dibujable implemente un metodo OBTENERCOLOR y llamarlo
        for (Dibujable dibujable : this.mapa.obtenerTodosLosDibujables()) {
            try {
                List<Point2D> point2DList = this.mapa.obtenerCoordenadas((Unidad) dibujable); //TODO: Este cast es feo -> El mapa deberia recibir un dibujable ?

                for (Point2D point2D : point2DList) {
                    Rectangle rectangle = new Rectangle(15, 15);
                    rectangle.setFill(dibujable.obtenerColor());
                    //rectangle.setOnMouseClicked(e -> mostrarMenuDeOpciones(point2D));

                    Text text = new Text(((Unidad) dibujable).obtenerPropietario().verNombre().substring(0, 1));
                    text.setFont(Font.font("Verdana", 10));

                    StackPane stack = new StackPane();
                    stack.getChildren().addAll(rectangle, text);
                    stack.setOnMouseClicked(e -> mostrarMenuDeOpciones(point2D));
                    this.gridPane.add(stack, (int) point2D.getX(), (int) point2D.getY());
                }

            } catch (CoordenadaInvalidaException ignore) {
            }
        }
    }

    private void mostrarMenuDeOpciones(Point2D point2D) {
        try {
            Dibujable dibujable = this.mapa.obtenerDibujable(point2D);
            if (!((Unidad) dibujable).unidadesSonDelMismoEquipo(this.algoEmpires.obtenerJugadorEnTurno()))
                return;

            boolean success = false;
            if (dibujable.getClass() == Castillo.class) {
                success = Menu.mostrarMenuDeCastillo((Castillo) dibujable);
            } else if (dibujable.getClass() == PlazaCentral.class) {
                success = Menu.mostrarMenuDePlazaCentral((PlazaCentral) dibujable);
            }

            if (success) {
                pasarTurno();
                actualizarMapa();
            }
        } catch (CoordenadaInvalidaException ignore) {
        }
    }

    private void pasarTurno() {
        try {
            this.algoEmpires.pasarTurno();
            Alerta.display("Proximo turno", String.format("Le toca al jugador: %s", this.algoEmpires.obtenerJugadorEnTurno().verNombre()));
        } catch (ComienzoDePartidaException ignore) {
        }
    }

    private void crearJuego(String jugador1, String jugador2) throws NombreRepetidoException, NumeroDeJugadoresException, ComienzoDePartidaException {
        this.algoEmpires = new AlgoEmpires();

        this.algoEmpires.agregarJugador(jugador1);
        this.algoEmpires.agregarJugador(jugador2);

        this.mapa = this.algoEmpires.comenzarPartida();
    }
}

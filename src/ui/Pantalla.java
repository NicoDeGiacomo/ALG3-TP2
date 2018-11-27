package ui;

import excepciones.main.ComienzoDePartidaException;
import excepciones.main.NombreRepetidoException;
import excepciones.main.NumeroDeJugadoresException;
import excepciones.mapa.CoordenadaInvalidaException;
import javafx.application.Application;
import javafx.scene.Node;
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
import main.Jugador;
import main.Mapa;
import unidades.Dibujable;
import unidades.Unidad;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class Pantalla extends Application {

    private Mapa mapa;
    private AlgoEmpires algoEmpires;
    private List<Dibujable> dibujablesUsados;

    private final int TAMANIO_VENTANA = 600;
    private final int TAMANIO_CELDA = (TAMANIO_VENTANA / mapa.TAMANIO) - 3;

    private GridPane gridPane;
    private Label infoLabel;
    private Scene menuDeJuego;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.dibujablesUsados = new LinkedList<>();
        this.stage = primaryStage;
        this.stage.setTitle("Algo Empires");

        Scene menuPrincipal = crearMenuPrincipal();
        this.menuDeJuego = crearMenuDeJuego();

        this.stage.setScene(menuPrincipal);
        this.menuDeJuego.getStylesheets().add("style.css");
        menuPrincipal.getStylesheets().add("style.css");
        this.stage.show();
    }

    private Scene crearMenuDeJuego() {
        this.gridPane = new GridPane();
        addStyle(this.gridPane, "grid-pane");

        this.infoLabel = new Label("");
        addStyle(this.infoLabel, "game-info");

        Button botonPasarTurno = new Button("Terminar turno");
        botonPasarTurno.setOnAction(e -> this.pasarTurno());

        VBox layout = new VBox();
        addStyle(layout, "vbox");
        layout.getChildren().addAll(this.infoLabel, this.gridPane, botonPasarTurno);

        return new Scene(layout, TAMANIO_VENTANA, TAMANIO_VENTANA);
    }

    private Scene crearMenuPrincipal() {
        Label label = new Label("Ingrese los nombres de los jugadores:");
        addStyle(label, "label");

        TextField nombre1 = new TextField("Jugador");
        TextField nombre2 = new TextField("Player");

        Button button = new Button("Comenzar juego");

        VBox layout = new VBox();
        addStyle(layout, "vbox");
        layout.getChildren().addAll(label, nombre1, nombre2, button);

        button.setOnAction(e -> {
            try {
                crearJuego(nombre1.getText(), nombre2.getText());
            } catch (NombreRepetidoException | NumeroDeJugadoresException | ComienzoDePartidaException error) {
                Alerta.display("Error al crear partida", error.getMessage());
                return;
            }
            stage.setScene(menuDeJuego);
            actualizarPantalla();
        });

        return new Scene(layout, TAMANIO_VENTANA, TAMANIO_VENTANA);
    }

    private void actualizarPantalla() {
        Jugador jugadorEnTurno = this.algoEmpires.obtenerJugadorEnTurno();
        this.infoLabel.setText(String.format("JUGADOR: %s | ORO: %d | POBLACION: %d", jugadorEnTurno.verNombre(), jugadorEnTurno.verOro(), jugadorEnTurno.verPoblacion()));


        //Pinto el mapa entero de verde

        for (int i = 0; i < Mapa.TAMANIO; i++) {
            for (int j = 0; j < Mapa.TAMANIO; j++) {
                Rectangle rectangle = new Rectangle(TAMANIO_CELDA, TAMANIO_CELDA);
                rectangle.setFill(Color.GREEN);
                this.gridPane.add(rectangle, i, j);
            }
        }

        for (Dibujable dibujable : this.mapa.obtenerTodosLosDibujables()) {
            try {
                List<Point2D> point2DList = this.mapa.obtenerCoordenadas(dibujable);

                for (Point2D point2D : point2DList) {
                    Rectangle rectangle = new Rectangle(TAMANIO_CELDA, TAMANIO_CELDA);
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
            if (!((Unidad) dibujable).unidadesSonDelMismoEquipo(this.algoEmpires.obtenerJugadorEnTurno()) || this.dibujablesUsados.contains(dibujable))
                return;

            if (dibujable.mostrarMenu(point2D)) {
                this.dibujablesUsados.add(dibujable);
                actualizarPantalla();
            }
        } catch (CoordenadaInvalidaException ignore) {
        }
    }

    private void pasarTurno() {
        this.dibujablesUsados.clear();
        try {
            this.algoEmpires.pasarTurno();
            this.actualizarPantalla();
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

    private void addStyle(Node node, String style) {
        node.getStyleClass().clear();
        node.getStyleClass().add(style);
    }

}

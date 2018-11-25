package ui;

import excepciones.main.ComienzoDePartidaException;
import excepciones.main.NombreRepetidoException;
import excepciones.main.NumeroDeJugadoresException;
import excepciones.mapa.CoordenadaInvalidaException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Algo Empires");

        /*Primero aparece una Scene para agregar dos jugadores y luego la Scene que es el juego principal*/
        Button button = new Button("OK");
        VBox layout = new VBox();
        layout.getStyleClass().add("vbox");

        TextField nombre1 = new TextField();
        TextField nombre2 = new TextField();
        Label label = new Label("Ingrese los nombres de los jugadores:");
        label.getStyleClass().add("label");
        layout.getChildren().addAll(label, nombre1, nombre2, button);

        Scene crearJugadores = new Scene(layout, 800, 600);
        crearJugadores.getStylesheets().add("style.css");
        primaryStage.setScene(crearJugadores);
        primaryStage.show();


        this.gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(1);
        gridPane.setHgap(1);
        gridPane.setGridLinesVisible(true);

        Scene juego = new Scene(gridPane, 1000, 1000);
        //button.setOnAction(e -> primaryStage.setScene(juego));
        button.setOnAction(e -> {
            try {
                crearJuego(nombre1.getText(), nombre2.getText());
            } catch (NombreRepetidoException | NumeroDeJugadoresException | ComienzoDePartidaException error) {
                Alerta.display("Error al crear partida", error.getMessage());
                return;
            }
            primaryStage.setScene(juego);
            actualizarMapa();
        });

    }

    private void actualizarMapa() {
        //Pinto el mapa entero de verde
        for (int i = 0; i <= Mapa.TAMANIO; i++) {
            for (int j = 0; j <= Mapa.TAMANIO; j++) {
                Rectangle rectangle = new Rectangle(15, 15);
                rectangle.setFill(Color.GREEN);
                gridPane.add(rectangle, i, j);
            }
        }

        //Pinto las unidades de rojo -> Para mas tarde que Dibujable implemente un metodo OBTENERCOLOR y llamarlo
        for (Dibujable dibujable : this.mapa.obtenerTodosLosDibujables()) {
            try {
                List<Point2D> point2DList = this.mapa.obtenerCoordenadas((Unidad) dibujable); //TODO: Este cast es feo -> El mapa deberia recibir un dibujable ?

                for (Point2D point2D : point2DList) {
                    Rectangle rectangle = new Rectangle(15, 15);
                    rectangle.setFill(((Unidad) dibujable).obtenerColor());
                    //rectangle.setOnMouseClicked(e -> mostrarMenuDeOpciones(point2D));

                    Text text = new Text(((Unidad) dibujable).obtenerPropietario().verNombre().substring(0, 1));
                    text.setFont(Font.font("Verdana", 10));

                    StackPane stack = new StackPane();
                    stack.getChildren().addAll(rectangle, text);
                    stack.setOnMouseClicked(e -> mostrarMenuDeOpciones(point2D));
                    gridPane.add(stack, (int) point2D.getX(), (int) point2D.getY());
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

        algoEmpires.agregarJugador(jugador1);
        algoEmpires.agregarJugador(jugador2);

        algoEmpires.comenzarPartida();

        this.mapa = algoEmpires.mapa; //TODO: Esto es feo, cambiar despues
    }
}

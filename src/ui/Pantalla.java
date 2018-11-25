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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.AlgoEmpires;
import main.Jugador;
import main.Mapa;
import unidades.Dibujable;
import unidades.Unidad;
import unidades.edificio.Castillo;
import unidades.edificio.Edificio;

import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

public class Pantalla extends Application {

    private AlgoEmpires algoEmpires;
    private Mapa mapa;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Algo Empires");

        /*Primero aparece una Scene para agregar dos jugadores y luego la Scene que es el juego principal*/
        Button button = new Button("OK");
        VBox layout = new VBox();

        TextField nombre1 = new TextField();
        TextField nombre2 = new TextField();
        layout.getChildren().addAll(new Label("Ingrese los nombres de los jugadores:"), nombre1, nombre2, button);

        Scene crearJugadores = new Scene(layout, 800, 600);
        primaryStage.setScene(crearJugadores);
        primaryStage.show();


        GridPane gridPane = new GridPane();
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
            construirMapa(gridPane);
        });

    }

    private void construirMapa(GridPane gridPane) {
        //Pinto todo de verde
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
                List<Point2D> point2DList = this.mapa.obtenerCoordenadas((Unidad) dibujable); //TODO: Este cast es feo -> El mapa deberia recibir un dibujable

                for (Point2D point2D : point2DList) {
                    Rectangle rectangle = new Rectangle(15, 15);
                    rectangle.setFill(Color.RED);
                    gridPane.add(rectangle, (int) point2D.getX(), (int) point2D.getY());
                }

            } catch (CoordenadaInvalidaException ignore) {}
        }
    }

    private void crearJuego(String jugador1, String jugador2) throws NombreRepetidoException, NumeroDeJugadoresException, ComienzoDePartidaException {
        this.algoEmpires = new AlgoEmpires();

        this.algoEmpires.agregarJugador(jugador1);
        this.algoEmpires.agregarJugador(jugador2);

        this.algoEmpires.comenzarPartida();

        this.mapa = this.algoEmpires.mapa; //TODO: Esto es feo, cambiar despues
    }
}

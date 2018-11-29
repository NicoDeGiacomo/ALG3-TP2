package ui;

import excepciones.main.ComienzoDePartidaException;
import excepciones.main.NombreRepetidoException;
import excepciones.main.NumeroDeJugadoresException;
import excepciones.mapa.CoordenadaInvalidaException;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
import unidades.edificios.Castillo;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class Pantalla extends Application {

    private Mapa mapa;
    private AlgoEmpires algoEmpires;
    private List<Dibujable> dibujablesUsados;
    private String jugador1;
    private String jugador2;

    private final int TAMANIO_VENTANA = 600;
    private final int TAMANIO_CELDA = (TAMANIO_VENTANA / mapa.TAMANIO) - 5;

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
        addStyleSheet(this.menuDeJuego);
        addStyleSheet(menuPrincipal);

        Sonido.reproducirSonidoDeMenu();

        this.stage.show();
    }

    private Scene crearMenuDeJuego() {
        this.gridPane = new GridPane();
        addStyleClass(this.gridPane, "grid-pane");

        this.infoLabel = new Label("");
        addStyleClass(this.infoLabel, "game-info");

        Button botonPasarTurno = new Button("Terminar turno");
        botonPasarTurno.setOnAction(e -> {
            Sonido.reproducirSonidoDeBoton();
            this.pasarTurno();
        });

        Rectangle colorJugador1 = new Rectangle();
        colorJugador1.setHeight(10);
        colorJugador1.setWidth(10);
        addStyleClass(colorJugador1,"colorunidadesjugador1");

        //colorJugador1.setText(this.jugador1);

        colorJugador1.setTranslateY(-TAMANIO_VENTANA/10);
        colorJugador1.setTranslateX(-TAMANIO_VENTANA/3);


        VBox layout = new VBox();
        addStyleClass(layout, "vbox");
        layout.getChildren().addAll(this.infoLabel, this.gridPane, botonPasarTurno,colorJugador1);

        return new Scene(layout, TAMANIO_VENTANA, TAMANIO_VENTANA);
    }

    private Scene crearMenuPrincipal() {
        ImageView logoView = new ImageView();
        addStyleClass(logoView, "logo");

        Label label = new Label("Ingrese los nombres de los jugadores:");
        addStyleClass(label, "label");

        TextField nombre1 = new TextField("Jugador");
        TextField nombre2 = new TextField("Player");
        this.jugador1 = nombre1.getText();
        this.jugador2 = nombre2.getText();

        Button button = new Button("Comenzar juego");

        VBox layout = new VBox();
        addStyleClass(layout, "vbox");
        layout.getChildren().addAll(logoView, label, nombre1, nombre2, button);

        button.setOnAction(e -> {
            Sonido.detenerSonidos();
            Sonido.reproducirSonidoDeBoton();
            Sonido.reproducirSonidoDeFondo();
            try {
                crearJuego(nombre1.getText(), nombre2.getText());
            } catch (NombreRepetidoException | NumeroDeJugadoresException | ComienzoDePartidaException error) {
                Alerta.displayError("Error al crear partida", error.getMessage());
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
                rectangle.setFill( Color.FORESTGREEN);
                this.gridPane.add(rectangle, i, j);
            }
        }

        for (Dibujable dibujable : this.mapa.obtenerTodosLosDibujables()) {
            try {
                List<Point2D> point2DList = this.mapa.obtenerCoordenadas(dibujable);

                ImageView imagen = dibujable.obtenerImagen();
                String propietario = ((Unidad)dibujable).obtenerPropietario().verNombre();
                    String style = obtenerColorDeJugador(propietario) ;
                    imagen.setFitWidth(TAMANIO_CELDA * ((int) Math.sqrt(dibujable.verTamanio())));
                    imagen.setFitHeight(TAMANIO_CELDA * ((int) Math.sqrt(dibujable.verTamanio())));
                    GridPane.setColumnSpan(imagen, ((int) Math.sqrt(dibujable.verTamanio())));
                    GridPane.setRowSpan(imagen, ((int) Math.sqrt(dibujable.verTamanio())));
                    imagen.setOnMouseClicked(e -> mostrarMenuDeOpciones(point2DList.get(0)));
                    addStyleClass(imagen,style);
                    this.gridPane.add(imagen, ((int) point2DList.get(0).getX()), ((int) point2DList.get(0).getY()));



            } catch (CoordenadaInvalidaException ignore) {
            }
        }
    }

    private String obtenerColorDeJugador(String propietario) {
        if (propietario.equals(jugador1)) return "colorunidadesjugador1";
        return "colorunidadesjugador2";

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
        String nombreAnterior = this.algoEmpires.obtenerJugadorEnTurno().verNombre();
        this.dibujablesUsados.clear();
        try {
            this.algoEmpires.pasarTurno();
            if (!this.algoEmpires.obtenerJugadorEnTurno().todaviaEnJuego()) {
                mostarPantallaDeVictoria(nombreAnterior);
                return;
            }
            Alerta.displayMessage("Proximo turno", String.format("Le toca al jugador: %s", this.algoEmpires.obtenerJugadorEnTurno().verNombre()));
            this.actualizarPantalla();
        } catch (ComienzoDePartidaException ignore) {
        }
    }

    private void mostarPantallaDeVictoria(String nombreAnterior) {
        Sonido.reproducirSonidoDeVictoria();
        VBox layout = new VBox();
        addStyleClass(layout, "vbox");
        Label label1 = new Label("Juego terminado.");
        addStyleClass(label1, "label");
        Label label2 = new Label(String.format("%s es el ganador!", nombreAnterior));
        addStyleClass(label2, "label");
        layout.getChildren().addAll(label1, label2);

        Scene scene = new Scene(layout, TAMANIO_VENTANA, TAMANIO_VENTANA);
        addStyleSheet(scene);
        this.stage.setScene(scene);
    }

    private void crearJuego(String jugador1, String jugador2) throws NombreRepetidoException, NumeroDeJugadoresException, ComienzoDePartidaException {
        this.algoEmpires = new AlgoEmpires();

        this.algoEmpires.agregarJugador(jugador1);
        this.algoEmpires.agregarJugador(jugador2);

        this.mapa = this.algoEmpires.comenzarPartida();
    }

    static void addStyleClass(Node node, String style) {
        node.getStyleClass().clear();
        node.getStyleClass().add(style);
    }

    static void addStyleSheet(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add("style.css");
    }

}

package ui;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unidades.edificio.Castillo;
import unidades.edificio.Cuartel;
import unidades.edificio.PlazaCentral;
import unidades.milicia.Aldeano;
import unidades.milicia.ArmaDeAsedio;
import unidades.milicia.Arquero;
import unidades.milicia.Espadachin;

import java.util.concurrent.atomic.AtomicReference;

public class Menu {

    //TODO: Arreglar la repeticion de codigo
    static private boolean answer;

    public static boolean mostrarMenuDeCastillo(Castillo castillo) {
        Stage window = new Stage();

        Button crearUnidad = new Button("Crear unidad");
        crearUnidad.setOnAction(e -> {
            try {
                castillo.crearUnidad();
                answer = true;
            } catch (OroInsuficienteException | LimiteDePoblacionException | CoordenadaInvalidaException error) {
                Alerta.display("Error al crear la unidad", error.getMessage());
                answer = false;
            }
            window.close();
        });

        mostrarMenu(window, "Menu de Castillo", crearUnidad);
        return answer;
    }

    public static boolean mostrarMenuDePlazaCentral(PlazaCentral plazaCentral) {
        Stage window = new Stage();

        Button crearUnidad = new Button("Crear unidad");
        crearUnidad.setOnAction(e -> {
            try {
                plazaCentral.crearUnidad();
                answer = true;
            } catch (OroInsuficienteException | LimiteDePoblacionException | CoordenadaInvalidaException error) {
                Alerta.display("Error al crear la unidad", error.getMessage());
                answer = false;
            }
            window.close();
        });

        return mostrarMenu(window, "Menu de Plaza Central", crearUnidad);
    }

    public static boolean mostrarMenuDeCuartel(Cuartel cuartel) {
        //TODO: IMPLEMENTAR
        return false;
    }

    public static boolean mostrarMenuDeAldeano(Aldeano aldeano) {
        //TODO: Terminar de implementar.
        Stage window = new Stage();

        Button crearCuartel= new Button("Construir Cuartel");
        crearCuartel.setOnAction(e -> {

            answer = false;

            Point2D coordenada = mostrarMenuConstruccion(aldeano);

            window.close();

        });

        return mostrarMenu(window, "Menu de Aldeano", crearCuartel);
    }

    public static Point2D mostrarMenuConstruccion(Aldeano aldeano) {
        //TODO: Ver si se puede devolver una posición apta para construir.
        Stage window = new Stage();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5));
        grid.setHgap(5);
        grid.setVgap(5);

        int alcance = aldeano.verAlcance();

        for (int i = -alcance; i <= alcance; i++) {
            for (int j = -alcance; j <= alcance; j++) {
                String valor = (i == 0 && j == 0) ? "A" : "  ";
                Button button = new Button(valor);
                button.setOnAction(e -> {
                    window.close();
                });
                grid.add(button, i + alcance, j + alcance);
            }
        }

        ScrollPane scrollPane = new ScrollPane(grid);
        window.setScene(new Scene(scrollPane));
        window.show();

        return new Point2D(1,1);
    }

    public static boolean mostrarMenuDeArquero(Arquero arquero) {
        //TODO: IMPLEMENTAR
        return false;
    }

    public static boolean mostrarMenuDeEspadachin(Espadachin espadachin) {
        //TODO: IMPLEMENTAR
        return false;
    }

    public static boolean mostrarMenuDeArmaDeAsedio(ArmaDeAsedio armaDeAsedio) {
        //TODO: IMPLEMENTAR
        return false;
    }

    static private boolean mostrarMenu(Stage window, String titulo, Button... buttons) {
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(titulo);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText("Elija la acción a realizar:");

        VBox layout = new VBox(10);
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

}

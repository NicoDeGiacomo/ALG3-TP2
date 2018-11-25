package ui;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unidades.edificio.Castillo;
import unidades.edificio.PlazaCentral;

class Menu {

    //TODO: Arreglar la repeticion de codigo
    static boolean answer;

    static boolean mostrarMenuDeCastillo(Castillo castillo) {
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

    static boolean mostrarMenuDePlazaCentral(PlazaCentral plazaCentral) {
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

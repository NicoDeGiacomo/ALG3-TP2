package ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Mapa;

import java.io.FileInputStream;
import java.util.Map;

public class Pantalla extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Algo Empires");

        /*Primero aparece una Scene para agregar dos jugadores y luego la Scene que es el juego principal*/
        Button button = new Button("Pasar a escena 2");
        VBox layout = new VBox();
        layout.getChildren().addAll(new Label("Escena 1"), button);

        Scene crearJugadores = new Scene(layout, 800, 600);
        primaryStage.setScene(crearJugadores);
        primaryStage.show();




        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setGridLinesVisible(true);
        Image image = new Image(new FileInputStream("assets/pasto.jpg"));

        //Controlar el tamanio de la imagen!!!
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(10);
                imageView.setFitWidth(10);
                gridPane.add(imageView, i, j);
            }
        }

        Scene juego = new Scene(gridPane);
        button.setOnAction(e -> primaryStage.setScene(juego));







        //ImageView selectedImage = new ImageView(new Image(new FileInputStream("assets/pasto.jpg")));
        //Image casilla = new Image(new FileInputStream("assets/pasto.jpg"));
        //selectedImage.setImage(casilla);

        /*StackPane stackPane1 = new StackPane();
        stackPane1.getChildren().add(selectedImage);
        Scene scene1 = new Scene(stackPane1);
        primaryStage.setScene(scene1);
        primaryStage.show();*/
    }
}

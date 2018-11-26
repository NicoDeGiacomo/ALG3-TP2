package ui;

import excepciones.main.LimiteDePoblacionException;
import excepciones.main.OroInsuficienteException;
import excepciones.mapa.CoordenadaInvalidaException;
import excepciones.mapa.UnidadNoMovibleException;
import excepciones.unidades.AldeanoOcupadoException;
import excepciones.unidades.CreacionDeCastilloException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unidades.Unidad;
import unidades.edificio.Castillo;
import unidades.edificio.Cuartel;
import unidades.edificio.PlazaCentral;
import unidades.milicia.Aldeano;
import unidades.milicia.ArmaDeAsedio;
import unidades.milicia.Arquero;
import unidades.milicia.Espadachin;

import java.awt.geom.Point2D;

public class Menu {

    static private boolean answer;
    static private Point2D posAnswer;

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
        Stage window = new Stage();

        Button crearArquero = new Button("Crear Arquero");
        crearArquero.setOnAction(e -> {
            try {
                cuartel.crearArquero();
                answer = true;
            } catch (OroInsuficienteException | LimiteDePoblacionException | CoordenadaInvalidaException error) {
                Alerta.display("Error al crear la unidad", error.getMessage());
                answer = false;
            }
            window.close();
        });

        Button crearEspadachin = new Button("Crear Espadachin");
        crearEspadachin.setOnAction(e -> {
            try {
                cuartel.crearEspadachin();
                answer = true;
            } catch (OroInsuficienteException | LimiteDePoblacionException | CoordenadaInvalidaException error) {
                Alerta.display("Error al crear la unidad", error.getMessage());
                answer = false;
            }
            window.close();
        });

        return mostrarMenu(window, "Menu de Cuartel", crearArquero, crearEspadachin);
    }

    public static boolean mostrarMenuDeAldeano(Aldeano aldeano, Point2D point2D) {
        Stage window = new Stage();

        Button crearCuartel = new Button("Construir Cuartel");
        crearCuartel.setOnAction(e -> {
            Point2D coordenada = mostrarGrillaDeCoordenadas(aldeano, point2D, aldeano.verAlcance());
            try {
                aldeano.construir(new Cuartel(aldeano.obtenerPropietario()), coordenada);
                answer = true;
            } catch (AldeanoOcupadoException | OroInsuficienteException | CreacionDeCastilloException | CoordenadaInvalidaException error) {
                Alerta.display("Error al crear edificio", error.getMessage());
                answer = false;
            }
            window.close();
        });

        Button crearPlazaCentral = new Button("Construir Plaza Central");
        crearPlazaCentral.setOnAction(e -> {
            Point2D coordenada = mostrarGrillaDeCoordenadas(aldeano, point2D, aldeano.verVelocidad());
            try {
                aldeano.construir(new PlazaCentral(aldeano.obtenerPropietario()), coordenada);
                answer = true;
            } catch (AldeanoOcupadoException | OroInsuficienteException | CreacionDeCastilloException | CoordenadaInvalidaException error) {
                Alerta.display("Error al crear edificio", error.getMessage());
                answer = false;
            }
            window.close();
        });

        Button moverAldeano = new Button("Mover Aldeano");
        moverAldeano.setOnAction(e -> {
            Point2D coordenada = mostrarGrillaDeCoordenadas(aldeano, point2D, aldeano.verVelocidad());

            try {
                aldeano.moverUnidad(aldeano, coordenada);
                answer = true;
            } catch (UnidadNoMovibleException | CoordenadaInvalidaException error) {
                Alerta.display("Error al crear edificio", error.getMessage());
                answer = false;
            }

            window.close();
        });

        return mostrarMenu(window, "Menu de Aldeano", crearCuartel, crearPlazaCentral, moverAldeano);
    }

    public static boolean mostrarMenuDeArquero(Arquero arquero, Point2D point2D) {
        Stage window = new Stage();

        Button moverArquero = new Button("Mover Arquero");
        moverArquero.setOnAction(e -> {
            Point2D coordenada = mostrarGrillaDeCoordenadas(arquero, point2D, arquero.verVelocidad());

            try {
                arquero.moverUnidad(arquero, coordenada);
                answer = true;
            } catch (UnidadNoMovibleException | CoordenadaInvalidaException error) {
                Alerta.display("Error al crear edificio", error.getMessage());
                answer = false;
            }

            window.close();
        });

        Button atacarUnidad = new Button("Atacar Unidad");
        atacarUnidad.setOnAction(e -> {
            Point2D coordenada = mostrarGrillaDeCoordenadas(arquero, point2D, arquero.verAlcance());

            //TODO: Necesito obtener la referencia a la Unidad Enemiga para provocarDanio(Unidad unidad).
            //arquero.provocarDanio();
            answer = true;

            window.close();
        });

        return mostrarMenu(window, "Menu de Arquero", moverArquero, atacarUnidad);
    }

    public static boolean mostrarMenuDeEspadachin(Espadachin espadachin, Point2D point2D) {
        Stage window = new Stage();

        Button moverEspadachin = new Button("Mover Arquero");
        moverEspadachin.setOnAction(e -> {
            Point2D coordenada = mostrarGrillaDeCoordenadas(espadachin, point2D, espadachin.verVelocidad());

            try {
                espadachin.moverUnidad(espadachin, coordenada);
                answer = true;
            } catch (UnidadNoMovibleException | CoordenadaInvalidaException error) {
                Alerta.display("Error al crear edificio", error.getMessage());
                answer = false;
            }

            window.close();
        });

        Button atacarUnidad = new Button("Atacar Unidad");
        atacarUnidad.setOnAction(e -> {
            Point2D coordenada = mostrarGrillaDeCoordenadas(espadachin, point2D, espadachin.verAlcance());

            //TODO: Necesito obtener la referencia a la Unidad Enemiga para provocarDanio(Unidad unidad).
            //arquero.provocarDanio();
            answer = true;

            window.close();
        });

        return mostrarMenu(window, "Menu de Arquero", moverEspadachin, atacarUnidad);
    }

    public static boolean mostrarMenuDeArmaDeAsedio(ArmaDeAsedio armaDeAsedio, Point2D point2D) {
        Stage window = new Stage();

        Button moverArmaDeAsedio = new Button("Mover Arquero");
        moverArmaDeAsedio.setOnAction(e -> {
            Point2D coordenada = mostrarGrillaDeCoordenadas(armaDeAsedio, point2D, armaDeAsedio.verVelocidad());

            try {
                armaDeAsedio.moverUnidad(armaDeAsedio, coordenada);
                answer = true;
            } catch (UnidadNoMovibleException | CoordenadaInvalidaException error) {
                Alerta.display("Error al crear edificio", error.getMessage());
                answer = false;
            }

            window.close();
        });

        Button atacarUnidad = new Button("Atacar Unidad");
        atacarUnidad.setOnAction(e -> {
            Point2D coordenada = mostrarGrillaDeCoordenadas(armaDeAsedio, point2D, armaDeAsedio.verAlcance());

            //TODO: Necesito obtener la referencia a la Unidad Enemiga para provocarDanio(Unidad unidad).
            //arquero.provocarDanio();
            answer = true;

            window.close();
        });

        return mostrarMenu(window, "Menu de Arquero", moverArmaDeAsedio, atacarUnidad);
    }

    private static Point2D mostrarGrillaDeCoordenadas(Unidad unidad, Point2D point2D, int distancia) {
        Stage window = new Stage();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5));
        grid.setHgap(5);
        grid.setVgap(5);

        for (int i = -distancia; i <= distancia; i++) {
            for (int j = -distancia; j <= distancia; j++) {
                Point2D show = new Point2D.Double(point2D.getX() + i, point2D.getY() + j);
                String valor = String.format("(%d; %d)", (long) show.getX(), (long) show.getY());
                Button button = new Button(valor);
                button.setOnAction(e -> {
                    //TODO: Ojo que si no está en el Mapa no debería aparecer!!
                    posAnswer = show;
                    window.close();
                });
                grid.add(button, i + distancia, j + distancia);
                if (i == 0 && j == 0)
                    button.setDisable(true);
            }
        }

        ScrollPane scrollPane = new ScrollPane(grid);
        window.setScene(new Scene(scrollPane));
        window.showAndWait();

        return posAnswer;
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

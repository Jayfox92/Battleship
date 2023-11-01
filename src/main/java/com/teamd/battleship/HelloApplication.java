package com.teamd.battleship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("BattleShip");

        GridPane spelplanSpelareEtt = createSpelplan();
        GridPane spelplanSpelareTva = createSpelplan();

        VBox spelarLayout = new VBox(50); // Skapar en VBox med 50 pixels avstånd
        spelarLayout.getChildren().addAll(spelplanSpelareEtt, spelplanSpelareTva);

        Scene scene = new Scene(createLayout(spelarLayout), 500, 600); //

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private GridPane createSpelplan() {
        GridPane gridPane = new GridPane();
        int size = 10; // Storlek på rutnätet
        for (int rad = 0; rad < size; rad++) {
            for (int kolumn = 0; kolumn < size; kolumn++) {
                Rectangle ruta = new Rectangle(25, 25);
                ruta.setFill(Color.LIGHTBLUE);
                ruta.setStroke(Color.BLACK);

                gridPane.add(ruta, kolumn, rad);
            }
        }
        return gridPane;
    }

    private VBox createLayout(VBox spelarLayout) {
        VBox layout = new VBox();
        layout.getChildren().addAll(spelarLayout);
        return layout;
    }


    //scen
    //2d array egen spelplan
    //2d array motståndarplan
    //ruta med text (sys.out)
    //färg kod för träff & miss
    //delay mellan actions (0-5s)
    public static void main(String[] args) {
        launch();
    }
}
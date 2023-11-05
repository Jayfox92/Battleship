package com.teamd.battleship;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("BattleShip");

        AnchorPane anchorPane = new AnchorPane();

        GridPane playerOne = createSpelplan();
        GridPane playerTwo = createSpelplan();

        AnchorPane.setTopAnchor(playerOne, 20.0);
        AnchorPane.setLeftAnchor(playerOne, 20.0);
        AnchorPane.setBottomAnchor(playerTwo, 20.0);
        AnchorPane.setLeftAnchor(playerTwo, 20.0);

        Button startaSpelKnapp = new Button("Starta spel");
        startaSpelKnapp.setOnAction(event -> handleStartGame()); // refererar till handleStartGame
        startaSpelKnapp.setLayoutX(300);
        startaSpelKnapp.setLayoutY(295);


        anchorPane.getChildren().addAll(playerOne, playerTwo, startaSpelKnapp);

        Scene scene = new Scene(anchorPane, 500, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private GridPane createSpelplan() {
        GridPane gridPane = new GridPane();
        int size = 10;
        for (int rad = 0; rad < size; rad++) {
            for (int kolumn = 0; kolumn < size; kolumn++) {
                Rectangle pane = new Rectangle(25, 25);
                pane.setFill(Color.rgb(0,204,204));
                pane.setStroke(Color.BLACK);

                pane.setOnMouseEntered(event -> {
                    pane.setFill(Color.rgb(0,0,112)); // Färg när musen är över rektangeln
                });

                pane.setOnMouseExited(event -> {
                    pane.setFill(Color.rgb(0, 204, 204)); // Återställer färgen när musen lämnar rektangeln
                });
                gridPane.add(pane, kolumn, rad);
            }
        }
        return gridPane;
    }

    private void handleStartGame() {
        System.out.println("Spelet startar");
        // placera spelets logik

    }

    public static void main(String[] args) {
        launch();
    }
}

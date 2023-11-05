package com.teamd.battleship;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private Stage primaryStage;
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FirstScene();
    }
    private void FirstScene() {
        primaryStage.setTitle("BattleShip");


        AnchorPane welcomePane = new AnchorPane();

        Text welcomeText = new Text("Welcome to BattleShip");
        welcomeText.setLayoutX(60);
        welcomeText.setLayoutY(100);
        welcomeText.setStyle("-fx-fill: dodgerblue; -fx-font-size: 36; -fx-font-weight: bold; -fx-font-style: italic;");

        Button exitGame = new Button("Avsluta");
        exitGame.setOnAction(event -> primaryStage.close());
        exitGame.setStyle("-fx-font-size: 18; -fx-background-color: dodgerblue; -fx-text-fill:white");
        exitGame.setPrefWidth(150);
        exitGame.setLayoutY(250);
        exitGame.setLayoutX(185);

        exitGame.setOnMouseEntered(event -> exitGame.setStyle("-fx-font-size: 18; -fx-background-color: red; -fx-text-fill: white;"));
        exitGame.setOnMouseExited(mouseEvent -> exitGame.setStyle("-fx-font-size: 18; -fx-background-color: dodgerblue; -fx-text-fill: white;"));

        Button startButton = new Button("BattleShip");
        startButton.setOnAction(event -> SecondScene());
        startButton.setStyle("-fx-font-size: 18; -fx-background-color: dodgerblue; -fx-text-fill:white ");
        startButton.setPrefWidth(150);
        startButton.setLayoutY(200);
        startButton.setLayoutX(185);

        startButton.setOnMouseEntered(event -> startButton.setStyle("-fx-font-size: 18; -fx-background-color: lightblue; -fx-text-fill: white;"));
        startButton.setOnMouseExited(event -> startButton.setStyle("-fx-font-size: 18; -fx-background-color: dodgerblue; -fx-text-fill: white;"));

        welcomePane.getChildren().addAll(startButton, welcomeText, exitGame);

        Scene welcomeScene = new Scene(welcomePane, 500, 400);
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }
    private void SecondScene(){
        primaryStage.setTitle("BattleShip");

        AnchorPane anchorPane = new AnchorPane();

        GridPane playerOne = createSpelplan();
        GridPane playerTwo = createSpelplan();

        playerOne.setLayoutX(125);
        playerOne.setLayoutY(45);
        playerTwo.setLayoutX(420);
        playerTwo.setLayoutY(45);


        Button startaSpelKnapp = new Button("Starta spel");
        startaSpelKnapp.setOnAction(event -> handleStartGame(anchorPane)); // refererar till handleStartGame
        startaSpelKnapp.setLayoutX(300);
        startaSpelKnapp.setLayoutY(290);
        anchorPane.getChildren().addAll(playerOne, playerTwo, startaSpelKnapp);

        Scene scene = new Scene(anchorPane, 800, 450);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private GridPane createSpelplan() { // 2dArray ? ? // lägga till bokstäver y-led o siffor x-led
        GridPane gridPane = new GridPane();
        int size = 10;
        for (int rad = 0; rad < size; rad++) {
            for (int kolumn = 0; kolumn < size; kolumn++) {
                Rectangle pane = new Rectangle(22, 22);
                pane.setFill(Color.rgb(0,204,204));
                pane.setStroke(Color.BLACK);

                pane.setOnMouseEntered(event -> {               //KAn ta bort
                    pane.setFill(Color.rgb(0,0,112)); // Färg när musen är över rektangeln
                });

                pane.setOnMouseExited(event -> {                //KAn ta bort ??
                    pane.setFill(Color.rgb(0, 204, 204)); // Återställ färg när musen lämnar rektangeln
                });
                gridPane.add(pane, kolumn, rad);
            }
        }
        return gridPane;
    }

    private void handleStartGame(AnchorPane anchorPane) {
        System.out.println("Spelet startar");
        TextField textField = new TextField();
        textField.setPromptText("Skriv in "); // skapar textfield för när spelet startar
        textField.setLayoutX(20);
        textField.setLayoutY(243);
        System.out.println(textField);
        anchorPane.getChildren().add(textField);
        // placera spelets logik

    }


}

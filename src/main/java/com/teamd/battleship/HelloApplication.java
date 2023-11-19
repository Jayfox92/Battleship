package com.teamd.battleship;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Map;
import java.util.Scanner;

public class HelloApplication extends Application {

    Scanner scanner = new Scanner(System.in);
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

    private void SecondScene() {
        primaryStage.setTitle("BattleShip");

        AnchorPane anchorPane = new AnchorPane();

        GridPane playerBoard = ownPlayerBoard();

        GridPane opponentBoard = opponentPlayerBoard();

        PositionGameBoards(playerBoard, opponentBoard);

        Button startaSpelKnapp = new Button("Starta spel");
        startaSpelKnapp.setOnAction(event -> handleStartGame(anchorPane,startaSpelKnapp)); // refererar till handleStartGame
        layoutStartGameButton(startaSpelKnapp);


        Text playerjag = new Text("Jag");
        playerjag.setLayoutX(230);
        playerjag.setLayoutY(35);
        playerjag.setStyle("-fx-fill: black; -fx-font-size: 25; -fx-font-weight: bold; -fx-font-style: italic;");

        Text playerMotståndare = new Text("Motståndare");
        playerMotståndare.setLayoutY(35);
        playerMotståndare.setLayoutX(455);
        playerMotståndare.setStyle("-fx-fill: black; -fx-font-size: 25; -fx-font-weight: bold; -fx-font-style: italic;");

        anchorPane.getChildren().addAll(playerBoard, opponentBoard, startaSpelKnapp, playerjag, playerMotståndare);

        Scene scene = new Scene(anchorPane, 800, 450);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
    private void PositionGameBoards(GridPane playerOne, GridPane playerTwo) { // här också
        playerOne.setLayoutX(125);
        playerOne.setLayoutY(45);
        playerTwo.setLayoutX(420);
        playerTwo.setLayoutY(45);
    }

    private void layoutStartGameButton(Button startaSpelKnapp) { //gör det enklare
        startaSpelKnapp.setLayoutX(350);
        startaSpelKnapp.setLayoutY(310);
    }
    private  GridPane opponentPlayerBoard(){
        GridPane gridPane = new GridPane();

        int size = 10;
        char[] letters = "ABCDEFGHIJ".toCharArray(); // added char array
        for (int rad = 0; rad < size; rad++) {
            for (int kolumn = 0; kolumn < size; kolumn++) {
                Rectangle pane = new Rectangle(22, 22);
                pane.setFill(Color.rgb(0, 204, 204));
                pane.setStroke(Color.BLACK);
                gridPane.add(pane, kolumn + 1, rad + 1); // Shifted by 1 to make space for labels

            }
        }

        // Add letters and numbers as labels
        for (int i = 0; i < size; i++) {
            Text columnLabel = new Text(Integer.toString(i));
            Text rowLabel = new Text(Character.toString(letters[i]));
            gridPane.add(columnLabel, i + 1, 0); // Numbers on x-axis
            gridPane.add(rowLabel, 0, i + 1);    // Letters on y-axis
        }

        return gridPane;

    }
    private GridPane ownPlayerBoard() {
        GridPane gridPane = new GridPane();
        Battleship battleship = new Battleship();

        battleship.shipPlacement();
        int size = 10;
        char[] letters = "ABCDEFGHIJ".toCharArray(); // added char array
        for (int rad = 0; rad < battleship.mapSizeY; rad++) {
            for (int kolumn = 0; kolumn < battleship.mapSizeX; kolumn++) {

                Rectangle pane = new Rectangle(22, 22);

                Map<String, Color> colorMap = Map.of( // skapar färg till båtarna här med hjälp av map
                            "2", Color.GREY,
                        "3", Color.GREY,
                        "4", Color.GREY,
                        "5", Color.GREY
                );
                pane.setFill(Color.rgb(0, 204, 204));
                pane.setStroke(Color.BLACK);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(pane);
                String temp[][] = battleship.getMap();

                Label label = new Label(""); // Gör Tom label för att ta bort siffrorna

                stackPane.getChildren().addAll(label);

                gridPane.add(stackPane, kolumn + 1, rad + 1); // Shifted by 1 to make space for labels
                gridPane.requestLayout();

                String shipLength = temp[rad][kolumn]; // hämtar värdet från arrayen för att kontrollera colormap innehåller shipLength
                if (colorMap.containsKey(shipLength)) {
                    pane.setFill(colorMap.get(shipLength));

                }
            }
        }

        // Add letters and numbers as labels
        for (int i = 0; i < size; i++) {
            Text columnLabel = new Text(Integer.toString(i));
            Text rowLabel = new Text(Character.toString(letters[i]));
            gridPane.add(columnLabel, i + 1, 0); // Numbers on x-axis
            gridPane.add(rowLabel, 0, i + 1);    // Letters on y-axis
        }

        return gridPane;
    }
    private void uptadeGameBoard(){


    }


    private void handleStartGame(AnchorPane anchorPane, Button startaSpelKnapp ) {
        startaSpelKnapp.setVisible(false); // gör så att knappen försvinner
        System.out.println("Spelet startar");

        TextField delayTextField = new TextField();
        delayTextField.setPromptText("Ange fördröjning i sekunder");
        delayTextField.setLayoutX(134);
        delayTextField.setLayoutY(300);
        delayTextField.setPrefSize(220, 20);
        delayTextField.setStyle("-fx-prompt-text-fill: red;");


        Button startAutoShotButton = new Button("Starta automatiskt skjutning");
        startAutoShotButton.setLayoutX(134);
        startAutoShotButton.setLayoutY(340);

        startAutoShotButton.setOnAction(event -> {
            String delayText = delayTextField.getText();

            try {
                int delayInSeconds = Integer.parseInt(delayText);

                // Kontrollera om det är heltal
                if (delayInSeconds > 0) {
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(delayInSeconds), e -> {
                        // LÄGGA TILL automatisk skjutnnig metoden här ?????
                        System.out.println("Automatisk skjutning efter " + delayInSeconds + " sekunder.");
                    }));
                    timeline.play();
                } else {
                    System.out.println("Ange ett heltal för fördröjning.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ange ett giltigt heltal för fördröjning.");
            }
        });

        anchorPane.getChildren().addAll(delayTextField, startAutoShotButton);
    }
}
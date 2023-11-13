package com.teamd.battleship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class HelloApplication extends Application {

    private Stage primaryStage;
    GridPane playerOne;
    GridPane playerTwo;
    ArrayList<Skepp> playerOneFleet;
    ArrayList<Skepp> playerTwoFleet;
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/InputView.fxml"));
        this.primaryStage = primaryStage;
        SecondScene();
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

        // Create board and fleet for the two players
        playerOne = createSpelplan();
        playerTwo = createSpelplan();
        playerOneFleet = createFleet();
        playerTwoFleet = createFleet();

        playerOne.setLayoutX(125);
        playerOne.setLayoutY(45);
        playerTwo.setLayoutX(420);
        playerTwo.setLayoutY(45);

        Button startaSpelKnapp = new Button("Start: Set boards");
        startaSpelKnapp.setOnAction(event -> handleStartGame(anchorPane)); // refererar till handleStartGame
        startaSpelKnapp.setLayoutX(350); //shifted the button a bit to the center
        startaSpelKnapp.setLayoutY(310); //moved the button along the y-axis as it was overlapping with the Grid
        anchorPane.getChildren().addAll(playerOne, playerTwo, startaSpelKnapp);

        Scene scene = new Scene(anchorPane, 800, 450);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private GridPane createSpelplan() {
        GridPane gridPane = new GridPane();
        int size = 10;
        char[] letters = "ABCDEFGHIJ".toCharArray(); // added char array
        for (int rad = 0; rad < size; rad++) {
            for (int kolumn = 0; kolumn < size; kolumn++) {
                Rectangle pane = new Rectangle(22, 22);
                pane.setFill(Color.rgb(0,204,204));
                pane.setStroke(Color.BLACK);

                // Original effects
                pane.setOnMouseEntered(event -> {
                    pane.setFill(Color.rgb(0,0,112));
                });
                pane.setOnMouseExited(event -> {
                    pane.setFill(Color.rgb(0, 204, 204));
                });

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


    private ArrayList<Skepp> createFleet() {
        /*
         * • ett hangarfartyg (5x1 ruta)
         * • två slagskepp (4x1 ruta)
         * • tre kryssare (3x1 ruta)
         * • fyra ubåtar (2x1 ruta)
         * Add a list of ships to place on the grid
         */
        ArrayList<Skepp> fleet = new ArrayList<>();
        fleet.add(new Hangarfartyg());
        fleet.add(new Slagskepp());
        fleet.add(new Slagskepp());
        fleet.add(new Kryssare());
        fleet.add(new Kryssare());
        fleet.add(new Kryssare());
        fleet.add(new Ubåt());
        fleet.add(new Ubåt());
        fleet.add(new Ubåt());
        fleet.add(new Ubåt());
        return fleet;
    }
        private void handleStartGame(AnchorPane anchorPane) {
        // Randomise the setting
        placeShips(playerOneFleet);
            placeShips(playerTwoFleet);

    }

    private void placeShips(ArrayList<Skepp> fleet){
        // Get random point on board
        Random rand = new Random();
        int x, y, shipLength;
        boolean horizontal = false;

        for(int i = 0; i < 10; i++) {
            boolean pointOK = false;
            while (!pointOK) {
                x = rand.nextInt(10);
                y = rand.nextInt(10);
                shipLength= fleet.get(i).getLängd();

                // check if ship fits left, up, right, down
                if (x - shipLength >= 0) {
                    // Check left
                    horizontal = true;
                    pointOK = checkBoard(x - shipLength, x, horizontal);

                }
                if (!pointOK && (y - shipLength >= 0)) {
                    // Check up
                    horizontal = false;
                    pointOK = checkBoard(y - shipLength, y, horizontal);
                }
                if (!pointOK && (x - shipLength < 0)) {
                    // Check right
                    horizontal = true;
                    pointOK = checkBoard(x, x + shipLength, horizontal);
                }
                if (!pointOK && (y - shipLength < 0)) {
                    // Check down
                    horizontal = false;
                    pointOK = checkBoard(y, y + shipLength, horizontal);
                }

            if (pointOK) {
                drawShipOnBoard(x - shipLength, x, horizontal);
            }
            }

        }

    }
    private boolean checkBoard(int start, int end, boolean horizontal) {
        boolean pointOK = true;

        // START: consider x and y as input, maybe remove horizontal?
        if (horizontal){
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                }
            }

        }
        return pointOK;
    }

    private void drawShipOnBoard(int start, int end, boolean horizontal) {


    }
}

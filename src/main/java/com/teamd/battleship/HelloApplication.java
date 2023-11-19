package com.teamd.battleship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
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
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {

    private Stage primaryStage;
    GridPane playerOne;
    GridPane playerTwo;
    ArrayList<Skepp> playerOneFleet;
    ArrayList<Skepp> playerTwoFleet;
    Button startaSpelKnapp;
    Button shootButton;
    String shootButtonText = "Player 1 shoots";
    boolean isPlayer1 = true;

    // Store 100 points per board
    int[][] player1ShootHistory = new int[100][2];
    int[][] player2ShootHistory = new int[100][2];
    int[][] activePlayerShootHistory = new int[100][2];
    int [] shootingPoint = new int[2];
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

        startaSpelKnapp = new Button("Start: Set boards");
        startaSpelKnapp.setOnAction(event -> handleStartGame(anchorPane)); // refererar till handleStartGame
        startaSpelKnapp.setLayoutX(350); //shifted the button a bit to the center
        startaSpelKnapp.setLayoutY(310); //moved the button along the y-axis as it was overlapping with the Grid

        shootButton = new Button(shootButtonText);
        shootButton.setOnAction(event -> handleShooting(anchorPane)); // refererar till handleStartGame
        shootButton.setLayoutX(350); //shifted the button a bit to the center
        shootButton.setLayoutY(350); //moved the button along the y-axis as it was overlapping with the Grid
        shootButton.setDisable(true);

        anchorPane.getChildren().addAll(playerOne, playerTwo, startaSpelKnapp, shootButton);

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
        placeShips(playerOne, playerOneFleet);
        placeShips(playerTwo, playerTwoFleet);
        // Disable the button after setting the boards.
        startaSpelKnapp.setDisable(true);
        shootButton.setDisable(false);

    }

    private int [] getRandomPoint(){
        Random rand = new Random();
        // First row and column is consumed by the labels
        return new int[]{rand.nextInt(10) + 1, rand.nextInt(10) + 1};
    }
    private void placeShips(GridPane playerBoard, ArrayList<Skepp> fleet){
        // Get random point on board
        int x, y, shipLength;
        int start = 0, fixed = 0;
        boolean horizontal = false;

        for(int i = 0; i < 10; i++) {
            boolean pointOK = false;
            while (!pointOK) {
                x = getRandomPoint()[0];
                y = getRandomPoint()[1];
                shipLength = fleet.get(i).getLängd();

                // check if ship fits left, up, right, down
                if (x - shipLength >= 0) {
                    // Check left
                    horizontal = true;
                    start = x - shipLength;
                    fixed = y;
                    pointOK = checkBoard(playerBoard, start, shipLength, fixed, horizontal);
                }
                if (!pointOK && (y - shipLength >= 0)) {
                    // Check up
                    horizontal = false;
                    start = y - shipLength;
                    fixed = x;
                    pointOK = checkBoard(playerBoard, start, shipLength, fixed, horizontal);

                }
                if (!pointOK && (x - shipLength < 0)) {
                    // Check right
                    horizontal = true;
                    start = x;
                    fixed = y;
                    pointOK = checkBoard(playerBoard, start, shipLength, fixed, horizontal);
                }
                if (!pointOK && (y - shipLength < 0)) {
                    // Check down
                    horizontal = false;
                    start = y;
                    fixed = x;
                    pointOK = checkBoard(playerBoard, start, shipLength, fixed, horizontal);
                }

                if (pointOK) {
                    drawShipOnBoard(playerBoard, start, shipLength, fixed, horizontal);
                }
            }

        }

    }
    private boolean checkBoard(GridPane playerBoard, int start, int shipLength, int fixedAxis, boolean horizontal) {
        boolean pointOK = true;
        int x, y;
        ObservableList<Node> children = playerBoard.getChildren();

        for (int i = start; i < start + shipLength; i++) {
            if (horizontal) {
                x = i + 1;
                y = fixedAxis + 1;
            }
            else {
                x = fixedAxis + 1;
                y = i + 1;
            }

            // Check if the rectangles in the range have a ship already
            for (Node node : children) {
                if(GridPane.getRowIndex(node) == x && GridPane.getColumnIndex(node) == y) {
                    Rectangle rect = (Rectangle) node;
                    if (rect.getFill().equals(Color.DARKGREEN)) {
                        pointOK = false;
                        break;
                    }
                }
            }
        }
        return pointOK;
    }

    private void drawShipOnBoard(GridPane playerBoard, int start, int shipLength, int fixedAxis, boolean horizontal) {

        int x, y;
        for (int i = start; i < start + shipLength; i++) {
            if (horizontal) {
                x = i + 1;
                y = fixedAxis + 1;
            }
            else {
                x = fixedAxis + 1;
                y = i + 1;
            }

            // Update the color of the rectangle at x,y
            ObservableList<Node> children = playerBoard.getChildren();
            for (Node node : children) {
                if(GridPane.getRowIndex(node) == x && GridPane.getColumnIndex(node) == y) {
                    Rectangle rect = (Rectangle) node;
                    rect.setFill(Color.DARKGREEN);
                    rect.setStroke(Color.BLUE);
                    break;
                }
            }
        }
    }

    private void handleShooting(AnchorPane anchorPane) {
        boolean pointHit;
        if (isPlayer1){
            // Player 1 turn
            pointHit = checkShootHit(2);
            shootButtonText = "Player 2 shoots";

        }
        else {
            // Player 2 turn
            pointHit = checkShootHit(1);
            shootButtonText = "Player 1 shoots";
        }

        shootButton.setText(shootButtonText);
        isPlayer1 = !isPlayer1;
    }

    private boolean checkShootHit(int opponent) {
        boolean pointHit = false, validPoint = false;
        GridPane playerBoard;

        while(!validPoint){
            validPoint = getPointToShoot(opponent);
        }

        if (opponent == 1){
            playerBoard = playerOne;
        }
        else {
            playerBoard = playerTwo;
        }

        ObservableList<Node> children = playerBoard.getChildren();

        // Check if the rectangles in the range have a ship already
        for (Node node : children) {
            if(GridPane.getRowIndex(node) == shootingPoint[1] && GridPane.getColumnIndex(node) == shootingPoint[0]) {
                Rectangle rect = (Rectangle) node;
                if (rect.getFill().equals(Color.DARKGREEN)) {
                    pointHit = true;
                    rect.setFill(Color.RED);
                    break;
                }
            }
        }
        return pointHit;
    }

    private boolean getPointToShoot(int opponent) {
        shootingPoint = getRandomPoint();
        boolean isValidPoint = true;

        if (opponent == 1){
            activePlayerShootHistory = player2ShootHistory;
        }
        else {
            activePlayerShootHistory = player1ShootHistory;
        }

        for (int i = 1; i < 100; i++){
            if ((activePlayerShootHistory[i][0] == 0) || (activePlayerShootHistory[i][1] == 0)){
                // Point 0,0 is connected to the labels, and also the init value
                break;
            }
            if ((activePlayerShootHistory[i][0] == shootingPoint[0]) && (activePlayerShootHistory[i][1] == shootingPoint[1])){
                // Point is in history, exit loop
                isValidPoint = false;
            }
        }

        // Save point in array
        for (int i = 1; isValidPoint && i < 100; i++){
            if ((activePlayerShootHistory[i][0] == 0) || (activePlayerShootHistory[i][1] == 0)){
                // Point 0,0 is connected to the labels, and also the init value
                activePlayerShootHistory[i][0] = shootingPoint[0];
                activePlayerShootHistory[i][1] = shootingPoint[1];
                break;
            }
        }
        return  isValidPoint;
    }
}

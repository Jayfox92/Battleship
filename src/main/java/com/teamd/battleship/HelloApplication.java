package com.teamd.battleship;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
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

public class HelloApplication extends Application implements Runnable {


    Scanner scanner = new Scanner(System.in);
    private Stage primaryStage;
    private String[][] opponentMap = new String[10][10];
    private Client client;
    private Server server;
    private Battleship battleship;
    private int boardSize = 10;
    private GridPane playerBoard;
    private GridPane opponentBoard;
    private int lastYShot;
    private int lastXShot;
    private char action;
    private Thread thread;
    public void setThread(){this.thread = thread;
    }

    public void setAction(char action){
        this.action = action;
    }
    public String[][] getOpponentMap() {
        return opponentMap;
    }

    public void setOpponentMap(String[][] opponentMap) {
        this.opponentMap = opponentMap;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Battleship getBattleship() {
        return battleship;
    }

    public void setBattleship(Battleship battleship) {
        this.battleship = battleship;
    }

    public static void main(String[] args) {

        Thread t = new Thread();
        t.start();
    }

    public void run() {
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

        playerBoard = ownPlayerBoard(boardSize);

        opponentBoard = opponentPlayerBoard(boardSize);

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
    private  GridPane opponentPlayerBoard(int size){
        GridPane gridPane = new GridPane();


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
    private GridPane ownPlayerBoard(int size) {
        GridPane gridPane = new GridPane();


        char[] letters = "ABCDEFGHIJ".toCharArray(); // added char array
        for (int rad = 0; rad < size; rad++) {
            for (int kolumn = 0; kolumn < size; kolumn++) {
                Rectangle pane = new Rectangle(22, 22);




                pane.setFill(Color.rgb(0, 204, 204));
                pane.setStroke(Color.BLACK);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(pane);

                //Label label = new Label(""); // Gör Tom label för att ta bort siffrorna

                //stackPane.getChildren().addAll(label);

                gridPane.add(stackPane, kolumn + 1, rad + 1); // Shifted by 1 to make space for labels
                gridPane.requestLayout();

                if (battleship.getMap()[rad][kolumn].contains("s")) {
                    pane.setFill(Color.GREY);

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
    public void storeLastShot(int y, int x){

            this.lastYShot = y;
            this.lastXShot = x;

    }
    public void updateOpponentBoard(char action){
        if (action=='m'){
            StackPane pane = (StackPane) opponentBoard.getChildren().get(lastYShot*boardSize+lastXShot);
            Rectangle rectangle = (Rectangle) pane.getChildren().get(lastYShot*boardSize+lastXShot);
            rectangle.setFill(Color.CADETBLUE);

        }
        else if (action=='h'){
            StackPane pane = (StackPane) opponentBoard.getChildren().get(lastYShot*boardSize+lastXShot);
            Rectangle rectangle = (Rectangle) pane.getChildren().get(lastYShot*boardSize+lastXShot);
            rectangle.setFill(Color.RED);

        }
        else if (action=='s'){
            StackPane pane = (StackPane) opponentBoard.getChildren().get(lastYShot*boardSize+lastXShot);
            Rectangle rectangle = (Rectangle) pane.getChildren().get(lastYShot*boardSize+lastXShot);
            rectangle.setFill(Color.BLACK);

        }
    }

    public void updateOwnGameBoard(int y, int x){

        StackPane pane = (StackPane) playerBoard.getChildren().get(y*boardSize+x);
        Rectangle rectangle = (Rectangle) pane.getChildren().get(y*boardSize+x);
        String[][] newMap = battleship.getMap();
        for (int rad =0; rad < boardSize; rad++){
            for (int kolumn=0; kolumn<boardSize; kolumn++){
                if (newMap[y][x].equals("hit")){
                    rectangle.setFill(Color.RED);
                }else if (newMap[y][x].equals("sunk")){
                    rectangle.setFill(Color.BLACK);

                }
            }
        }

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
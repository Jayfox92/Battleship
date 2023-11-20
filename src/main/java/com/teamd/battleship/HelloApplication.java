package com.teamd.battleship;

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

import java.util.Objects;

public class HelloApplication extends Application {
    private Battleship battleship;
    private GridPane opponentBoard;
    private GridPane ownGameBoard;
    private int lastYShot;
    private int lastXShot;
    private final int boardSize = 10;

    private  char action;

    private static HelloApplication instance;
    public HelloApplication() {
        instance = this;
    }

    public static HelloApplication getApplication() {
        return instance;
    }


    private Stage primaryStage;
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        isInitialized = true;
        FirstScene();
    }

    public static boolean isInitialized() {
        return isInitialized;
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

        //GridPane playerOne = createSpelplan();
        //GridPane playerTwo = createSpelplan();

        this.opponentBoard = createSpelplan(); // Assuming this is the opponent's board
        this.ownGameBoard = createSpelplan();  // Assuming this is the player's own board

        ownGameBoard.setLayoutX(125);
        ownGameBoard.setLayoutY(45);
        opponentBoard.setLayoutX(420);
        opponentBoard.setLayoutY(45);


        Button startaSpelKnapp = new Button("Starta spel");
        startaSpelKnapp.setOnAction(event -> handleStartGame(anchorPane)); // refererar till handleStartGame
        startaSpelKnapp.setLayoutX(350); //shifted the button a bit to the center
        startaSpelKnapp.setLayoutY(310); //moved the button along the y-axis as it was overlapping with the Grid
        anchorPane.getChildren().addAll(ownGameBoard,opponentBoard, startaSpelKnapp);

        Scene scene = new Scene(anchorPane, 800, 450);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private GridPane createSpelplan() {
       GridPane gridPane = new GridPane();
       // Battleship battleship = new Battleship();
        battleship.shipPlacement();

        // problemet är att kartan & skepp existerar endast i objektet battleship
        int size = 10;
        char[] letters = "ABCDEFGHIJ".toCharArray(); // added char array
        for (int rad = 0; rad < battleship.mapSizeY; rad++) {
            for (int kolumn = 0; kolumn < battleship.mapSizeX; kolumn++) {
                Rectangle pane = new Rectangle(22, 22);
                
                pane.setFill(Color.rgb(0,204,204));
                pane.setStroke(Color.BLACK);


                pane.setOnMouseEntered(event -> {
                    pane.setFill(Color.rgb(0,0,112));
                });
                pane.setOnMouseExited(event -> {
                    pane.setFill(Color.rgb(0, 204, 204));
                });
                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(pane);
                String temp[][] = battleship.getMap();
                Label label = new Label();

                if(!Objects.equals(temp[rad][kolumn], "s")) {
                    label.setText(temp[rad][kolumn]);

                }
                label.setOnMouseEntered(event -> {
                    pane.setFill(Color.rgb(0,0,112));
                });
                label.setOnMouseExited(event -> {
                    pane.setFill(Color.rgb(0, 204, 204));
                });
                stackPane.getChildren().addAll(label);
                stackPane.setOnMouseEntered(event -> {
                    pane.setFill(Color.rgb(0,0,112));
                });
                stackPane.setOnMouseExited(event -> {
                    pane.setFill(Color.rgb(0, 204, 204));
                });

                gridPane.add(stackPane, kolumn + 1, rad + 1); // Shifted by 1 to make space for labels
            }
        }
        /*for (int rad = 0; rad < size; rad++) {
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
        }*/


        // Add letters and numbers as labels
        for (int i = 0; i < size; i++) {
            Text columnLabel = new Text(Integer.toString(i));
            Text rowLabel = new Text(Character.toString(letters[i]));
            gridPane.add(columnLabel, i + 1, 0); // Numbers on x-axis
            gridPane.add(rowLabel, 0, i + 1);    // Letters on y-axis
        }

        return gridPane;
    }

    public void setBattleship(Battleship battleship) {
        this.battleship = battleship;

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



    public void externalUpdateMethod(char action, int x, int y) {
        // Directly on JavaFX thread, so no need for Platform.runLater()
        updateOpponentBoard(action);
        updateOwnGameBoard(x, y); // This assumes updates for both boards are needed
    }


    public void updateOpponentBoard(char action) {

        StackPane pane = (StackPane) opponentBoard.getChildren().get(lastYShot * boardSize + lastXShot);
        Rectangle rectangle = (Rectangle) pane.getChildren().get(0);
        if (action == 'm') {
            rectangle.setFill(Color.CADETBLUE);
        } else if (action == 'h') {
            rectangle.setFill(Color.RED);
        } else if (action == 's') {
            rectangle.setFill(Color.BLACK);
        }

    }

    public void setAction(char h) {

        this.action = h;
    }

    public void updateOwnGameBoard(Integer y, Integer x) {

        StackPane pane = (StackPane) ownGameBoard.getChildren().get(y * boardSize + x);
        Rectangle rectangle = (Rectangle) pane.getChildren().get(0);
        String state = battleship.getMap()[y][x];
        if ("hit".equals(state)) {
            rectangle.setFill(Color.RED);
        } else if ("sunk".equals(state)) {
            rectangle.setFill(Color.BLACK);
        }
    }

    public void storeLastShot(int randomY, int randomX) {

        this.lastYShot = randomY;
        this.lastXShot = randomX;

    }
}

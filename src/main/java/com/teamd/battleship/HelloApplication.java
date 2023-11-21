package com.teamd.battleship;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;


public class HelloApplication extends Application {
    private Stage primaryStage;
    private String[][] opponentMap = new String[10][10];
    private int boardSize = 10;
    private GridPane playerBoard;
    private GridPane opponentBoard;
    private int lastYShot;
    private int lastXShot;
    private Thread thread;
    private Battleship battleship;



    public static void main(String[] args) {
        launch();
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        String appMode = System.getProperty("appMode");
        battleship = new Battleship();
        battleship.setHelloApplication(this);
        if (appMode=="klient") {
            Client client = new Client();
            battleship.setClient(client);
            client.setHelloApplication(this);
            client.setBattleShip(battleship);

            Platform.runLater(()->{FirstScene();});
            thread = new Thread(() -> {
                client.connect();
            });


        }else {
            Server server = new Server();
            battleship.setServer(server);
            server.setHelloApplication(this);
            server.setBattleShip(battleship);

            Platform.runLater(()->{FirstScene();});
            thread = new Thread(()->{
                server.connect();});

        }
        this.primaryStage = primaryStage;
        battleship.shipPlacement();
    }

    private void FirstScene() {
        String appMode = System.getProperty("appMode");
        primaryStage.setTitle("BattleShip "+appMode);

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
        startButton.setOnAction(event ->{ Platform.runLater(()->SecondScene());});
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
        String appMode = System.getProperty("appMode");
        primaryStage.setTitle("BattleShip "+appMode);

        AnchorPane anchorPane = new AnchorPane();

        playerBoard = ownPlayerBoard(boardSize);

        opponentBoard = opponentPlayerBoard(boardSize);

        PositionGameBoards(playerBoard, opponentBoard);

        Button startaSpelKnapp = new Button("Starta spel");
        startaSpelKnapp.setOnAction(event -> thread.start());

        Button chooseDelayButton = new Button ("Ändra fördröjning");
        Platform.runLater(()-> { chooseDelayButton.setOnAction(event -> handleDelay(anchorPane));});


        layoutStartGameButton(startaSpelKnapp);
        layoutChooseDelayButton(chooseDelayButton);


        Text playerjag = new Text("Egen spelplan");
        playerjag.setLayoutX(165);
        playerjag.setLayoutY(35);
        playerjag.setStyle("-fx-fill: black; -fx-font-size: 25; -fx-font-weight: bold; -fx-font-style: italic;");

        if (appMode.equals("klient")){
            Text playerMotståndare = new Text("Motståndarens spelplan");
            playerMotståndare.setLayoutY(35);
            playerMotståndare.setLayoutX(455);
            playerMotståndare.setStyle("-fx-fill: black; -fx-font-size: 25; -fx-font-weight: bold; -fx-font-style: italic;");

            anchorPane.getChildren().addAll(playerBoard, opponentBoard, startaSpelKnapp, chooseDelayButton,playerjag, playerMotståndare);
        } else {
            Text playerMotståndare = new Text("Motståndarens spelplan");
            playerMotståndare.setLayoutY(35);
            playerMotståndare.setLayoutX(455);
            playerMotståndare.setStyle("-fx-fill: black; -fx-font-size: 25; -fx-font-weight: bold; -fx-font-style: italic;");

            anchorPane.getChildren().addAll(playerBoard, opponentBoard, startaSpelKnapp, chooseDelayButton,playerjag, playerMotståndare);
        }


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
    private void layoutChooseDelayButton(Button startaSpelKnapp) { //gör det enklare
        startaSpelKnapp.setLayoutX(150);
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

                gridPane.add(pane, kolumn + 1, rad + 1); // Shifted by 1 to make space for labels
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
        Rectangle rectangle = (Rectangle) opponentBoard.getChildren().get(lastYShot*boardSize+lastXShot);

        if (action=='m'){
            rectangle.setFill(Color.FLORALWHITE);

        }
        else if (action=='h'){
            rectangle.setFill(Color.RED);

        }
        else if (action=='s'){
            rectangle.setFill(Color.RED);

        }
    }

    public void updateOwnGameBoard(int y, int x, List<Integer> listOfCoordinates) {
        String[][] newMap = battleship.getMap();
        if (newMap[y][x].equals("hit")) {
            ((Rectangle) playerBoard.getChildren().get(y * boardSize + x)).setFill(Color.RED);
        } else if (newMap[y][x].equals("sunk")) {
            ((Rectangle) playerBoard.getChildren().get(y * boardSize + x)).setFill(Color.BLACK);
            for (int i = 0; i < listOfCoordinates.size(); i += 2) {
                int yy = listOfCoordinates.get(i);
                int xx = listOfCoordinates.get(i + 1);
                ((Rectangle) playerBoard.getChildren().get(yy * boardSize + xx)).setFill(Color.BLACK);

            }
        }
    }





    private void handleDelay(AnchorPane anchorPane) {



        TextField delayTextField = new TextField();
        delayTextField.setPromptText("Ange fördröjning i sekunder");
        delayTextField.setLayoutX(150);
        delayTextField.setLayoutY(340);
        delayTextField.setPrefSize(220, 20);
        delayTextField.setStyle("-fx-prompt-text-fill: red;");
        delayTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                String delayAsString = delayTextField.getText();
                try{
                    long delay = Long.parseLong(delayAsString);
                    battleship.setDelay(delay);
                    delayTextField.setVisible(false);
                    System.out.println("Fördröjning ändrat till "+delayAsString+" sekunder");
                } catch (Exception e){
                    System.out.println("Fel inmatning, försök igen. Heltal krävs utan mellanslag eller liknande.");
                }

            }
        });


        anchorPane.getChildren().addAll(delayTextField);
    }
}
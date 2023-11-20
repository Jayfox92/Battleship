package com.teamd.battleship;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.Random;

public class TestOfThread extends Application {

    private static final int GRID_SIZE = 10;
    private Rectangle[][] gridRectangles = new Rectangle[GRID_SIZE][GRID_SIZE];

    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        initializeGrid(grid);

        Scene scene = new Scene(grid, 300, 300);
        stage.setTitle("Battleship Game");
        stage.setScene(scene);
        stage.show();

        // Simulate game state changes
        simulateGameStateChanges();
    }

    private void initializeGrid(GridPane grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Rectangle rect = new Rectangle(20, 20, Color.LIGHTBLUE);
                rect.setStroke(Color.BLACK);
                grid.add(rect, j, i);
                gridRectangles[i][j] = rect;
            }
        }
    }

    private void simulateGameStateChanges() {
        Thread gameThread = new Thread(() -> {
            Random random = new Random();
            try {
                while (true) {
                    int x = random.nextInt(GRID_SIZE);
                    int y = random.nextInt(GRID_SIZE);

                    // Mock game state change
                    String gameState = random.nextBoolean() ? "h" : "m";

                    // Update UI
                    updateCell(x, y, gameState);

                    // Pause for a bit to simulate time between turns
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        gameThread.start();
    }

    private void updateCell(int x, int y, String gameState) {
        javafx.application.Platform.runLater(() -> {
            Color color;
            switch (gameState) {
                case "h":
                    color = Color.RED; // Hit
                    break;
                case "m":
                    color = Color.BLUE; // Miss
                    break;
                default:
                    color = Color.LIGHTBLUE; // Default (Water)
            }
            gridRectangles[y][x].setFill(color);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
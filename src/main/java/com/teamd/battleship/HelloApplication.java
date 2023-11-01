package com.teamd.battleship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
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
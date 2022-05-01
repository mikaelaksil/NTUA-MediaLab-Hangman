package com.example.medialab;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.awt.*;
import java.io.IOException;

public class HangmanApp extends Application {
    //Label numberOfWords = new Label();
    //Button chooseChar = new Button();

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(HangmanApp.class.getResource("/hangman.fxml"));
        //Parent content = loader.load();

        FXMLLoader fxmlLoader = new FXMLLoader(HangmanApp.class.getResource("hangman.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);


        stage.setTitle("MediaLab " + "Hangman");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }


}
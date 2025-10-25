package com.voyydless.pomodoro.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PomodoroApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Timer Test");

        StackPane root = new StackPane();
        root.getChildren().add(new javafx.scene.control.Label("This is a test!"));

        Scene scene = new Scene(root, 400, 300);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

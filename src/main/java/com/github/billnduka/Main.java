package com.github.billnduka; // Your groupId

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane; // Or a more suitable layout
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class Main extends Application { // Main class must extend Application

    @Override
    public void start(Stage primaryStage) throws Exception { // The start method is where you set up your JavaFX UI

        // Create the root node (usually a layout like Pane, BorderPane, etc.)
        Pane root = new Pane(); // You can change this to another layout later

        // Create the scene (the content of the window)
        Scene scene = new Scene(root, 600, 400, Color.BISQUE); // Set your desired window size

        Image icon = new Image(getClass().getResourceAsStream("/image.png"));


        primaryStage.getIcons().add(icon);
        // Set the title of the window
        primaryStage.setTitle("Brick Breaker");

        // Set the scene of the stage (window)
        primaryStage.setScene(scene);

        // Show the stage (make the window visible)
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args); // Use launch() to start the JavaFX application
    }
}
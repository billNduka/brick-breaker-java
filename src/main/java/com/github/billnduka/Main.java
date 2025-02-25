package com.github.billnduka; // Your groupId

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.*;
import javafx.scene.layout.Pane; 
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.text.Font;


public class Main extends Application { 
    private static final double WIDTH = 500.0;
    private static final double HEIGHT = 500.0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        

        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT); 
        Image icon = new Image(getClass().getResourceAsStream("/image.png"));
        Canvas canvas = new Canvas(HEIGHT, WIDTH);
        GraphicsContext context = canvas.getGraphicsContext2D();

        context.setFill(Color.BISQUE);
        context.fillRect(WIDTH / 2 - 40, HEIGHT - 30, 80, 10);

        primaryStage.getIcons().add(icon);
        root.getChildren().add(canvas);
        canvas.relocate(0, 0);

        scene.setFill(Color.BLACK);
        context.setFill(Color.BISQUE);
        context.fillRect(WIDTH / 2 - 40, HEIGHT - 20, 80, 10);
        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(scene);

        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args); // Use launch() to start the JavaFX application
    }
}
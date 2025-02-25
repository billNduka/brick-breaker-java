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


public class Main extends Application 
{ 
    private static final double WIDTH = 500.0;
    private static final double HEIGHT = 500.0;
    static final double initX = WIDTH / 2; 
    static final double initY = HEIGHT - 15;

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH * 1.1, HEIGHT * 1.1); 
        Image icon = new Image(getClass().getResourceAsStream("/image.png"));
        Canvas canvas = new Canvas(HEIGHT, WIDTH);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Ball ball = new Ball(initX, initY, Color.WHITE, new double[] {1, 3});


        gc.setFill(Color.BISQUE);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        ball.drawBall(gc);


        
        root.getChildren().add(canvas);

        
        
        
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static class Ball
    {
        static final double radius = 15.0;
        double xPos, yPos;
        Color ballColor;
        double[] velocity = {0, 0};

        public Ball(double x, double y, Color color, double[] veloc)
        {
            xPos = x;
            yPos = y; 
            ballColor = color;
            velocity = veloc;
        }

        private void drawBall(GraphicsContext gc)
        {
            gc.setStroke(this.ballColor);
            gc.setLineWidth(3);
            gc.strokeOval(this.xPos, this.yPos, Ball.radius, Ball.radius);
        }

        private void updateBall()
        {

        }
        
    }

    public static void main(String[] args) 
    {
        launch(args); // Use launch() to start the JavaFX application
    }
}
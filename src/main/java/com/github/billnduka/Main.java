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
import javafx.animation.AnimationTimer;
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
        Ball ball = new Ball(initX, initY, Color.WHITE, new double[] {5, 5});
        Block block = new Block(100.0, 50.0, Color.WHITESMOKE);


        clearScreen(gc);
        ball.drawBall(gc);
        block.drawBlock(gc);

        ball.updateBall(canvas, gc, ball, block);
        
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

        private void updateBall(Canvas canvas, GraphicsContext gc, Ball ball, Block block)
        {
            AnimationTimer timer = new AnimationTimer()
            {
                long lastUpdate;
                private static final int TARGET_FPS = 30;  // Desired FPS
                private static final long INTERVAL = 1_000_000_000 / TARGET_FPS; 
                @Override
                public void handle(long now)
                {
                    if (now - lastUpdate >= INTERVAL)
                    {
                        lastUpdate = now;
                        clearScreen(gc);

                        if(ball.xPos + 3 <= 0 || ball.xPos + 3 >= canvas.getWidth())
                        {
                            ball.velocity[0] = -ball.velocity[0];
                        }
                        if(ball.yPos + 3 <= 0 || ball.yPos + 3 >= canvas.getHeight())
                        {
                            ball.velocity[1] = -ball.velocity[1];
                        }

                        ball.xPos -= ball.velocity[0];
                        ball.yPos -= ball.velocity[1];

                        ball.drawBall(gc);
                        block.drawBlock(gc);
                    }
                }

            };

            timer.start();

        }
        
    }

    private static class Block
    {
        double xPos, yPos;
        Color blockColor;
        static double height = 15.0;
        static double width = 45.0;
        static double arcWidth = 3; 
        static double arcHeight = 3;

        public Block(double x, double y, Color color)
        {
            xPos = x;
            yPos = y; 
            blockColor = color;
        }
        private void drawBlock(GraphicsContext gc)
        {
            gc.setStroke(this.blockColor);
            gc.setLineWidth(3);
            gc.strokeRoundRect(this.xPos, this.yPos, width, height, arcWidth, arcHeight);
        };

    }

    public static void clearScreen(GraphicsContext gc)
    {
        gc.setFill(Color.BISQUE);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
    }

    public static void main(String[] args) 
    {
        launch(args); // Use launch() to start the JavaFX application
    }
}
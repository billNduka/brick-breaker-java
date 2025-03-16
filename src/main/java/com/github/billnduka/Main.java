package com.github.billnduka; // Your groupId

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.*;
import javafx.scene.layout.Pane; 
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;
// import javafx.scene.text.Text;
// import javafx.scene.text.Font;



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
        Ball ball = new Ball(initX, initY, Color.WHITE, new double[] {2, 3});
        Block[] blocks = new Block[10];

        for(int i = 1; i <= blocks.length; i ++)
        {
            blocks[i - 1] = new Block(40 * i, 30 + (60 * (i % 2)), Color.WHITESMOKE);
        }


        clearScreen(gc);
        ball.drawBall(gc);
        for(int i = 0; i < blocks.length; i ++)
        {
            blocks[i].drawBlock(gc);
        }
        

        gameLoop(canvas, gc, ball, blocks);
        

        root.getChildren().add(canvas);        
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static class Ball
    {
        static final double radius = 12.0;
        static final double lineWidth = 3;
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
            gc.setLineWidth(lineWidth);
            gc.strokeOval(this.xPos, this.yPos, Ball.radius, Ball.radius);
        }

        private void updateBall(Canvas canvas)
        {
            //Collision with canvas borders
            if(this.xPos + 3 <= 0 || this.xPos + 3 >= canvas.getWidth())
            {
                this.velocity[0] = -this.velocity[0];
            }
            if(this.yPos + 3 <= 0 || this.yPos + 3 >= canvas.getHeight())
            {
                this.velocity[1] = -this.velocity[1];
            }

            this.xPos -= this.velocity[0];
            this.yPos -= this.velocity[1];
        }
        
    }

    private static class Block
    {
        double xPos, yPos;
        Color blockColor;
        static double height = 20.0;
        static double width = 50.0;
        static double arcWidth = 3; 
        static double arcHeight = 3;
        static double lineWidth = 3;
        boolean broken = false;

        public Block(double x, double y, Color color)
        {
            xPos = x;
            yPos = y; 
            blockColor = color;
        }
        private void drawBlock(GraphicsContext gc)
        {
            gc.setStroke(this.blockColor);
            gc.setLineWidth(Block.lineWidth);
            gc.strokeRoundRect(this.xPos, this.yPos, width, height, arcWidth, arcHeight);
        };
        private void checkCollision(Ball ball) 
        {
            double northPoint = ball.yPos;
            double southPoint = ball.yPos + 2 * Ball.radius;
            double eastPoint = ball.xPos + 2 * Ball.radius;
            double westPoint = ball.xPos;
        
            final double BUFFER = 0.5; // Small threshold to prevent floating point errors
        
            if (
                // Collision from bottom right
                ((westPoint > xPos) && (westPoint < xPos + width) && (northPoint <= yPos + height + BUFFER) && (northPoint > yPos + height - BUFFER)) ||
                // COllision from bottom left
                ((eastPoint > xPos) && (eastPoint < xPos + width) && (northPoint <= yPos + height + BUFFER) && (northPoint > yPos + height - BUFFER)) ||
                // Collision from top right
                ((westPoint > xPos) && (westPoint < xPos + width) && (southPoint >= yPos - BUFFER) && (southPoint < yPos + BUFFER)) ||
                // Collision from top left
                ((eastPoint > xPos) && (eastPoint < xPos + width) && (southPoint >= yPos - BUFFER) && (southPoint < yPos + BUFFER))
            ) 
            {
                ball.velocity[1] = -ball.velocity[1];
                ball.yPos += ball.velocity[1];
                this.broken = true;
            }
            if (
                //Collision from bottom right
                ((northPoint > yPos) && (northPoint < yPos + height) && (westPoint < xPos + width + BUFFER) && (westPoint > xPos) ) ||
                //Collision from top right
                ((southPoint > yPos) && (southPoint < yPos + height) && (westPoint < xPos + width + BUFFER) && (westPoint > xPos) ) ||
                //Collision from bottom left
                ((northPoint > yPos) && (northPoint < yPos + height) && (eastPoint < xPos + width) && (eastPoint > xPos - BUFFER)) ||
                //Collision from bottom left
                ((southPoint > yPos) && (southPoint < yPos + height) && (eastPoint < xPos + BUFFER) && (eastPoint > xPos - BUFFER))
                ) 
            {
                ball.velocity[0] = -ball.velocity[0];
                ball.xPos += ball.velocity[0];
                this.broken = true;
            }
        }
         
    }

    public static void clearScreen(GraphicsContext gc)
    {
        gc.setFill(Color.BISQUE);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
    }


    public static void gameLoop(Canvas canvas, GraphicsContext gc, Ball ball, Block[] blocks)
    {
        AnimationTimer timer = new AnimationTimer()
            {
                @Override
                public void handle(long now)
                {
                    clearScreen(gc);

                    //Check collision                    

                    ball.updateBall(canvas);
                    
                    for(int i = 0; i < blocks.length; i ++)
                    {
                        if(!blocks[i].broken)
                        {
                        blocks[i].checkCollision(ball);
                        }
                    }

                    ball.drawBall(gc);
                    for(int i = 0; i < blocks.length; i ++)
                    {
                        if(!blocks[i].broken)
                        {
                            blocks[i].drawBlock(gc);
                        }
                    }
                
                }

            };

            timer.start();
    }

    public static void main(String[] args) 
    {
        launch(args); // Use launch() to start the JavaFX application
    }
}
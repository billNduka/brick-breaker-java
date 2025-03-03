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
        Ball ball = new Ball(initX, initY, Color.WHITE, new double[] {5, 7});
        Block[] blocks = new Block[1];
        blocks[0] = new Block(100.0, 50.0, Color.WHITESMOKE);


        clearScreen(gc);
        ball.drawBall(gc);
        blocks[0].drawBlock(gc);

        gameLoop(canvas, gc, ball, new Block(100.0, 50.0, Color.WHITESMOKE));
        

        root.getChildren().add(canvas);        
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static class Ball
    {
        static final double radius = 15.0;
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
        static double height = 15.0;
        static double width = 45.0;
        static double arcWidth = 3; 
        static double arcHeight = 3;
        static double lineWidth = 3;

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
            //Collision with top of block
            if(ball.yPos + 2 * Ball.radius + Ball.lineWidth >= this.yPos + Block.lineWidth && 
                ball.yPos + 2 * Ball.radius + Ball.lineWidth <= this.yPos &&
                ball.xPos >= this.xPos && ball.xPos + 2 * Ball.radius + Ball.lineWidth <= this.xPos + Block.width + Block.lineWidth)
            {
                ball.velocity[0] = -ball.velocity[0];
                ball.velocity[1] = -ball.velocity[1];
            }
        }

    }

    public static void clearScreen(GraphicsContext gc)
    {
        gc.setFill(Color.BISQUE);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
    }


    public static void gameLoop(Canvas canvas, GraphicsContext gc, Ball ball, Block block)
    {
        AnimationTimer timer = new AnimationTimer()
            {
                @Override
                public void handle(long now)
                {
                    clearScreen(gc);

                    //update ball
                    
                    ball.updateBall(canvas);

                    ball.drawBall(gc);
                    block.drawBlock(gc);
                
                }

            };

            timer.start();
    }

    public static void main(String[] args) 
    {
        launch(args); // Use launch() to start the JavaFX application
    }
}
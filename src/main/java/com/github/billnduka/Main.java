package com.github.billnduka; 

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane; 
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;



public class Main extends Application 
{ 
    private static final double WIDTH = 500.0;
    private static final double HEIGHT = 500.0;
    static final double initX = WIDTH / 2; 
    static final double initY = HEIGHT - 50;

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
        Paddle paddle = new Paddle(WIDTH / 2.0);

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
        paddle.drawPaddle(gc);
        

        gameLoop(canvas, gc, ball, blocks, paddle);
        

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

        private void updateBall(Canvas canvas, Paddle paddle)
        {
            //Collision with left canvas borders
            if(xPos < 0)
            {
                xPos = 0;
                velocity[0] = -velocity[0];
            }
            //Collision with right canvas border
            if(xPos + 2 * radius > WIDTH)
            {
                xPos = WIDTH - 2 * radius;
                velocity[0] = -velocity[0];
            }
            //Collision with top canvas border
            if(yPos < 0)
            {
                yPos = 0;
                velocity[1] = -velocity[1];
            }
            //Collision with bottom canvas border
            if(yPos + radius > HEIGHT)
            {
                yPos = HEIGHT -  radius;
                this.velocity[1] = -this.velocity[1];
            }
            //Collision with top of paddle
            if((yPos +  radius > HEIGHT - Paddle.height) && (((xPos > paddle.xPos) && (xPos < paddle.xPos + Paddle.width)) || ((xPos +  radius < paddle.xPos + Paddle.width) && (xPos +  radius > paddle.xPos))))
            {
                yPos = (HEIGHT - Paddle.height) - 2 * radius;
                this.velocity[0] = -this.velocity[0];
                this.velocity[1] = -this.velocity[1];
            }

            xPos -= this.velocity[0];
            yPos -= this.velocity[1];
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

    private static class Paddle
    {
        double xPos;
        static Color color = Color.WHITESMOKE;
        static double height = 15.0;
        static double width = 50.0;
        static double arcWidth = 5; 
        static double arcHeight = 3;
        static double lineWidth = 3;
        static double yPos = HEIGHT - (height + lineWidth);

        public Paddle(double xPos)
        {
            this.xPos = xPos;
        }

        private void updatePaddle()
        {
            
        }
        private void drawPaddle(GraphicsContext gc)
        {
            gc.setStroke(color);
            gc.setLineWidth(lineWidth);
            gc.strokeRoundRect(this.xPos, yPos, width, height, arcWidth, arcHeight);
        }
    }

    public static void clearScreen(GraphicsContext gc)
    {
        gc.setFill(Color.BISQUE);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
    }


    public static void gameLoop(Canvas canvas, GraphicsContext gc, Ball ball, Block[] blocks, Paddle paddle)
    {
        AnimationTimer timer = new AnimationTimer()
            {
                @Override
                public void handle(long now)
                {
                    clearScreen(gc);

                    //Check collision                    

                    ball.updateBall(canvas, paddle);
                    
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

                    paddle.drawPaddle(gc);
                
                }

            };

            timer.start();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
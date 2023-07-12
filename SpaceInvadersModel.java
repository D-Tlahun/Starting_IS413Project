package com.example.is413project;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.concurrent.atomic.AtomicBoolean;

public class SpaceInvadersModel {

    private Pane root;
    private double t = 0;
    private final long timeel = 100;
    private Invader_Move player;
    private Invader_Move invader;
    private int invaderDirection = 1;
    private boolean gameOver = false;

    public SpaceInvadersModel() {
        root = new Pane();
        root.setPrefSize(600, 800);

        player = new Invader_Move(300, 750, 50, 40, "player", Color.GREEN);
        invader = new Invader_Move(90, 150, 20, 10, "invader", Color.BLACK);

        root.getChildren().addAll(player, invader);
    }

    public Pane getRoot() {
        return root;
    }

    public void update() {
        t += 1.6;

        if (t > 2) {
            double newX = invader.getTranslateX() + invaderDirection * 5;
            double invaderWidth = invader.getWidth();
            double stageWidth = root.getWidth();

            // Check if the invader reaches the right boundary
            if (newX + invaderWidth >= stageWidth) {
                newX = stageWidth - invaderWidth;
                invaderDirection = -1; // Change movement direction to left
            }

            // Check if the invader reaches the left boundary
            if (newX <= 0) {
                newX = 0;
                invaderDirection = 1; // Change movement direction to right
            }

            invader.setTranslateX(newX);
            t = 0;
        }

        if (player.intersects(invader)) {
            player.setDead(true);
            invader.setDead(true);
        }



        if (invader.isDead()) {
            gameOver = true;
        }
        root.getChildren().removeIf(n -> {
            if (n instanceof Invader_Move) {
                Invader_Move s = (Invader_Move) n;
                return s.isDead();
            }
            return false;
        });
    }

    public void displayGameOverMessage() {
        if (gameOver) {
            Text gameOverText = new Text("Game Over");
            gameOverText.setFont(Font.font(48));
            gameOverText.setFill(Color.RED);
            gameOverText.setTranslateX(250);
            gameOverText.setTranslateY(400);

            root.getChildren().add(gameOverText);
        }
    }

    public void movePlayerLeft() {
        if (player.getTranslateX() > 0) {
            player.moveLeft();
        }
    }

    public void movePlayerRight() {
        double maxX = root.getWidth() - player.getWidth();
        if (player.getTranslateX() < maxX) {
            player.moveRight();
        }
    }

    public void shootPlayer() {
        shoot(player);
    }

    public void startGameLoop(AtomicBoolean moveLeft, AtomicBoolean moveRight) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (moveRight.get()) {
                    movePlayerRight();
                } else if (moveLeft.get()) {
                    movePlayerLeft();
                }
                update();
                displayGameOverMessage(); // Call the method to display the "Game Over" message
            }
        };

        timer.start();
    }

    private void shoot(Invader_Move who) {
        Invader_Move missile = new Invader_Move(
                who.getTranslateX() + who.getWidth() / 2 - 2,
                who.getTranslateY(),
                5, 10,
                "missile",
                Color.RED
        );

        root.getChildren().add(missile);

        AnimationTimer missileTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                missile.moveUp();
                if (missile.intersects(invader)) {
                    invader.setDead(true);
                    missile.setDead(true);
                }

                if (missile.isDead()) {
                    root.getChildren().remove(missile);
                    stop();
                }
            }
        };

        missileTimer.start();
    }
}

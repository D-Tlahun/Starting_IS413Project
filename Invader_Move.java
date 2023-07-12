package com.example.is413project;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Invader_Move extends Rectangle  {
    private boolean dead = false;
    private final String type;

    public Invader_Move(double x, double y, double width, double height, String type, Color color) {
        super(width, height, color);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getType() {
        return type;
    }

    public void moveLeft() {
        setTranslateX(getTranslateX() - 5);
    }

    public void moveRight() {
        setTranslateX(getTranslateX() + 5);
    }

    public void moveUp() {
        setTranslateY(getTranslateY() - 5);
    }

    public void moveDown() {
        setTranslateY(getTranslateY() + 5);
    }

    public boolean intersects(Invader_Move other) {
        return getBoundsInParent().intersects(other.getBoundsInParent());
    }

    public void move(int i, double v) {
        double movementSpeed = 1.0; // Adjust the movement speed as needed
        double invaderWidth = getWidth();

        double newX = getTranslateX() + movementSpeed;

        // Check if the invader reaches the right boundary
        if (newX + invaderWidth >= getScene().getWidth()) {
            newX = getScene().getWidth() - invaderWidth;
            movementSpeed = -Math.abs(movementSpeed); // Reverse the movement direction
        }

        // Check if the invader reaches the left boundary
        if (newX <= 0) {
            newX = 0;
            movementSpeed = -Math.abs(movementSpeed); // Reverse the movement direction
        }

        setTranslateX(newX);
    }

}
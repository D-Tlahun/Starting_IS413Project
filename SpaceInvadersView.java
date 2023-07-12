package com.example.is413project;

import javafx.scene.LightBase;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

public class SpaceInvadersView {

    private Stage stage;
    private SpaceInvadersModel model;

    public SpaceInvadersView(Stage stage, SpaceInvadersModel model) {
        this.stage = stage;
        this.model = model;
    }

    public void initialize() {
        Scene scene = new Scene(model.getRoot());



        AtomicBoolean moveLeft = new AtomicBoolean(false);
        AtomicBoolean moveRight = new AtomicBoolean(false);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    moveRight.set(true);
                    break;
                case RIGHT:
                    moveLeft.set(true);
                    break;
                case SPACE:
                    model.shootPlayer();
                    break;
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT:
                    moveLeft.set(false);
                    break;
                case RIGHT:
                    moveRight.set(false);
                    break;
            }
        });

        model.startGameLoop( moveRight, moveLeft);

        stage.setScene(scene);
        stage.show();
    }
}

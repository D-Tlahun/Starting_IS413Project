package com.example.is413project;

import javafx.application.Application;
import javafx.stage.Stage;

public class SpaceInvadersController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Space Invader Game");
        SpaceInvadersModel model = new SpaceInvadersModel();
        SpaceInvadersView view = new SpaceInvadersView(stage, model);
        view.initialize();

    }
}

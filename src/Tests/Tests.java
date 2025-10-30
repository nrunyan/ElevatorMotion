package Tests;

import GUI.ElevatorGUI;
import Simulation.MotionSimulation;
import Util.Direction;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;

public class Tests extends Application {

    private static final double STOP_DISTANCE = 1.5; // meters

    @Override
    public void start(Stage primaryStage) {

    }

    public static void main(String[] args) {
        launch(args);
    }
}

package Tests;

import GUI.ElevatorGUI;
import Hardware.Motor;
import Simulation.MotionSimulation;
import Util.Direction;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates the elevator moving up and down several times,
 * stopping at each floor, without freezing the GUI.
 */
public class Tests extends Application {

    @Override
    public void start(Stage primaryStage) {
        MotionSimulation sim = new MotionSimulation(.5);
        Thread simThread = new Thread(sim);
        simThread.setDaemon(true);
        simThread.start();

        ElevatorGUI gui = new ElevatorGUI(
                sim.getSensors(),
                sim.get_sensor_pos_HashMap(),
                sim.getElevator(),
                sim.getMotor()
        );
        gui.getPrimaryStage(primaryStage);

        Motor motor = new Motor();
        motor.subscribe(sim);
        motor.set_direction(Direction.UP);
        motor.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

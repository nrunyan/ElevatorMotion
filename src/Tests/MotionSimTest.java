package Tests;

import GUI.ElevatorGUI;
import Hardware.Elevator;
import Hardware.Motor;
import Simulation.MotionSimulation;
import javafx.application.Application;
import javafx.stage.Stage;

public class MotionSimTest extends Application {
    Motor test_motor;

    @Override
    public void start(Stage primaryStage) {
        MotionSimulation sim = new MotionSimulation();
        test_motor = sim.getMotor();

        MotorControllerTest motorGuy = new MotorControllerTest(test_motor);
        Thread motorControllerGuyThread = new Thread(motorGuy);
        motorControllerGuyThread.start();

        Thread simThread = new Thread(sim);
        simThread.start();

        ElevatorGUI gui = new ElevatorGUI(sim.getSensors(), sim.get_sensor_pos_HashMap(), sim.getElevator(), sim.getMotor());
        gui.getPrimaryStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

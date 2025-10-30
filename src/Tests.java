import GUI.ElevatorGUI;
import Simulation.MotionSimulation;
import javafx.application.Application;
import javafx.stage.Stage;

public class Tests extends Application {

    @Override
    public void start(Stage primaryStage) {
        MotionSimulation sim = new MotionSimulation();
        Thread simThread = new Thread(sim);
        simThread.start();

        ElevatorGUI gui = new ElevatorGUI(sim.getSensors(), sim.get_sensor_pos_HashMap(), sim.getElevator(), sim.getMotor());
        gui.getPrimaryStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package Tests;

import GUI.ElevatorGUI;
import Hardware.Motor;
import Simulation.MotionSimulation;
import Util.Direction;
import javafx.application.Application;
import javafx.stage.Stage;

public class MotorControllerTest extends Application{

    private Motor myMotor;
    private boolean ON = true;
    MotionSimulation motionSimulation;
    public MotorControllerTest(Motor motor) {
        myMotor = motor;
        motionSimulation =new MotionSimulation(1);
        motor.subscribe(motionSimulation);
    }



    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        MotorControllerTest motorControllerTest = new MotorControllerTest(new Motor());

        Thread simThread = new Thread(motionSimulation);
        simThread.setDaemon(true);
        simThread.start();

        ElevatorGUI gui = new ElevatorGUI(
                motionSimulation.getSensors(),
                motionSimulation.get_sensor_pos_HashMap(),
                motionSimulation.getElevator(),
                motionSimulation.getMotor()
        );
        gui.getPrimaryStage(primaryStage);

    }
    public static void main(String[] args) {
        launch(args);
    }
}

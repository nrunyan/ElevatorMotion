package Tests;

import GUI.ElevatorGUI;
import Simulation.MotionSimulation;
import Util.Direction;
import javafx.application.Application;
import javafx.stage.Stage;

public class Tests extends Application implements Runnable {

    @Override
    public void start(Stage primaryStage) {

        //TODO make sure speed factor affects this part of sim
        double speedFactor =1;
        MotionSimulation sim = new MotionSimulation(speedFactor);
        Thread simThread = new Thread(sim);
        simThread.setDaemon(true); // ensures thread stops when app closes
        simThread.start();

        ElevatorGUI gui = new ElevatorGUI(
                sim.getSensors(),
                sim.get_sensor_pos_HashMap(),
                sim.getElevator(),
                sim.getMotor()
        );
        gui.getPrimaryStage(primaryStage);

        sim.setDirection(Direction.UP);

        sim.start();

        javafx.animation.Timeline timeline = new javafx.animation.Timeline();

        timeline.getKeyFrames().add(new javafx.animation.KeyFrame(
                javafx.util.Duration.seconds(5),
                e -> sim.stop()
        ));

        timeline.getKeyFrames().add(new javafx.animation.KeyFrame(
                javafx.util.Duration.seconds(7),
                e -> {
                    sim.setDirection(Direction.DOWN);
                    sim.start();
                }
        ));

        timeline.getKeyFrames().add(new javafx.animation.KeyFrame(
                javafx.util.Duration.seconds(12),
                e -> sim.stop()
        ));

        timeline.setCycleCount(1);

        timeline.play();
    }


    /**
     * Runs this operation.
     */
    @Override
    public void run() {

    }
}
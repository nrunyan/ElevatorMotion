package Tests;

import GUI.ElevatorGUI;
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


        int flors = 10;
        List<Integer> floorSequence = new ArrayList<>();
        for (int i = 0; i < flors; i++) floorSequence.add(i);
        for (int i = flors - 2; i > 0; i--) floorSequence.add(i);
        Timeline timeline = new Timeline();
        double time_at_Floor = 2.05;
        double time = 0;
        Direction dir = Direction.UP;
        for (int i = 0; i < floorSequence.size(); i++) {
            int floor = floorSequence.get(i);
            Direction direction = (i < flors - 1) ? Direction.UP : Direction.DOWN;
            //System.out.println(i+" < "+flors+ "Floor "+floor);
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(time),
                    e -> {
                        sim.setDirection(direction);
                        sim.start();
                    }
            ));
            time += time_at_Floor; //this is the time it took to reach the floor
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.seconds(time),
                    e -> sim.stop()
            ));
        }

        timeline.setCycleCount(Timeline.INDEFINITE); //so liek a while true that doesnt break everything!!!
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

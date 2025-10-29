package GUI;

import Elevator_Controler.MotionAPI;
import Util.Direction;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ElevatorGUI extends Application {


    /**
     * JOEL'S Util.Constants
     *
     * Height between two sensors on the same floor: 3.0 meters
     * Floor thickness: 1.0 meters
     * Maximum speed of the elevator: 1.0 meters per second
     * The distance the cabin travels while accelerating (from max speed to stop or vice versa): 1.5 meters
     * Time it takes for the motor to fully accelerate the cabin from max speed to stop or vice versa: 3.0 seconds
     * Acceleration of the motor (same for start and stop): 0.3333333333333333 meters per second^2
     */

    private MotionAPI motion_API = new MotionAPI();
    private Label direction_label = new Label("direction:null");
    private Label top_alignment_label = new Label("top_Aligned:null");
    private Label bottom_alignment_label = new Label("bottom_Aligned:null");
    private Rectangle elevator_car;
    private Rectangle floor;
    private static final double SENSOR_HEIGHT=3.0;
    private static final double FLOOR_THICKNESS=1.0;
    private static final double MAX_SPEED=1.0;
    private static final double ACCLERATION=.333;

    private static final int NUM_FLOORS=10;

    private static final double SHAFT_HEIGHT = (SENSOR_HEIGHT+FLOOR_THICKNESS)*NUM_FLOORS;
    private static final double CAR_HEIGHT = 3.0;
    private static final int SHAFT_WIDTH = 100;
    private static final double CAR_WIDTH=3.0;
    private static final double initialY=SHAFT_HEIGHT - CAR_HEIGHT;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Group 7's super sick gui");
        Pane shaftPane = new Pane();
        shaftPane.setPrefSize(SHAFT_WIDTH, SHAFT_HEIGHT);
        shaftPane.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");

        // CHANGE TO CONCAT GUI VIEWS INTO ONE
        elevator_car = new Rectangle(joelToJava(CAR_WIDTH), joelToJava(CAR_HEIGHT), Color.DARKGRAY);
        elevator_car.setLayoutX(10);
        elevator_car.setLayoutY(joelToJava(initialY));
        shaftPane.getChildren().add(elevator_car);

        Button upButton = new Button("Up");
        Button downButton = new Button("Down");
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");

        upButton.setOnAction(e -> {
            motion_API.set_direction(Direction.UP);
            redraw();
        });

        downButton.setOnAction(e -> {
            motion_API.set_direction(Direction.DOWN);
            redraw();
        });

        startButton.setOnAction(e -> {
            motion_API.start();
            move();
        });

        stopButton.setOnAction(e -> {
            motion_API.stop();
            redraw();
        });

        HBox buttonBox = new HBox(10, upButton, downButton, startButton, stopButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));


        VBox statusBox = new VBox(5, direction_label, top_alignment_label, bottom_alignment_label);
        statusBox.setAlignment(Pos.CENTER_LEFT);
        statusBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(shaftPane);
        root.setTop(buttonBox);
        root.setBottom(statusBox);

        Scene scene = new Scene(root, 300, 550);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    /**
     * Motion simulator
     */
    private void move() {
        Direction dir = motion_API.direction;
        int bottom=310;
        int top=10; //We might want to make thes global constants
//        // 1 second is 10^9
//        AnimationTimer animationTimer = new AnimationTimer() {
//
//        };
        if (dir == null) return;

        if (dir == Direction.UP) {
            // this is just a placeholder irl we want some animation timer here
            elevator_car.setLayoutY(Math.max(top, elevator_car.getLayoutY() - joelToJava(4.0)));
        } else if (dir == Direction.DOWN) {
            elevator_car.setLayoutY(Math.min(joelToJava(initialY), elevator_car.getLayoutY() + joelToJava(4.0)));
        }
        redraw();
    }

    /**
     * Any refresh actions here
     */
    private void redraw() {

        direction_label.setText("Util.Direction: " + motion_API.direction);
        top_alignment_label.setText("Top Aligned: " + motion_API.top_alignment());
        bottom_alignment_label.setText("Bottom Aligned: " + motion_API.bottom_alignment());
    }

    private double joelToJava(double meters){
        return meters*10;
    }
}

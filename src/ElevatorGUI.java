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
     * JOEL'S Constants
     *
     * Height between two sensors on the same floor: 3.0 meters
     * Floor thickness: 1.0 meters
     * Maximum speed of the elevator: 1.0 meters per second
     * The distance the cabin travels while accelerating (from max speed to stop or vice versa): 1.5 meters
     * Time it takes for the motor to fully accelerate the cabin from max speed to stop or vice versa: 3.0 seconds
     * Acceleration of the motor (same for start and stop): 0.3333333333333333 meters per second^2
     */

    private MotionAPI motionAPI = new MotionAPI();
    private Label directionLabel = new Label("direction:null");
    private Label topAlignmentLabel = new Label("top_Aligned:null");
    private Label bottomAlignmentLabel = new Label("bottom_Aligned:null");
    private Rectangle elevatorCar;
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
    private static final double initialY=SHAFT_HEIGHT - CAR_HEIGHT - 10;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Group 7's super sick gui");
        Pane shaftPane = new Pane();
        shaftPane.setPrefSize(SHAFT_WIDTH, SHAFT_HEIGHT);
        shaftPane.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");

        elevatorCar = new Rectangle(joelToJava(CAR_WIDTH), joelToJava(CAR_HEIGHT), Color.DARKGRAY);
        elevatorCar.setLayoutX(10);
        elevatorCar.setLayoutY(joelToJava(initialY));
        shaftPane.getChildren().add(elevatorCar);

        Button upButton = new Button("Up");
        Button downButton = new Button("Down");
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");

        upButton.setOnAction(e -> {
            motionAPI.setDirection(Direction.UP);
            redraw();
        });

        downButton.setOnAction(e -> {
            motionAPI.setDirection(Direction.DOWN);
            redraw();
        });

        startButton.setOnAction(e -> {
            motionAPI.start();
            move();
        });

        stopButton.setOnAction(e -> {
            motionAPI.stop();
            redraw();
        });

        HBox buttonBox = new HBox(10, upButton, downButton, startButton, stopButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));


        VBox statusBox = new VBox(5, directionLabel, topAlignmentLabel, bottomAlignmentLabel);
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
        Direction dir = motionAPI.direction;
        int bottom=310;
        int top=10; //We might want to make thes global constants
        if (dir == null) return;

        if (dir == Direction.UP) {
            // this is just a placeholder irl we want some animation timer here
            elevatorCar.setLayoutY(Math.max(top, elevatorCar.getLayoutY() - joelToJava(4.0)));
        } else if (dir == Direction.DOWN) {
            elevatorCar.setLayoutY(Math.min(joelToJava(initialY), elevatorCar.getLayoutY() + joelToJava(4.0)));
        }
        redraw();
    }

    /**
     * Any refresh actions here
     */
    private void redraw() {

        directionLabel.setText("Direction: " + motionAPI.direction);
        topAlignmentLabel.setText("Top Aligned: " + motionAPI.topAlignment());
        bottomAlignmentLabel.setText("Bottom Aligned: " + motionAPI.bottomAlignment());
    }

    private double joelToJava(double meters){
        return meters*10;
    }
}

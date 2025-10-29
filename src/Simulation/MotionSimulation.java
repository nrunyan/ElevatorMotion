package Simulation;

import Hardware.Elevator;
import Hardware.Motor;
import Hardware.Sensor;
import Util.Constants;
import Util.Direction;

import java.util.HashMap;

/**
 * This class simulates motion, for demonstration purposes
 * will be deleted later and replaced with working software
 */
//TODO: make actual test to demonstrate gui functionality
public class MotionSimulation implements Runnable{
    // Convert floor indicator to Sensor
    private HashMap<Integer, Sensor> sensor_HashMap =new HashMap<>();
    // Convert Sensors to y positions
    private HashMap<Integer, Double>  sensor_pos_Map = new HashMap<>();

    // The motor object (to be replaced by Hardware)
    private Motor motor;

    // The elevator object (for simulation purposes)
    private Elevator elevator;

    // The elevator's current speed
    private double current_speed=0.0;

    // 1 if accelerating, -1 if decelerating,
    private int accelerating_indicator  = 0;

    // Which direction the elevator is going
    private Direction direction;

    // The floor number associated (-1 when unset)
    private int top_idx = -1;
    private int bottom_idx = -1;

    // How long the thread sleeps before updating position, velocity, etc.
    private final int SLEEP_MILLIS = 375;

    // Top Level
    private final int MAX_IDX = 20;

    /**
     * Makes a motion simulation
     */
    public MotionSimulation(){
        motor=new Motor();
        elevator = new Elevator();

        // TODO initialize hashmaps, and sensors


    }

    /**
     *  motion simulation is runnable
     */
    @Override
    public void run() {
        while (true){
            if((current_speed < Constants.MAX_SPEED) && (current_speed > 0)) {
                // Accelerate
                current_speed += Constants.ACCELERATION *
                        from_millis_to_seconds(SLEEP_MILLIS) *
                        accelerating_indicator;
                elevator.set_y_position(elevator_delta_y() + elevator.getY_position());
            } else{
                // Constant speed cases
                if (accelerating_indicator < 0) {
                    // Stopped
                    current_speed = 0;
                    accelerating_indicator = 0;
                } else {
                    // Maxed out speed
                    current_speed = Constants.MAX_SPEED;
                    accelerating_indicator = 0;
                }
            }
            update_sensors();
            try {
                Thread.sleep(SLEEP_MILLIS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Update all the sensor objects, as to wether they are triggered or not
     * At most two sensors on at any time, use the position of the elevator
     * to set the sensors.
     */
    private void update_sensors(){
        // Get the positions of the elevator
        double y_pos_bottom = elevator.getY_position();
        double y_pos_top = elevator.upper_bound();

        // Update sensors based on position and direction
        switch (direction){
            case UP:
                // TODO: check this
                int sensor_above_idx = top_idx + 1;
                // Top sensor triggered
                if (sensor_above_idx != MAX_IDX && y_pos_top > sensor_pos_Map.get(sensor_above_idx)){
                    // Turn sensor above on
                    sensor_HashMap.get(sensor_above_idx).set_triggered(true);

                    // Turn bottom sensor off, if not already off
                    if(bottom_idx!=-1){
                        sensor_HashMap.get(bottom_idx).set_triggered(false);
                    }

                    // Update bottom and top floor indices
                    bottom_idx = top_idx;
                    top_idx = sensor_above_idx;
                    //TODO: snap elevator into place if stopped

                // bottom sensor untriggered
                } else if (bottom_idx!=-1 && sensor_pos_Map.get(bottom_idx) > y_pos_bottom){
                    // Turn off bottom sensor
                    sensor_HashMap.get(bottom_idx).set_triggered(false);

                    //set bottom id to none selected
                    bottom_idx =-1;

                }
                break;
            case DOWN:
                int sensor_bellow_idx= bottom_idx-1;
                if(sensor_bellow_idx!=-1&&y_pos_bottom<sensor_pos_Map.get(sensor_bellow_idx)){
                    //top sensor untriggered


                    sensor_HashMap.get(sensor_bellow_idx).set_triggered(true);

                    // Turn top sensor off, if not already off
                    if(top_idx!=-1){
                        sensor_HashMap.get(top_idx).set_triggered(false);
                    }

                    //update triggered floor positions
                    top_idx=bottom_idx;
                    bottom_idx=sensor_bellow_idx;
                    //TODO: if motor's off, snap elevator into place

                } else if (top_idx!=-1&& sensor_pos_Map.get(top_idx)>y_pos_top) {
                    // turn off top sensor
                    sensor_HashMap.get(top_idx).set_triggered(true);
                    top_idx=-1;
                }
            default:
                // Elevator not moving

        }

        for (int i: sensor_HashMap.keySet()){
            if(i>elevator.getY_position() && i < elevator.upper_bound()){
                sensor_HashMap.get(i).set_triggered(true);
            }else {
                sensor_HashMap.get(i).set_triggered(false);
            }
        }
    }

    /**
     * @param floor_indicator 0 is bottom of first floor, 1 is top of first
     *                        floor, 2 is bottom of second floor, etc.
     * @return the Sensor object associated
     */
    public Sensor get_sensor(int floor_indicator) {
        return sensor_HashMap.get(floor_indicator);
    }

    /**
     * @return Gets the elevators position based on current speed
     */
    private double elevator_delta_y() {
        return  current_speed * from_millis_to_seconds(SLEEP_MILLIS);
    }
    /**
     *  Handy converter
     * @param milliseconds the number of milliseconds
     * @return converted to seconds
     */
    private double from_millis_to_seconds(int milliseconds){
        return ((double)milliseconds)/1000;
    }

    public static void main(String[] args) {

    }
}

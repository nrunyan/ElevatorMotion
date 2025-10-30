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
public class MotionSimulation implements Runnable {
    // Convert floor indicator to Sensor
    private final HashMap<Integer, Sensor> sensor_HashMap =new HashMap<>();
    // Convert Sensors to y positions
    private final HashMap<Integer, Double>  sensor_pos_Map = new HashMap<>();

    // The motor object (to be replaced by Hardware)
    private final Motor motor;

    // The elevator object (for simulation purposes) also hardware
    private final Elevator elevator;

    // How long the thread sleeps before updating position, velocity, etc.
    private final int SLEEP_MILLIS = 100;

    // Top Level
    private final int MAX_SENSOR_IDX = 19;

    // The elevator's current speed
    private double current_speed = 0.0;

    // 1 if accelerating, -1 if decelerating,
    private int accelerating_indicator = 0;

    // Which direction the elevator is going
    private Direction direction = Direction.NULL;

    // The floor number associated (-1 when unset)
    private int top_idx = 1;
    private int bottom_idx = 0;

    private boolean at_start=true;
    private double speedFactor=1;

    /**
     * Makes a motion simulation
     */
    public MotionSimulation(double speedFactor){
        motor = new Motor();
        elevator = new Elevator();
        this.speedFactor=speedFactor;

        // Initializing the Hash Maps
        double y_pos = 0;
        for (int i = 0; i <= MAX_SENSOR_IDX; i++) {
            sensor_HashMap.put(i, new Sensor());

            if (i % 2 == 0){
                // Lower level sensor
                y_pos += Constants.FLOOR_THICKNESS;
            } else {
                // Upper level sensor
                y_pos += Constants.HEIGHT;
            }
            sensor_pos_Map.put(i, y_pos);
        }


    }

    /**
     *  motion simulation is runnable
     */
    @Override
    public void run() {
        update_sensors();
        boolean running=true;
        boolean sillyBoolean = true;

        double sillyTimeSorryVal = from_millis_to_seconds(SLEEP_MILLIS);

        while (running) {
            update_sensors();
            try {

               // System.out.println(elevator.getY_position() + ", " + elevator.upper_bound());

                motion_updater_slash_neg(sillyTimeSorryVal);
                update_sensors();

                // kinda like loop timeing
                Thread.sleep((long) ((int)SLEEP_MILLIS*speedFactor));

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        update_sensors();
    }

    private void motion_updater_slash_neg(double sillyTimeSorryVal) {
        if (accelerating_indicator != 0) {

            current_speed += Constants.ACCELERATION * sillyTimeSorryVal * accelerating_indicator;
        } else {
            //no acceleration go towards zero, so this is sorta a janky reuse of accleration indicator

            if (current_speed != 0.0) {
                double steppre = Constants.ACCELERATION * sillyTimeSorryVal * Math.signum(current_speed); //get the sig nif number of the current speed
                if (Math.abs(steppre) >= Math.abs(current_speed)) {
                    current_speed = 0.0;
                } else {
                    current_speed -= steppre;
                }
            }
        }

        //goes to max speed negative or positive account for going over so we dont have to worry about roundinf
        if (Math.abs(current_speed) > Constants.MAX_SPEED) {
            current_speed = Math.copySign(Constants.MAX_SPEED, current_speed);  // bless you math.copytime
        }

        //positive->up, negative-> down
        if (current_speed != 0.0) {
            double delta_Y = current_speed * sillyTimeSorryVal;
            //tell obsetcvers
            elevator.set_y_position(elevator.getY_position() + delta_Y);
        } else {
            //If we've come to a full stop and previously were decelerating, reset indicator
            if (accelerating_indicator < 0) {

                accelerating_indicator = 0;
            }
        }
    }

    private void update_sensors() {
        double top=-1;
        double bottom=-1;
        double yBottom = elevator.getY_position();
        double yTop = elevator.upper_bound();

        for (Integer idx : sensor_pos_Map.keySet()) {
            double sensorY = sensor_pos_Map.get(idx);

            if (sensorY+.5 >= yBottom && sensorY-.5 <= yTop) {
                sensor_HashMap.get(idx).set_triggered(true);
                if(bottom==-1){
                    bottom=sensorY;
                }else{
                    top =sensorY;
                }
            } else {
                sensor_HashMap.get(idx).set_triggered(false);
            }
        }
        if(motor.is_off()&&bottom>=0){
            elevator.set_y_position(bottom);
        }

    }



    /**
     * Update all the sensor objects, whether they are triggered or not
     * At most two sensors on at any time, use the position of the elevator
     * to set the sensors.
     */
    private void update_sensors2(){
        // Get the positions of the elevator

        //asks joel why this doesn't work if it's in the constructor
        //just leave it here for now, i think its a weird observer issue or something idk ask joel
        if(at_start){
            sensor_HashMap.get(0).set_triggered(true);
            sensor_HashMap.get(1).set_triggered(true);
            at_start=false;

        }


        double y_pos_bottom = elevator.getY_position();
        double y_pos_top = elevator.upper_bound();

        // Update sensors based on position and direction
        switch (direction){
            case UP:

                // TODO: check this
                int sensor_above_idx = top_idx + 1;
                // Top sensor

                if (sensor_above_idx <= MAX_SENSOR_IDX && y_pos_top > sensor_pos_Map.get(sensor_above_idx)){
                    // Turn sensor above on
                    sensor_HashMap.get(sensor_above_idx).set_triggered(true);
                    //System.out.println("Elevator is at: "+y_pos_top+ "sensor is at: "+ sensor_pos_Map.get(sensor_above_idx));

                    // Turn bottom sensor off, if not already off
                    if(bottom_idx!=-1){
                        sensor_HashMap.get(bottom_idx).set_triggered(false);
                        //System.out.println("turning off bottom sensor at Sensor: "+bottom_idx+" Elevator is at : "+y_pos_bottom);
                    }

                    // Update bottom and top floor indices
                    bottom_idx = top_idx;
                    top_idx = sensor_above_idx;
                    if (motor.is_off() && top_idx != -1 && bottom_idx != -1) {
                        // snap into place
                        elevator.set_y_position(sensor_pos_Map.get(bottom_idx));
                    }

                // bottom sensor untriggered
                } else if (bottom_idx!=-1 && sensor_pos_Map.get(bottom_idx) < y_pos_bottom){
                    // Turn off bottom sensor
                    //System.out.println("2 if: sensor :"+sensor_pos_Map.get(bottom_idx)+" > bottom: "+y_pos_bottom);
                    sensor_HashMap.get(bottom_idx).set_triggered(false);

                    //set bottom id to none selected
                    bottom_idx =-1;

                }
                break;
            case DOWN:

                int sensor_bellow_idx= bottom_idx-1;
                if(sensor_bellow_idx>=0&&y_pos_bottom<sensor_pos_Map.get(sensor_bellow_idx)){
                    //top sensor untriggered

                    sensor_HashMap.get(sensor_bellow_idx).set_triggered(true);

                    // Turn top sensor off, if not already off
                    if(top_idx!=-1){
                        sensor_HashMap.get(top_idx).set_triggered(false);
                    }

                    //update triggered floor positions
                    top_idx=bottom_idx;
                    bottom_idx=sensor_bellow_idx;
                    if (motor.is_off() && top_idx != -1 && bottom_idx != -1) {
                        // snap into place
                        elevator.set_y_position(sensor_pos_Map.get(bottom_idx));
                    }

                } else if (top_idx!=-1&& sensor_pos_Map.get(top_idx)>y_pos_top) {
                    // turn off top sensor
                    sensor_HashMap.get(top_idx).set_triggered(true);
                    top_idx=-1;
                }
            default:
                // Elevator not moving

        }

//        for (int i: sensor_HashMap.keySet()){
//            if(i>elevator.getY_position() && i < elevator.upper_bound()){
//                sensor_HashMap.get(i).set_triggered(true);
//            }else {
//                sensor_HashMap.get(i).set_triggered(false);
//            }
//        }
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

    //Todo: Comment these later
    public HashMap<Integer, Sensor> getSensors() {
        return sensor_HashMap;
    }

    public HashMap<Integer, Double> get_sensor_pos_HashMap() {
        return sensor_pos_Map;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public Motor getMotor() {
        return motor;
    }

    public void stop(){
        motor.stop();
        direction=Direction.NULL;
        accelerating_indicator=0;
    }

    public void start(){
        at_start=false;
        motor.start();
        if(direction.equals(Direction.UP)){
            accelerating_indicator=1;
        }else if (direction.equals(Direction.DOWN)){
            accelerating_indicator=-1;
        }else{
            System.out.println("Set direction");
        }

    }

    public void stopAt_next_floor(){
        //next bottom sensor, further that 1.5 meters away


    }

    public void setDirection(Direction direction){
        this.direction=direction;
    }

//    public static void main(String[] args) {
//
//    }
}

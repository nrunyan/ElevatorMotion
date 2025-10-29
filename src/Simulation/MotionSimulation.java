package Simulation;

import Hardware.Elevator;
import Hardware.Motor;
import Hardware.Sensor;
import Util.Constants;

import java.util.HashMap;

/**
 * This class simulates motion, for demonstration purposes
 * will be deleted later and replaced with working software
 */
public class MotionSimulation implements Runnable{
    private HashMap<Integer, Sensor> sensor_HashMap =new HashMap<>();
    private Motor motor;
    private Elevator elevator;
    private double current_speed=0.0;
    private int accelerating_indicator  = 0;

    // How long the thread sleeps before updating position, velocity, etc.
    private final int SLEEP_MILLIS = 375;

    /**
     * Makes a motion simulation
     */
    public MotionSimulation(){
        motor=new Motor();
        elevator = new Elevator();
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
            try {
                Thread.sleep(SLEEP_MILLIS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Update all the sensor objects, as to wether they are triggered or not
     */
    private void update_sensors(){
        for (int i: sensor_HashMap.keySet()){
            if(i>elevator.getY_position()&& i< elevator.upper_bound()){
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

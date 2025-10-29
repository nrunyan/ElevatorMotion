package Simulation;

import Hardware.Elevator;
import Hardware.Motor;
import Hardware.Sensor;

import java.util.HashMap;

public class MotionSimulation {
    private HashMap<Integer, Sensor> sensor_HashMap =new HashMap<>();
    private Motor motor;
    private Elevator elevator;


    public MotionSimulation(){
        motor=new Motor();
        elevator = new Elevator();
    }

    private void update_sensors(){
        for (int i: sensor_HashMap.keySet()){
            if(i>elevator.getY_position()&& i< elevator.upper_bound()){
                sensor_HashMap.get(i).set_triggered(true);
            }else {
                sensor_HashMap.get(i).set_triggered(false);
            }
        }
    }

    public Sensor get_sensor(int floor) {
        return sensor_HashMap.get(floor);
    }

    public Motor get_motor() {
        return motor;
    }

    public Elevator get_elevator() {
        return elevator;
    }
}

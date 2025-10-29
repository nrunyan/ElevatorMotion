import java.util.HashMap;

public class MotionSimulation {
    private HashMap<Integer,Sensor> sensorHashMap=new HashMap<>();
    private Motor motor;
    private Elevator elevator;

    public MotionSimulation(){
        motor=new Motor();
        elevator = new Elevator();
    }

    private void updateSensors(){
        for (int i:sensorHashMap.keySet()){
            if(i>elevator.getY_position()&& i< elevator.upperBound()){
                sensorHashMap.get(i).setTriggered(true);
            }else {
                sensorHashMap.get(i).setTriggered(false);
            }
        }
    }

    public Sensor getSensor(int floor) {
        return sensorHashMap.get(floor);
    }

    public Motor getMotor() {
        return motor;
    }

    public Elevator getElevator() {
        return elevator;
    }
}

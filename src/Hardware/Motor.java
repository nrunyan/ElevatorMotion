package Hardware;
import Util.*;

/**
 * Authors: Natalie Runyan, Valerie Barker
 */
public class Motor implements Observable{
    //If the motor is on or not
    private boolean on;
    //The direction the motor is running
    private Direction direction;

    /**
     * Start API for the motor, starts the motor and notifies observers
     */
    public void start(){
        on=true;
        for (Observer o : observers) o.update(this);
    }

    /**
     * Stop API for the motor, stops the motor and notifies observers
     */
    public void stop(){
        on=false;
        for (Observer o : observers) o.update(this);
    }

    /**
     * Set direction API for the motor, sets direction and notifies observers
     * @param direction
     */
    public void set_direction(Direction direction){
        this.direction=direction;
        for (Observer o : observers) o.update(this);
    }

    /**
     * Subscribe API for observers to observe this motor
     * @param subscriber an Observer that wishes to observe this object
     */
    @Override
    public void subscribe(Observer subscriber) {
        observers.add(subscriber);
    }
}

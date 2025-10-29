package Hardware;

import Util.Observable;
import Util.Observer;

/**
 * Authors: Natalie Runyan, Valerie Barker
 */
public class Sensor implements Observable {
    //Weather or not the sensor is aligned with the elevator
    private boolean triggered=false;

    /**
     * Sets the sensor trigger to true or false.
     * Notifies observers of the new trigger value.
     * @param triggered
     */
    public void set_triggered(boolean triggered){
        this.triggered=triggered;
        for (Observer o : observers) o.update(this);
    }

    /**
     * Returns if the sensor is triggered or not
     * @return
     */
    public boolean is_triggered() {return triggered;}

    /**
     * API for observers to observe this sensor
     * @param subscriber an Observer that wishes to observe this object
     */
    @Override
    public void subscribe(Observer subscriber) {
        observers.add(subscriber);
    }
}

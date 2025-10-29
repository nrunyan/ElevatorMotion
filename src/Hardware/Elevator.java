package Hardware;

import Util.Observable;
import Util.Observer;

/**
 * Authors: Natalie Runyan, Valerie Barker
 */

public class Elevator implements Observable {
    //The y position of the bottom of the elevator
    private double y_position;
    //The height of the elevator
    private double height;

    /**
     * Returns the y position of the elevator
     * @return
     */
    public double getY_position(){
        return y_position;
    }

    /**
     * Sets the y position of the elevator, and notifies observers
     * @param y_position
     */
    public void set_y_position(double y_position){
        this.y_position=y_position;
        for (Observer o : observers) o.update(this);
    }

    /**
     * Gets the y position of the top of the elevator
     * @return
     */
    public double upper_bound() {
        return height+y_position;
    }

    /**
     * API for observers to subscribe to this object
     * @param subscriber an Observer that wishes to observe this object
     */
    @Override
    public void subscribe(Observer subscriber) {
        observers.add(subscriber);
    }
}

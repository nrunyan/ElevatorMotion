package Hardware;

import Util.Observable;
import Util.Observer;

public class Elevator implements Observable {
    private double y_position;
    private double height;
    public double getY_position(){
        return y_position;
    }
    public void set_y_position(double y_position){
        this.y_position=y_position;
        for (Observer o : observers) o.update(this);
    }

    public double upper_bound() {
        return height+y_position;
    }

    @Override
    public void subscribe(Observer subscriber) {
        observers.add(subscriber);
    }
}

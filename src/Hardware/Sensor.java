package Hardware;

import Util.Observable;
import Util.Observer;

public class Sensor implements Observable {
    private boolean triggered=false;
    public void set_triggered(boolean triggered){
        this.triggered=triggered;
    }

    public boolean is_triggered() {return triggered;}

    @Override
    public void subscribe(Observer subscriber) {
        observers.add(subscriber);
    }
}

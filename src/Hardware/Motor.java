package Hardware;
import Util.*;
public class Motor implements Observable{
    private boolean on;
    private Direction direction;
    public void start(){
        on=true;
        for (Observer o : observers) o.update(this);
    }
    public void stop(){
        on=false;
        for (Observer o : observers) o.update(this);
    }
    public void set_direction(Direction direction){
        this.direction=direction;
        for (Observer o : observers) o.update(this);
    }

    @Override
    public void subscribe(Observer subscriber) {
        observers.add(subscriber);
    }
}

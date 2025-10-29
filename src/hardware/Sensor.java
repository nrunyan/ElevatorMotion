package hardware;

public class Sensor {
    public boolean is_triggered;

    public Sensor(){
        this.is_triggered = false;
    }

    public void set_is_triggered(boolean is_triggered){
        this.is_triggered = is_triggered;
        //Motion.notify_Triggered()
    }
}

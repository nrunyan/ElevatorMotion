package Hardware;
public class Sensor {
    private boolean triggered=false;
    public void set_triggered(boolean triggered){
        this.triggered=triggered;
    }

    public boolean is_triggered() {
        return triggered;
    }
}

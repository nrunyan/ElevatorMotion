package Hardware;
public class Elevator {
    private double y_position;
    private double height;
    public double getY_position(){
        return y_position;
    }
    public void set_y_position(double y_position){
        this.y_position=y_position;
    }

    public double upper_bound() {
        return height+y_position;
    }
}

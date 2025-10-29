public class Motor {
    private boolean on;
    private Direction direction;
    public void start(){
        on=true;
    }
    public void stop(){
        on=false;
    }
    public void setDirection(Direction direction){
        this.direction=direction;
    }
}

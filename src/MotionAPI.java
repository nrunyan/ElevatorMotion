public class MotionAPI {
    public Direction direction=null;
    private Integer top_Alignment=null;
    private Integer bottom_Alignment=null;

    /**
     * Set directions of elevator
     * @param direction Up, down or null
     */
    public void setDirection(Direction direction){
        this.direction=direction;
    }

    /**
     * Returns the floor that the top of elevator is aligned with
     * @return A floor number or null
     */
    public Integer topAlignment(){
        return top_Alignment;
    }

    /**
     * Returns the floor that the bottom of elevator is aligned with
     * @return A floor number or null
     */
    public Integer bottomAlignment(){
        return bottom_Alignment;
    }

    /**
     *Starts the motor and the elevator’s
     * movement at constant_speed either up or down
     */
    public void start(){

    }

    /**
     *Starts the motor and the elevator’s
     * movement from constant_speed to zero with a decay of deeleration
     */
    public void stop(){

    }
}

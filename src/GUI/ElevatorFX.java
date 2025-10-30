package GUI;
import Util.*;
import Hardware.*;
import javafx.scene.shape.Rectangle;


public class ElevatorFX implements Observer{

    private Rectangle elly;

    public ElevatorFX(Rectangle elly){
        this.elly = elly;
    }
    @Override
    public void update(Observable viewee) {
        //being passed sensor at some point????
        if(viewee instanceof Elevator){
            elly.setLayoutY(((Elevator) viewee).getY_position());
        }else {
            System.out.println("Observable error from elevator");
        }
    }
}

package GUI;
import Util.*;
import Hardware.*;
import javafx.scene.shape.Rectangle;


public class ElevatorFX implements Observer{
    private Rectangle elly;
    @Override
    public void update(Observable viewee) {
        if(viewee instanceof Elevator){
            elly.setLayoutY(((Elevator) viewee).getY_position());
        }else {
            System.out.println("Observable error from elevator");
        }
    }

    public void setElly(Rectangle elly) {
        this.elly = elly;
    }
}

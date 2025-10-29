package GUI;

import Util.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import Hardware.*;

public class SensorFX implements Observer {
    private Circle sen;

    @Override
    public void update(Observable viewee) {
        if(viewee instanceof Sensor){
            if(((Sensor) viewee).is_triggered()){
                sen.setFill(Color.GREEN);
            }else {
                sen.setFill(Color.RED);
            }
        }
    }

    public void setSen(Circle sen) {
        this.sen = sen;
    }
}

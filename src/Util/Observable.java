package Util;

import java.util.ArrayList;
import java.util.List;

public interface Observable {
    List<Observer> observers = new ArrayList<>();
    public void subscribe(Observer subscriber);
}

package Tests;

import Hardware.Motor;
import Util.Direction;

public class MotorControllerTest implements Runnable{

    private Motor myMotor;
    private boolean ON = true;
    public MotorControllerTest(Motor motor) {
        myMotor = motor;
    }
    @Override
    public void run() {
        while (ON) {
            try {
                myMotor.set_direction(Direction.UP);
                myMotor.start();
                System.out.println("Papa");
                Thread.sleep(5000);
                System.out.println("Please papa");
                myMotor.stop();
                Thread.sleep(5000);
                System.out.println("Papa please");
                myMotor.start();
                Thread.sleep(1000);
                System.out.println("Tweet Tweet");
                myMotor.stop();
                Thread.sleep(1000);
                System.out.println("Feed me papa");
                myMotor.set_direction(Direction.DOWN);
                myMotor.start();
                Thread.sleep(5000);
                System.out.println("Tweet Tweet");
                myMotor.stop();
                Thread.sleep(5000);
                System.out.println("Im hungry papa");
                myMotor.start();
                Thread.sleep(1000);
                myMotor.stop();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

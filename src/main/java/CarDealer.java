import java.util.ArrayList;
import java.util.List;

public class CarDealer {
    public static final long NEW_AUTO_RECEIVE_TIME = 2000;
    private final int carsCounter;
    private final Manager manager;
    private final List<Car> carList;

    public CarDealer(int carsCounter) {
        this.carsCounter = carsCounter;
        this.manager = new Manager(this);
        this.carList = new ArrayList<>();
    }

    public void sellCar() {
        this.manager.sellCar();
    }

    public void acceptCar() {
        for (int i = 0; i < this.carsCounter; i++) {
            this.manager.receiveCar();
            try {
                Thread.sleep(NEW_AUTO_RECEIVE_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Car> getCarList() {
        return carList;
    }
}

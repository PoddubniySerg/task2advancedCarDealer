import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Manager {
    private static final long RECEIVE_CAR_TIME = 1000;
    private static final long SELL_CAR_TIME = 1000;

    private final CarDealer carDealer;
    private final Lock lock;
    private final Condition receivedCar;

    public Manager(CarDealer carDealer) {
        this.carDealer = carDealer;
        this.lock = new ReentrantLock(true);
        this.receivedCar = lock.newCondition();
    }

    public void receiveCar() {
        try {
            this.lock.lock();
            System.out.println("Производитель " + Thread.currentThread().getName() + " выпустил 1 авто");
            Thread.sleep(RECEIVE_CAR_TIME);
            this.carDealer.getCarList().add(new Car());
            System.out.println("Новый автомобиль доставлен в салон");
            this.receivedCar.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.lock.unlock();
        }
    }

    public void sellCar() {
        try {
            this.lock.lock();
            System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
            while (this.carDealer.getCarList().isEmpty()) {
                System.out.println("Машин нет");
                this.receivedCar.await();
            }
            Thread.sleep(SELL_CAR_TIME);
            System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            this.lock.unlock();
        }
        this.carDealer.getCarList().remove(0);
    }
}

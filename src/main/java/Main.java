public class Main {
    public static final int SELL_COUNTER = 10;
    public static final long NEW_CLIENT_VISIT_TIME = 2000;

    public static void main(String[] args) {
        final CarDealer carShowRoom = new CarDealer(SELL_COUNTER);

        new Thread(null, carShowRoom::acceptCar, "Toyota").start();

        for (int i = 0; i < SELL_COUNTER; i++) {
            new Thread(null, carShowRoom::sellCar, "Покупатель " + (i + 1)).start();
            try {
                Thread.sleep(NEW_CLIENT_VISIT_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

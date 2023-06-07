public class Food {

    public synchronized void eat(String animalName, int eatingTime) throws InterruptedException {
        if (!Animal.isWinner()) {
            System.out.println(animalName + " is eating.");
            Thread.sleep(eatingTime);
            System.out.println(animalName + " stopped eating.");
        }
    }
}
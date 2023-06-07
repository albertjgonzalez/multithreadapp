import java.util.Random;

public class Animal implements Runnable {
    private String name;
    private int position;
    private int speed;
    private int restMax;
    private int eatTime;
    private static volatile boolean winner = false;
    private Food sharedFood;

    public Animal(String name, int speed, int restMax, int eatTime, Food sharedFood) {
        this.name = name;
        this.speed = speed;
        this.restMax = restMax;
        this.eatTime = eatTime;
        this.sharedFood = sharedFood;
        this.position = 0;
    }

    @Override
    public void run() {
        Random rand = new Random();

        while (!isWinner() && position < 120) {
            try {
                Thread.sleep(rand.nextInt(restMax));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isWinner()) {
                break;
            }

            position += speed;
            System.out.println(name + " is running at " + position + " yards.");

            if (position >= 120) {
                setWinner(true);
                System.out.println(name + " wins.");
            }

            if(!isWinner()) {
                try {
                    sharedFood.eat(name, eatTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isWinner() {
        return winner;
    }

    public static void setWinner(boolean winner) {
        Animal.winner = winner;
    }

    public static void main(String[] args) {
        Food sharedFood = new Food();
        Animal hare = new Animal("Hare", 9, 75, 200, sharedFood);
        Animal tortoise = new Animal("Tortoise", 5, 200, 75, sharedFood);

        Thread hareThread = new Thread(hare);
        Thread tortoiseThread = new Thread(tortoise);

        hareThread.setDaemon(false);
        tortoiseThread.setDaemon(false);

        hareThread.start();
        tortoiseThread.start();
    }
}
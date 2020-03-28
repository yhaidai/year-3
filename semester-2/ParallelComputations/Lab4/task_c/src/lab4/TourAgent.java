package lab4;

import java.util.Random;

public class TourAgent implements Runnable {
    private Graph graph;

    public TourAgent(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int i = random.nextInt(graph.getSize());
            int j = random.nextInt(graph.getSize());
            int price = graph.getPrice(i, j);
            System.out.println("Price(" +  i + ", " + j + ") = " + price);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

package lab4;

import java.util.Random;

public class PriceChanger implements Runnable {
    private Graph graph;

    public PriceChanger(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int i = random.nextInt(graph.getSize());
            int j = random.nextInt(graph.getSize());
            int price = random.nextInt(1000);
            graph.changePrice(i, j, price);
            graph.print();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

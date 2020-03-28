package lab4;

import java.util.Random;

public class RouteManager implements Runnable {
    private Graph graph;

    public RouteManager(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            Boolean add = random.nextBoolean();
            int i = random.nextInt(graph.getSize());
            int j = random.nextInt(graph.getSize());
            if (add) {
                int price = random.nextInt(1000);
                graph.addRoute(i, j, price);
            } else {
                graph.removeRoute(i, j);
            }
            graph.print();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

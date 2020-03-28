package lab4;

import java.util.Random;

public class CitiesManager implements Runnable{
    private Graph graph;

    public CitiesManager(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            Boolean add = random.nextBoolean();
            if (add) {
                graph.addCity();
            } else {
                int i = random.nextInt(graph.getSize());
                graph.removeCity(i);
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

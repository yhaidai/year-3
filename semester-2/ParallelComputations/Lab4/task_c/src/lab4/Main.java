package lab4;

public class Main {

    public static void main(String[] args) {
	    Graph graph = new Graph(5);
	    graph.addRoute(0, 1, 200);
        graph.addRoute(1, 2, 300);
        graph.addRoute(4, 2, 700);
        graph.print();
        System.out.println(graph.getPrice(0, 4));
        System.out.println("\n");

        Runnable priceChanger = new PriceChanger(graph);
        Runnable routeManager = new RouteManager(graph);
        Runnable citiesManager = new CitiesManager(graph);
        Runnable tourAgent = new TourAgent(graph);

        Thread thread1 = new Thread(priceChanger);
        Thread thread2 = new Thread(routeManager);
        Thread thread3 = new Thread(citiesManager);
        Thread thread4 = new Thread(tourAgent);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

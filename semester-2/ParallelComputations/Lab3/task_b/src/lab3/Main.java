package lab3;

public class Main {

    public static void main(String[] args) {
	    Barber barber = new Barber();
	    Thread barberThread = new Thread(barber);
	    barberThread.start();

	    Visitor John = new Visitor("John", barber);
	    Visitor Jack = new Visitor("Jack", barber);
	    Visitor Juliet = new Visitor("Juliet", barber);
	    Thread visitorThread1 = new Thread(John);
        Thread visitorThread2 = new Thread(Jack);
        Thread visitorThread3 = new Thread(Juliet);

        visitorThread1.start();
        visitorThread2.start();
        visitorThread3.start();
    }
}

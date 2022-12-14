

import java.util.LinkedList;
import java.util.Queue;

public class Monitor {

    private LinkedList<String> buffor = new LinkedList<>();
    private int MAX_CAPACITY = 2;
    private int counter = 0;


    Monitor() {

    }

    public synchronized void produce() throws InterruptedException
    {
        while (true) {

            // producer thread waits while list
            // is full
            while (buffor.size() == MAX_CAPACITY)
                wait();

            String message = "Będę inzynierem za " + Integer.toString(counter) + " lat!";
            counter++;
            buffor.add(message);

            System.out.println("Sent message!");

            // notifies the consumer thread that
            // now it can start consuming
            notify();

        }
    }

    // Function called by consumer thread
    public synchronized String consume() throws InterruptedException
    {
        while (true) {

            // consumer thread waits while list
            // is empty
            while (buffor.size() == 0)
                wait();

            // to retrieve the first job in the list
            String message = buffor.removeFirst();

            System.out.println("Consumer consumed message: " + message);

            // Wake up producer thread
            notify();

        }
    }
}
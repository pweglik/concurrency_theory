import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        Monitor monitor = new Monitor();

        Producer producer1 = new Producer("producerThread1", monitor);
        Thread producerThread1 = new Thread(producer1);

        Consumer consumer1 = new Consumer("consumerThread1", monitor);
        Thread consumerThread1 = new Thread(consumer1);

        Producer producer2 = new Producer("producerThread2", monitor);
        Thread producerThread2 = new Thread(producer2);

        Consumer consumer2 = new Consumer("consumerThread2", monitor);
        Thread consumerThread2 = new Thread(consumer2);

        producerThread1.start();
        consumerThread1.start();
        producerThread2.start();
        consumerThread2.start();

        try
        {
            producerThread1.join();
            consumerThread1.join();
            producerThread2.join();
            consumerThread2.join();
        }
        catch(Exception ex)
        {
            System.out.println("Exception has" +
                    " been caught" + ex);
        }


    }
}
import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        Resource resource = new Resource();

        Producer producer1 = new Producer("producerThread1", resource);
        Thread producerThread1 = new Thread(producer1);

        Consumer consumer1 = new Consumer("consumerThread1", resource);
        Thread consumerThread1 = new Thread(consumer1);

        Producer producer2 = new Producer("producerThread2", resource);
        Thread producerThread2 = new Thread(producer2);

        Consumer consumer2 = new Consumer("consumerThread2", resource);
        Thread consumerThread2 = new Thread(consumer2);

        Producer producer3 = new Producer("producerThread2", resource);
        Thread producerThread3 = new Thread(producer3);

        Consumer consumer3 = new Consumer("consumerThread2", resource);
        Thread consumerThread3 = new Thread(consumer3);

        producerThread1.start();
        consumerThread1.start();
        producerThread2.start();
        consumerThread2.start();
        producerThread3.start();
        consumerThread3.start();

        try
        {
            producerThread1.join();
            consumerThread1.join();
            producerThread2.join();
            consumerThread2.join();
            producerThread3.join();
            consumerThread3.join();
        }
        catch(Exception ex)
        {
            System.out.println("Exception has" +
                    " been caught" + ex);
        }


    }
}
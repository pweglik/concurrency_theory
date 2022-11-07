import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int producersCount = 3;
        int consumersCount = 2;
        
        Producer[] producers = new Producer[producersCount];
        Consumer[] consumers = new Consumer[consumersCount];

        Scheduler scheduler = new Scheduler();
        scheduler.start();
        scheduler.join();

        Proxy proxy = new Proxy(scheduler);

        for (int i = 0; i < producersCount; i++) {
            Producer newProducer = new Producer("producerThread" + i, proxy);
            newProducer.start();
            producers[i] = newProducer;
        }

        for (int i = 0; i < consumersCount; i++) {
            Consumer newConsumer = new Consumer("consumerThread" + i, proxy);
            newConsumer.start();
            consumers[i] = newConsumer;
        }

        try {
            for (int i = 0; i < producersCount; i++) {
                producers[i].join();
            }
            for (int i = 0; i < consumersCount; i++) {
                consumers[i].join();
            }
        } catch (Exception ex) {
            System.out.println("Exception has" +
                    " been caught" + ex);
        }
    }
}
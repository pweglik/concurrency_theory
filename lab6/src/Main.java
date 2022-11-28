import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final ThreadMXBean threadManager = ManagementFactory.getThreadMXBean();
    private static int REPEATS = 1;

    public static void main(String[] args) throws InterruptedException {
        int totalResult = 0;
        for(int l = 0; l < REPEATS; l ++) {
            int producersCount = 2;
            int consumersCount = 2;

            int totalTasks = 0;

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


            int time = 1;
            Thread.sleep(time * 1000);


            for (int i = 0; i < producersCount; i++) {
                totalTasks += producers[i].taskCounter;
                producers[i].stop();
                producers[i].thread.stop();
            }
            for (int i = 0; i < consumersCount; i++) {
                totalTasks += consumers[i].taskCounter;
                consumers[i].stop();
                consumers[i].thread.stop();
            }
//            System.out.printf("Total extra tasks: %d\n", totalTasks);
            totalResult += totalTasks;
        }

        System.out.printf("Total extra tasks averaged in %d runs: %d\n", REPEATS, totalResult / REPEATS);

    }


    private static double avg(List <Long> cpuTimes) {
        return cpuTimes.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);
    }
}
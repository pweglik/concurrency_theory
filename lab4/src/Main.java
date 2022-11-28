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

            double totalCpuTimes = 0;
            double totalProduced = 0;
            int totalTasks = 0;

            Resource resource = new Resource();
            Producer[] producers = new Producer[producersCount];
            Consumer[] consumers = new Consumer[consumersCount];

            for (int i = 0; i < producersCount; i++) {
                Producer newProducer = new Producer("producerThread" + i, resource);
                newProducer.start();
                producers[i] = newProducer;
            }

            for (int i = 0; i < consumersCount; i++) {
                Consumer newConsumer = new Consumer("consumerThread" + i, resource);
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

            List<Long> cpuTimes = new ArrayList<>();

            for (int i = 0; i < producersCount; i++) {
                cpuTimes.add(threadManager.getThreadCpuTime(producers[i].thread.getId()));
                totalTasks += producers[i].taskCounter;
                producers[i].stop();
                producers[i].thread.stop();
            }
            for (int i = 0; i < consumersCount; i++) {
                cpuTimes.add(threadManager.getThreadCpuTime(consumers[i].thread.getId()));
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
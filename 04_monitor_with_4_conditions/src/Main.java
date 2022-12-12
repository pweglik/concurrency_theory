import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final ThreadMXBean threadManager = ManagementFactory.getThreadMXBean();

    public static void main(String[] args) throws InterruptedException {
        int producersCount = 1;
        int consumersCount = 1;

        double totalCpuTimes = 0;
        double totalProduced = 0;

        int repeats = 5;
        for (int j = 0; j < repeats; j++) {

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
                producers[i].stop();
                producers[i].thread.stop();
            }
            for (int i = 0; i < consumersCount; i++) {
                cpuTimes.add(threadManager.getThreadCpuTime(consumers[i].thread.getId()));
                consumers[i].stop();
                consumers[i].thread.stop();
            }

            System.out.println("Total real time: ");
            System.out.printf("%ds\n", time);
            System.out.println("TNumber of threads: ");
            System.out.printf("%d\n", producersCount + consumersCount);
            System.out.println("Average CPU time per thread: ");
            System.out.printf("%.3fs\n", (double) avg(cpuTimes) / 1000000000);
            System.out.println("Total CPU time: ");
            System.out.printf("%.3fs\n", (double) avg(cpuTimes) / 1000000000 * (producersCount + consumersCount));
            System.out.print("\n");
            System.out.printf("Total produced: %d\nTotal consumed: %d\n", resource.prod_counter, resource.cons_counter);

            totalCpuTimes += avg(cpuTimes) / 1000000000 * (producersCount + consumersCount);
            totalProduced += resource.prod_counter;
        }

        System.out.println("Averaged total cpu time: ");
        System.out.printf("%.3fs\n", totalCpuTimes / repeats);
        System.out.println("Averaged total produced: ");
        System.out.printf("%.3f\n", totalProduced / repeats);
        System.out.println("Averaged total produced per CPU second");
        System.out.printf("%.3f\n", (totalProduced / repeats)/(totalCpuTimes / repeats));
    }

    private static double avg(List <Long> cpuTimes) {
        return cpuTimes.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);
    }
}
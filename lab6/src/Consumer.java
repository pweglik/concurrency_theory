import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class Consumer extends Thread implements Runnable {
    Thread thread;
    private String threadName;
    private Proxy proxy;

    private Random generator = new Random(42);
    int taskCounter = 0;

    Consumer(String name, Proxy proxy) {
        this.threadName = name;
        this.proxy = proxy;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );

        while(true) {
//            System.out.println("Step " + threadName);
            try {

                int consumeCount =  generator.nextInt(10) + 1;

                Promise promise = this.proxy.pop(consumeCount);

                while(!promise.isCompleted()) {
                    Computation.compute(10);
                    this.taskCounter++;
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (thread == null) {
            thread = new Thread (this, threadName);
            thread.start ();
        }
    }


}


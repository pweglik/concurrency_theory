import java.util.Random;

class Consumer extends Thread implements Runnable {
    Thread thread;
    private String threadName;
    private Resource resource;
    int taskCounter = 0;

    private Random generator = new Random(42);

    Consumer( String name, Resource argResource) {
        threadName = name;
        resource = argResource;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );

        while(true) {
//            System.out.println("Step " +  threadName );
            try {
                Computation.compute(10);
                this.taskCounter++;

                int consumeCount =  generator.nextInt(10) + 1;
                resource.consume(consumeCount, this.thread);
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


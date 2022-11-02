import java.util.Random;

class Producer extends Thread implements Runnable {
    public Thread thread;
    private String threadName;
    private Resource resource;

    private Random generator = new Random(42);


    Producer( String name, Resource argResource) {
        threadName = name;
        resource = argResource;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );

        while(true) {
//            System.out.println("Step " +  threadName );
            try {
                int produceCount = generator.nextInt(10) + 1;
                resource.produce(produceCount);
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


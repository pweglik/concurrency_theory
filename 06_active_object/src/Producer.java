import java.util.Random;

class Producer extends Thread implements Runnable {
    public Thread thread;
    private String threadName;
    private Proxy proxy;

    private Random generator = new Random(42);


    Producer(String name, Proxy proxy) {
        this.threadName = name;
        this.proxy = proxy;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );

        while(true) {
            System.out.println("Step " + threadName);
            try {
                int produceCount = generator.nextInt(10) + 1;
                String[] messages = new String[produceCount];
                for(int i = 0; i < produceCount; i++) {
                    messages[i] = "One day I'll be an engineer!";
                }
                Promise promise = this.proxy.push(messages);

                while(!promise.isCompleted()){
                    Thread.sleep(10);
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


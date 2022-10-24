class Producer extends Thread implements Runnable {
    private Thread t;
    private String threadName;
    private Resource resource;
    private int produceCount;


    Producer( String name, Resource argResource, int argProduceCount) {
        threadName = name;
        resource = argResource;
        produceCount = argProduceCount;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );

        while(true) {
            System.out.println("Step " +  threadName );
            try {
                resource.produce(produceCount);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }


}


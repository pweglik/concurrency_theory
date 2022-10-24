class Consumer extends Thread implements Runnable {
    private Thread t;
    private String threadName;
    private Resource resource;

    private int consumeCount;

    Consumer( String name, Resource argResource, int argConsumeCount) {
        threadName = name;
        resource = argResource;
        consumeCount = argConsumeCount;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );

        while(true) {
            System.out.println("Step " +  threadName );
            try {
                resource.consume(consumeCount);
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


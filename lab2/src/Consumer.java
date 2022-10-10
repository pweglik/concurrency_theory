class Consumer extends Thread implements Runnable {
    private Thread t;
    private String threadName;

    private int numberOfOps = 100000;
    private Monitor monitor;

    Consumer( String name, Monitor argMonitor) {
        threadName = name;
        monitor = argMonitor;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );

        while(true) {
            try {
                monitor.consume();
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


class MyThread extends Thread implements Runnable {
    private Thread t;
    private String threadName;

    private Counter counter;
    private int numberOfOps = 100000;
    private boolean inc;

    MyThread( String name, Counter counterArg, boolean incArg) {
        threadName = name;
        counter = counterArg;
        inc = incArg;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );

        for(int i = 0; i < numberOfOps; i++) {
            if(inc) {
                counter.inc();
            } else {
                counter.dec();
            }
        }

        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}


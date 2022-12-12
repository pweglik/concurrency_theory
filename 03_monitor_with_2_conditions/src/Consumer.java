import java.util.concurrent.ThreadLocalRandom;
class Consumer extends Thread implements Runnable {
    private Thread t;
    private String threadName;
    private Resource resource;

    Consumer( String name, Resource argResource) {
        threadName = name;
        resource = argResource;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );

        while(true) {
            try {
                int sizeOfPortion = ThreadLocalRandom.current().nextInt(0, 10 + 1);
                resource.popFromStack(sizeOfPortion);
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


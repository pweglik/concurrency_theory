import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class Scheduler extends Thread implements Runnable{
    Thread thread;
    LinkedBlockingQueue<MethodRequest> requestQueue = new LinkedBlockingQueue<MethodRequest>();
    LinkedList<MethodRequest> waitingQueue = new LinkedList<MethodRequest>();
    int servantStackLength = 20;
    Servant servant = new Servant(servantStackLength);

    Scheduler() {

    }

    public void run() {
        System.out.println("Running scheduler");

        while(true) {
            try {
                this.dispatch();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void start () {
        System.out.println("Starting scheduler");
        if (thread == null) {
            thread = new Thread (this, "Scheduler");
            thread.start ();
        }
    }

    public Servant getServant() {
        return this.servant;
    }

    public void addRequest(MethodRequest request) throws InterruptedException {
        requestQueue.put(request);
    }

    public void dispatch() throws InterruptedException {
        MethodRequest request;
        // guard == true means we can't do an operation
        // guard == false mean resource is not guarded
        if(waitingQueue.peek() != null && !waitingQueue.peek().guarded()) {
            request = waitingQueue.poll();
        }
        else{
            request = requestQueue.take();
        }

        if(request.guarded()) {
            waitingQueue.add(request);
        }
        else {
            Computation.compute(5);
            request.call();
        }

    }
}



import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Resource {

    private Stack<String> buffor = new Stack<>();
    private int MAX_CAPACITY = 20;
    private int counter = 0;
    ReentrantLock lock = new ReentrantLock();
    Condition stackEmptyCondition = lock.newCondition();
    Condition hasWaitingConsumersLock = lock.newCondition();
    boolean hasWaitingConsumers = false;
    Condition stackFullCondition = lock.newCondition();
    Condition hasWaitingProducersLock = lock.newCondition();
    boolean hasWaitingProducers = false;

    Resource() {

    }

    public void produce(int sizeOfPortion) throws InterruptedException
    {
        try {
            lock.lock();

            while(hasWaitingProducers) {
                hasWaitingProducersLock.await();
            }
            hasWaitingProducers = true;

            while(buffor.size() > MAX_CAPACITY - sizeOfPortion) {
                stackFullCondition.await();
            }

            for(int i = 0; i < sizeOfPortion; i++) {
                String message = "Będę inzynierem za " + Integer.toString(counter) + " lat!";
                counter++;
                buffor.push(message);
                System.out.println("Message sent!");
            }

        } finally {
            hasWaitingProducers = false;
            hasWaitingProducersLock.signal();
            stackEmptyCondition.signal();
            lock.unlock();
        }
    }

    // Function called by consumer thread
    public void consume(int sizeOfPortion) throws InterruptedException
    {
        try {
            lock.lock();

            while(hasWaitingConsumers) {
                hasWaitingConsumersLock.await();
            }
            hasWaitingConsumers = true;

            while(buffor.size() < sizeOfPortion) {
                stackEmptyCondition.await();
            }

            for(int i = 0; i < sizeOfPortion; i++) {
                System.out.println("Message received: " +  buffor.pop());
            }

        } finally {
            hasWaitingConsumers = false;
            hasWaitingConsumersLock.signal();
            stackFullCondition.signal();
            lock.unlock();
        }
    }
}
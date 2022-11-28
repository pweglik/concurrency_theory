

import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Resource {

    private Stack<String> buffer = new Stack<>();
    private int MAX_CAPACITY = 20;
    private int COST  = 50;

    ReentrantLock lock = new ReentrantLock();
    Condition stackEmptyCondition = lock.newCondition();
    Condition hasWaitingConsumersLock = lock.newCondition();
    boolean hasWaitingConsumers = false;
    Condition stackFullCondition = lock.newCondition();
    Condition hasWaitingProducersLock = lock.newCondition();
    boolean hasWaitingProducers = false;

    Resource() {

    }

    public void produce(int sizeOfPortion, Thread thread) throws InterruptedException
    {
        try {
            lock.lock();

            while(hasWaitingProducers) {
                hasWaitingProducersLock.await();
            }
            hasWaitingProducers = true;

            while(buffer.size() > MAX_CAPACITY - sizeOfPortion) {
                stackFullCondition.await();
            }

            Computation.compute(COST);
            for(int i = 0; i < sizeOfPortion; i++) {
                String message = "Będę inzynierem!";
                buffer.push(message);
//                System.out.println("Message sent!");
            }

        } finally {
            hasWaitingProducers = false;
            hasWaitingProducersLock.signal();
            stackEmptyCondition.signal();
            lock.unlock();
        }
    }

    // Function called by consumer thread
    public void consume(int sizeOfPortion, Thread thread) throws InterruptedException
    {
        try {
            lock.lock();

            while(hasWaitingConsumers) {
                hasWaitingConsumersLock.await();
            }
            hasWaitingConsumers = true;

            while(buffer.size() < sizeOfPortion) {
                stackEmptyCondition.await();
            }

            Computation.compute(COST);
            for(int i = 0; i < sizeOfPortion; i++) {
                buffer.pop();
//                System.out.println("Message received: " +  buffor.pop());
            }

        } finally {
            hasWaitingConsumers = false;
            hasWaitingConsumersLock.signal();
            stackFullCondition.signal();
            lock.unlock();
        }
    }
}
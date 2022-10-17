

import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Resource {

    private Stack<String> buffor = new Stack<>();
    private int MAX_CAPACITY = 18;
    private int counter = 0;
    ReentrantLock lock = new ReentrantLock();
    Condition stackEmptyCondition = lock.newCondition();
    Condition stackFullCondition = lock.newCondition();

    Resource() {

    }

    public void pushToStack(int sizeOfPortion) throws InterruptedException
    {
        try {
            lock.lock();
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
            stackEmptyCondition.signal();
            lock.unlock();
        }
    }

    // Function called by consumer thread
    public void popFromStack(int sizeOfPortion) throws InterruptedException
    {
        try {
            lock.lock();
            while(buffor.size() < sizeOfPortion) {
                stackEmptyCondition.await();
            }

            for(int i = 0; i < sizeOfPortion; i++) {
                System.out.println("Message received: " +  buffor.pop());
            }

        } finally {
            stackFullCondition.signal();
            lock.unlock();
        }
    }
}
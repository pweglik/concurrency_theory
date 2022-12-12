

import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Resource {

    private Stack<String> buffer = new Stack<>();
    private int MAX_CAPACITY = 20;
    int prod_counter = 0;
    int cons_counter = 0;


    ReentrantLock consumerLock = new ReentrantLock();
    ReentrantLock producerLock = new ReentrantLock();
    ReentrantLock commonLock = new ReentrantLock();

    Condition stackEmptyCondition = commonLock.newCondition();
    Condition stackFullCondition = commonLock.newCondition();



    Resource() {

    }

    public void produce(int sizeOfPortion) throws InterruptedException
    {
        try {
            producerLock.lock();

            try {
                commonLock.lock();

                while(buffer.size() > MAX_CAPACITY - sizeOfPortion) {
                    stackFullCondition.await();
                }

                for(int i = 0; i < sizeOfPortion; i++) {
                    String message = "Będę inzynierem za " + Integer.toString(prod_counter) + " lat!";
                    prod_counter++;
                    buffer.push(message);
//                    System.out.println("Message sent!");
                }

            } finally {
                stackEmptyCondition.signal();
                commonLock.unlock();
            }
        } finally {
            producerLock.unlock();
        }
    }

    // Function called by consumer thread
    public void consume(int sizeOfPortion) throws InterruptedException
    {
        try {
            consumerLock.lock();

            try {
                commonLock.lock();

                while(buffer.size() < sizeOfPortion) {
                    stackEmptyCondition.await();
                }

                for(int i = 0; i < sizeOfPortion; i++) {
//                    System.out.println("Message received: " +  buffer.pop());
                    buffer.pop();
                    cons_counter++;
                }

            } finally {
                stackFullCondition.signal();
                commonLock.unlock();
            }
        } finally {
            consumerLock.unlock();
        }
    }
}
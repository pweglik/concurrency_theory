public class Main {
    int numberOfThreads = 20;
    Thread[] threadArray = new Thread[numberOfThreads];

    public static void main(String args[]) throws InterruptedException {
        Counter globalCounter = new Counter();

        MyThread incThread = new MyThread( "Thread-1", globalCounter, true);
        incThread.start();

        MyThread decThread = new MyThread( "Thread-2", globalCounter, false);
        decThread.start();


        try
        {
            incThread.join();
            decThread.join();
        }
        catch(Exception ex)
        {
            System.out.println("Exception has" +
                    " been caught" + ex);
        }

        globalCounter.print();
    }
}

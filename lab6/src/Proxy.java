public class Proxy {
    Scheduler scheduler;
    Proxy(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    Promise push(String newMessages[]) throws InterruptedException {
        Promise result = new Promise();
        MethodRequest request = new Push(scheduler.getServant(), result, newMessages);
        scheduler.addRequest(request);

        return result;
    }

    Promise pop(int numberOfMessagesToPop) throws InterruptedException {
        Promise result = new Promise();
        MethodRequest request = new Pop(scheduler.getServant(), result, numberOfMessagesToPop);
        scheduler.addRequest(request);

        return result;
    }
}

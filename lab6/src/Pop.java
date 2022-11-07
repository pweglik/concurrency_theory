public class Pop extends MethodRequest {
    Servant servant;
    Promise promise;
    int numberOfMessagesToPop;
    Pop(Servant servant, Promise promise, int numberOfMessagesToPop) {
        this.servant = servant;
        this.promise = promise;
        this.numberOfMessagesToPop = numberOfMessagesToPop;
    }

    public boolean guarded() {
        return !servant.hasAtLeastNElements(this.numberOfMessagesToPop);
    }

    public void call() {
        promise.setMessages(servant.pop(this.numberOfMessagesToPop));
        promise.complete();
    }
}

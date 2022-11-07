public class Push extends MethodRequest {
    Servant servant;
    Promise promise;
    String[] messages;
    Push(Servant servant, Promise promise, String[] newMessage) {
        this.servant = servant;
        this.promise = promise;
        this.messages = newMessage;
    }

    public boolean guarded() {
        return !servant.hasAtLeastNEmptySpace(messages.length);
    }

    public void call() {
        servant.push(this.messages);
        promise.complete();
    }
}

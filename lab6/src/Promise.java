public class Promise {
    String[] messages = null;
    boolean completed = false;
    Promise() {

    }

    void setMessages(String[] newMessages) {
        this.messages = newMessages;
    }

    boolean isCompleted() {
        return this.completed;
    }
    void complete() {
        this.completed = true;
    }

    String[] getMessages() {
        return this.messages;
    }
}

import java.util.Stack;

public class Servant {
    private int size;
    private Stack<String> buffer = new Stack<>();

    Servant(int size) {
        this.size = size;
    }

    boolean hasAtLeastNElements(int n) {
        return buffer.size() >= n;
    }

    boolean hasAtLeastNEmptySpace(int n) {
        return buffer.size() + n <= size;
    }

    void push(String[] newMessages) {
        System.out.println("Stack push! ");
        if(this.hasAtLeastNEmptySpace(newMessages.length))
            for(String message: newMessages) {
                buffer.push(message);
            }
        else {
            throw new Error("Stack is full!");
        }
    }

    String[] pop(int numberOfMessagesToPop) {
        System.out.println("Stack pop! ");
        String[] messages = new String[numberOfMessagesToPop];
        if(this.hasAtLeastNElements(numberOfMessagesToPop))
            for(int i = 0; i < numberOfMessagesToPop; i++) {
                messages[i] = buffer.pop();
            }
        else {
            throw new Error("Stack is full!");
        }

        return messages;
    }


}

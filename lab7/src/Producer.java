import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
    private One2OneChannelInt channel;
    private int start;

    public Producer(final One2OneChannelInt out, int start) {
        channel = out;
        this.start = start;
    }

    public void run() {
        int item;
        for (int k = 0; k < 100; k++) {
            item = (int) (Math.random() * 100) + 1 + start;
            channel.out().write(item);
        }
        channel.out().write(-1);
        System.out.println("Producer" + start + " ended.");
    }
}
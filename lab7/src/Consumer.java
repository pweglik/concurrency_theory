import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {
    private One2OneChannelInt in;
    private One2OneChannelInt req;

    public Consumer(final One2OneChannelInt req, final One2OneChannelInt in) {
        this.req = req;
        this.in = in;
    }

    public void run() {
        int item;
        while (true) {
            req.out().write(0); // Request data - blocks until data is available
            item = in.in().read();
            if (item < 0)
                break;
            System.out.println(item);
        }
        System.out.println("Consumer ended.");
    }
}
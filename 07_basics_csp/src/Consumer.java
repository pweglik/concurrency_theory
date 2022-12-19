import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.One2OneChannelInt;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Consumer extends Client implements CSProcess {
    public List<One2OneChannel> channelsToBuffers;
    public List<One2OneChannel> channelsFromBuffers;
    public One2OneChannel channelToLB;
    private int id;

    public Consumer(final List<One2OneChannel> channelsToBuffers,
                    final List<One2OneChannel> channelsFromBuffers,
                    One2OneChannel channelToLB,
                    int id) {
        this.channelsToBuffers = channelsToBuffers;
        this.channelsFromBuffers = channelsFromBuffers;
        this.channelToLB = channelToLB;
        this.id = id;

    }

    public void run() {
        System.out.printf("Start Consumer! \n");
        while(true) {
            int i = ThreadLocalRandom.current().nextInt(0, this.channelsToBuffers.size());

            // Request access - blocks until data is available
            this.channelsToBuffers.get(i).out().write(Request.REQUEST_CONSUME);
            Request response = (Request) this.channelsFromBuffers.get(i).in().read();

            if (response == Request.ACCEPT)
            {
                this.channelsToBuffers.get(i).out().write(Request.CONSUME);
            }
        }
    }

    public List<One2OneChannel> getChannelsToBuffer() {
        return this.channelsToBuffers;
    }

    public List<One2OneChannel> getChannelsFromBuffer() {
        return this.channelsFromBuffers;
    }
}
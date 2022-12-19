import org.jcsp.lang.One2OneChannel;

import java.util.List;

public abstract class Client {
    public List<One2OneChannel> channelsToBuffers;
    public List<One2OneChannel> channelsFromBuffers;
    public abstract List<One2OneChannel> getChannelsToBuffer();
    public abstract List<One2OneChannel> getChannelsFromBuffer();
}

import org.jcsp.lang.*;

import java.util.List;

/**
 * Buffer class: Manages communication between Producer2
 * and Consumer2 classes.
 */
public class Buffer implements CSProcess {
    public List<One2OneChannel> channelsToClients;
    public List<One2OneChannel> channelsFromClients;
    // The buffer itself
    private int size;
    private int bufferValue;
    private int id;


    public Buffer(List<One2OneChannel> channelsToBuffer,
           List<One2OneChannel> channelsFromBuffer,
           int size,
           int id) {
        this.channelsToClients = channelsFromBuffer;
        this.channelsFromClients = channelsToBuffer;
        this.size = size;
        this.bufferValue = 0;
        this.id = id;
    }

    public void run() {
        final Guard[] guards = new Guard[this.channelsFromClients.size()];

        for(int i = 0 ; i < this.channelsFromClients.size() ; i++) {
            guards[i] = channelsFromClients.get(i).in();
        }

        final Alternative alt = new Alternative(guards);

        while(true) {
            int index = alt.fairSelect();
            Request req = (Request) channelsFromClients.get(index).in().read();
            switch(req)  {
                case REQUEST_CONSUME:
                    if (bufferValue > 0) {
                        channelsToClients.get(index).out().write(Request.ACCEPT);
                        // consuming CONSUME
                        Request reqCons = (Request) channelsFromClients.get(index).in().read();
                        if(reqCons == Request.CONSUME) {
                            bufferValue--;
                        }
                    }
                    else {
                        channelsToClients.get(index).out().write(Request.REJECT);
                    }
                    break;
                case REQUEST_PRODUCE:
                    if (bufferValue < size) {
                        channelsToClients.get(index).out().write(Request.ACCEPT);
                        // consuming CONSUME
                        Request reqProd = (Request) channelsFromClients.get(index).in().read();
                        if(reqProd == Request.PRODUCE) {
                            bufferValue++;
                        }
                    }
                    else {
                        channelsToClients.get(index).out().write(Request.REJECT);
                    }
                    break;
            }
            System.out.printf("Buffer %d full in %.2f percent \n", this.id, ((float) bufferValue / size * 100));
        }
    }
}
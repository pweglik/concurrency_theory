import org.jcsp.lang.*;

import java.util.ArrayList;
import java.util.List;

public final class Main {
    public static void main(String[] args) {
        int NUMBER_OF_PRODUCERS = 20;
        int NUMBER_OF_CONSUMERS = 10;
        int NUMBER_OF_BUFFERS = 4;
        int BUFFER_SIZE = 10;

        List<CSProcess> processList = new ArrayList<>();
        List<Client> clients = new ArrayList<>();

        // Producers
        for (int i = 0; i < NUMBER_OF_PRODUCERS; i++) {
            List<One2OneChannel> channelsToBuffers = new ArrayList<>();
            List<One2OneChannel> channelsFromBuffers = new ArrayList<>();
            for (int j = 0; j < NUMBER_OF_BUFFERS; j++) {
                One2OneChannel channelToBuffer = Channel.one2one();
                channelsToBuffers.add(channelToBuffer);
                One2OneChannel channelFromBuffer = Channel.one2one();
                channelsFromBuffers.add(channelFromBuffer);
            }
            One2OneChannel channelToLB = Channel.one2one();

            CSProcess newProducer = new Producer(
                    channelsToBuffers,
                    channelsFromBuffers,
                    channelToLB,
                    i
            );

            clients.add((Client) newProducer);
            processList.add(newProducer);
        }

        // Consumers
        for (int i = 0; i < NUMBER_OF_CONSUMERS; i++) {
            List<One2OneChannel> channelsToBuffers = new ArrayList<>();
            List<One2OneChannel> channelsFromBuffers = new ArrayList<>();
            for (int j = 0; j < NUMBER_OF_BUFFERS; j++) {
                One2OneChannel channelToBuffer = Channel.one2one();
                channelsToBuffers.add(channelToBuffer);
                One2OneChannel channelFromBuffer = Channel.one2one();
                channelsFromBuffers.add(channelFromBuffer);
            }
            One2OneChannel channelToLB = Channel.one2one();

            CSProcess newConsumer = new Consumer(
                    channelsToBuffers,
                    channelsFromBuffers,
                    channelToLB,
                    i
            );
            clients.add((Client) newConsumer);
            processList.add(newConsumer);
        }


        // Buffers
        for (int i = 0; i < NUMBER_OF_BUFFERS; i++) {
            List<One2OneChannel> channelsToBuffer = new ArrayList<>();
            List<One2OneChannel> channelsFromBuffer = new ArrayList<>();
            for (int j = 0; j < NUMBER_OF_PRODUCERS + NUMBER_OF_CONSUMERS; j++) {
                channelsToBuffer.add(clients.get(j).getChannelsToBuffer().get(i));
                channelsFromBuffer.add(clients.get(j).getChannelsFromBuffer().get(i));
            }

            One2OneChannel channelToLB = Channel.one2one();
            processList.add(new Buffer(
                    channelsToBuffer,
                    channelsFromBuffer,
                    BUFFER_SIZE,
                    i));
        }

        CSProcess[] convertedProcessList = new CSProcess[processList.size()];
        convertedProcessList = processList.toArray(convertedProcessList);

        Parallel parallel = new Parallel(convertedProcessList);
        parallel.run();
    }
}


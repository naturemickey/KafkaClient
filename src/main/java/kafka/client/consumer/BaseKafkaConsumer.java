package kafka.client.consumer;

import java.util.List;

public abstract class BaseKafkaConsumer<T> extends BaseKafkaBatchConsumer<T> {
    public BaseKafkaConsumer(Config config) {
        super(config);
    }

    @Override
    public final void receive(List<T> messages) {
        messages.forEach(this::receive);
    }

    public abstract void receive(T message);
}

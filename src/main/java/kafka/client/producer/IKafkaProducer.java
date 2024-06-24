package kafka.client.producer;

import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.List;
import java.util.concurrent.Future;

public interface IKafkaProducer<T> {
    Future<RecordMetadata> send(T message);
    Future<RecordMetadata> send(String key, T message);
    List<Future<RecordMetadata>> send(List<T> message);
    List<Future<RecordMetadata>> send(String key, List<T> message);
}

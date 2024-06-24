package kafka.client.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Future;

public class KafkaProducerFactory {

    public static <T> IKafkaProducer<T> createProducer(String topic, Config config) {
        return new KafkaProducer<T>(topic, config);
    }

    private static class KafkaProducer<T> implements IKafkaProducer<T> {
        private final org.apache.kafka.clients.producer.KafkaProducer<String, T> producer;
        private final String topic;

        private KafkaProducer(String topic, Config config) {
            Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.bootstrap_servers);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, config.key_serializer.getName());
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, config.value_serializer.getName());
            props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, UUID.randomUUID().toString());
            if (config.optional != null) {
                if (config.optional.enable_idempotence) {
                    props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
                }
            }

            this.producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
            this.producer.initTransactions();
            this.topic = topic;
        }

        public java.util.concurrent.Future<org.apache.kafka.clients.producer.RecordMetadata> send(T message) {
            return this.send(null, message);
        }

        public java.util.concurrent.Future<org.apache.kafka.clients.producer.RecordMetadata> send(String key, T message) {
            ProducerRecord<String, T> record = new ProducerRecord<>(this.topic, key, message);

            return this.producer.send(record);
        }

        public List<Future<RecordMetadata>> send(List<T> messages) {
            return this.send(null, messages);
        }

        public List<Future<RecordMetadata>> send(String key, List<T> messages) {
            List<Future<RecordMetadata>> futures = new ArrayList<>();
            try {
                producer.beginTransaction();

                for (T msg : messages) {
                    futures.add(this.send(null, msg));
                }

                producer.commitTransaction();
            } catch (KafkaException e) {
                producer.abortTransaction();
                throw e;
            }
            return futures;
        }
    }
}
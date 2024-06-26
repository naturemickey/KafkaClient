package kafka.client.consumer;

import kafka.client.ConfigConstants;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.*;

public abstract class BaseKafkaBatchConsumer<T> {

    private static Logger logger = LoggerFactory.getLogger(BaseKafkaBatchConsumer.class);

    public abstract void receive(List<T> messages);

    public BaseKafkaBatchConsumer(Config config) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.bootstrap_servers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.group_id);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, config.key_deserializer.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.value_deserializer.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConfigConstants.VALUE_DESERIALIZER_CLASS, getValueClass());
        if (config.optional != null) {
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.optional.auto_offset_reset);
        }

        this.listener = () -> {
            KafkaConsumer<String, T> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Collections.singletonList(config.topic));

            while (true) {
                try {
                    ConsumerRecords<String, T> records = consumer.poll(Duration.ofMillis(100));
                    Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
                    List<T> dataList = new ArrayList<>();

                    for (ConsumerRecord<String, T> record : records) {
                        dataList.add(record.value());
                        offsets.put(new TopicPartition(record.topic(), record.partition()),
                                new OffsetAndMetadata(record.offset() + 1, null));
                    }

                    this.receive(dataList);
                    consumer.commitSync(offsets);
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        };
    }

    private Class<?> getValueClass() {
        Class<?> clazz = this.getClass();
        Class<?> valueClass;

        // 获取直接超类的Type
        Type superclassType = clazz.getGenericSuperclass();

        // 检查超类是否是ParameterizedType
        if (superclassType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superclassType;

            // 获取泛型参数类型
            Type[] typeArguments = parameterizedType.getActualTypeArguments();

            if (typeArguments[0] instanceof Class<?>) {
                valueClass =  (Class<?>) typeArguments[0];
            } else {
                throw new RuntimeException("泛型参数的类型不是Class对象：" + typeArguments[0].getTypeName());
            }
        } else {
            throw new RuntimeException("超类不是ParameterizedType");
        }
        return valueClass;
    }

    private final Runnable listener;

    public void start() {
        new Thread(this.listener).start();
    }
}

package test.consumer;

import kafka.client.consumer.BaseKafkaConsumer;
import kafka.client.consumer.Config;
import kafka.client.serialization.JsonDeserializer;
import kafka.client.serialization.KryoDeserializer;
import test.TestData;

public class TestConsumer extends BaseKafkaConsumer<test.TestData> {

    public TestConsumer(Config config) {
        super(config);
    }

    public static void main(String[] args) {
        Config config = new Config();
        config.bootstrap_servers = "192.168.31.237:9092,192.168.31.238:9092,192.168.31.239:9092";
        config.group_id = "michael-test-group";
        config.topic = "michael-test-topic";
//        config.value_deserializer = KryoDeserializer.class;

        TestConsumer consumer = new TestConsumer(config);

        consumer.start();
    }

    @Override
    public void receive(TestData message) {
        System.out.println(message);
    }
}
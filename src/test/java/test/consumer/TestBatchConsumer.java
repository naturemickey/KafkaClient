package test.consumer;

import kafka.client.consumer.BaseKafkaBatchConsumer;
import kafka.client.consumer.Config;
import test.TestData;

import java.util.List;

public class TestBatchConsumer extends BaseKafkaBatchConsumer<TestData> {

    public TestBatchConsumer(Config config) {
        super(config);
    }

    public static void main(String[] args) {
        Config config = new Config();
        config.bootstrap_servers = "192.168.31.237:9092,192.168.31.238:9092,192.168.31.239:9092";
        config.group_id = "michael-test-group";
        config.topic = "michael-test-topic";

        new TestBatchConsumer(config).start();
    }
    @Override
    public void receive(List<TestData> messages) {
        if (messages.size() > 0) {
            System.out.println("receive: " + messages.size());

            messages.forEach(System.out::println);
        }
    }
}

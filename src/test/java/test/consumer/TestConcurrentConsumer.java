package test.consumer;

import kafka.client.consumer.BaseKafkaConcurrentConsumer;
import kafka.client.consumer.Config;
import test.TestData;

public class TestConcurrentConsumer extends BaseKafkaConcurrentConsumer<TestData> {

    public TestConcurrentConsumer(Config config, int threadCount) {
        super(config, threadCount);
    }

    public static void main(String[] args) {
        Config config = new Config();
        config.bootstrap_servers = "192.168.31.237:9092,192.168.31.238:9092,192.168.31.239:9092";
        config.group_id = "michael-test-group";
        config.topic = "michael-test-topic";

        TestConcurrentConsumer consumer = new TestConcurrentConsumer(config, 5);

        consumer.start();
    }
    @Override
    public void receive(TestData message) {
        System.out.println(message);
    }
}

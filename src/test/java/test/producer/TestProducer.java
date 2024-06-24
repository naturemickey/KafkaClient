package test.producer;

import kafka.client.producer.Config;
import kafka.client.producer.IKafkaProducer;
import kafka.client.producer.KafkaProducerFactory;
import kafka.client.serialization.JsonSerializer;
import kafka.client.serialization.KryoSerializer;
import test.TestData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestProducer {

    public static void main(String[] args) {
        Config config = new Config();
        config.bootstrap_servers = "192.168.31.237:9092,192.168.31.238:9092,192.168.31.239:9092";
//        config.value_serializer = KryoSerializer.class;

        IKafkaProducer<TestData> producer = KafkaProducerFactory.createProducer("michael-test-topic", config);

        List<TestData> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestData data = new TestData();
            data.setId(i);
            data.setName("name"+i);
            data.setAge(i + 1);
            data.setDate(new Date());
            dataList.add(data);
        }
        producer.send(dataList);
    }
}

package kafka.client.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.client.ConfigConstants;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class JsonDeserializer<T> implements Deserializer<T> {


    private final ObjectMapper objectMapper = new ObjectMapper();
    private Class<T> tClass;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        this.tClass = (Class<T>) configs.get(ConfigConstants.VALUE_DESERIALIZER_CLASS);
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.readValue(data, tClass);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing JSON message", e);
        }
    }

    @Override
    public void close() {
        // No resources to close
    }
}
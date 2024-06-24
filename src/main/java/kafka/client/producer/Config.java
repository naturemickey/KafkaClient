package kafka.client.producer;


import kafka.client.serialization.JsonSerializer;

/**
 * 未实现的可选配置：
 *
 * 发送行为相关配置
 * 	•	acks：消息发送确认的方式。0 表示不需要确认，1 表示需要 leader 的确认，all 表示需要所有副本的确认，默认为 1。
 * 	•	compression.type：消息的压缩类型，可以是 none、gzip、snappy、lz4 或 zstd，默认为 none。
 * 	•	retries：发送失败时重试的次数，默认为 2147483647（Integer.MAX_VALUE）。
 * 	•	batch.size：每个分区未发送消息的批次大小，默认是 16384 字节。
 * 	•	linger.ms：Producer 发送消息前等待更多消息加入批次的时间，默认为 0 毫秒。
 * 	•	buffer.memory：Producer 用来缓冲等待发送消息的总内存大小，默认为 33554432（32MB）。
 * 事务相关配置
 * 	•	transactional.id：用于启用事务的唯一标识符。
 * 	•	transaction.timeout.ms：事务的超时时间，默认为 60000 毫秒（60秒）。
 * 错误处理相关配置
 * 	•	max.in.flight.requests.per.connection：每个连接最多未确认请求数，默认为 5。设置过高可能影响消息顺序。
 * 	•	enable.idempotence：是否启用幂等性，默认为 false。设置为 true 可以确保消息不重复发送。
 * 连接管理相关配置
 * 	•	client.id：Producer 客户端的 ID，用于标识 Producer 的来源。
 * 	•	connections.max.idle.ms：连接空闲时间超过该值将关闭连接，默认为 540000 毫秒（9 分钟）。
 * 	•	request.timeout.ms：Producer 请求的超时时间，默认为 30000 毫秒（30秒）。
 * 安全相关配置
 * 	•	security.protocol：用于 Producer 和 broker 之间通信的协议，支持 PLAINTEXT、SSL、SASL_PLAINTEXT 和 SASL_SSL。
 * 	•	ssl. 和 sasl. 配置**：用于配置 SSL 和 SASL 的相关参数，具体可以参考 Kafka 官方文档。
 * 高级配置
 * 	•	interceptor.classes：拦截器类的列表，用于拦截 Producer 的消息处理。
 * 	•	metrics.sample.window.ms：度量统计样本的窗口大小，默认为 30000 毫秒。
 * 	•	metrics.num.samples：度量统计样本的数量，默认为 2。
 * 	•	metric.reporters：用于收集和报告度量数据的类。
 */
public class Config {
    public String bootstrap_servers; // Kafka broker 的地址列表，格式为 host1:port1,host2:port2,...
    public final Class<?> key_serializer = org.apache.kafka.common.serialization.StringSerializer.class; // 消息键的序列化器类（如 org.apache.kafka.common.serialization.StringSerializer）
    public Class<?> value_serializer = JsonSerializer.class; // 消息值的序列化器类（如 org.apache.kafka.common.serialization.StringSerializer）
    public OptionalConfig optional;
}

class OptionalConfig {
}
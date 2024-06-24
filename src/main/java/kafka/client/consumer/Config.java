package kafka.client.consumer;


import kafka.client.serialization.JsonDeserializer;

/**
 未实现的可选配置：

 消费行为相关配置
 •	fetch.min.bytes：Consumer 在从服务器获取记录之前等待的最小数据量，默认为 1。
 •	fetch.max.bytes：Consumer 从服务器获取记录的最大数据量，默认为 50 MB。
 •	fetch.max.wait.ms：Consumer 等待 fetch.min.bytes 数据的最大时间，默认为 500 毫秒。
 •	max.poll.records：每次调用 poll() 时返回的最大记录数，默认为 500。
 •	max.poll.interval.ms：Consumer 调用 poll() 的最大间隔时间，超过该时间会触发再均衡，默认为 300000 毫秒（5 分钟）。
 •	session.timeout.ms：Consumer 与 broker 之间的会话超时时间，默认为 10000 毫秒。
 •	heartbeat.interval.ms：Consumer 向 broker 发送心跳的时间间隔，默认为 3000 毫秒。
 自动提交相关配置
 •	auto.commit.interval.ms：自动提交偏移量的时间间隔，默认为 5000 毫秒。
 错误处理相关配置
 •	isolation.level：Consumer 读取消息的隔离级别，read_committed 仅读取已提交的消息，read_uncommitted 读取所有消息。默认为 read_uncommitted。
 •	retry.backoff.ms：Consumer 在尝试获取元数据或分区领导者信息失败后等待的时间，默认为 100 毫秒。
 连接管理相关配置
 •	client.id：Consumer 客户端的 ID，用于标识 Consumer 的来源。
 •	connections.max.idle.ms：连接空闲时间超过该值将关闭连接，默认为 540000 毫秒（9 分钟）。
 •	request.timeout.ms：Consumer 请求的超时时间，默认为 305000 毫秒（5 分钟）。
 •	metadata.max.age.ms：强制更新元数据的时间间隔，默认为 300000 毫秒（5 分钟）。
 安全相关配置
 •	security.protocol：用于 Consumer 和 broker 之间通信的协议，支持 PLAINTEXT、SSL、SASL_PLAINTEXT 和 SASL_SSL。
 •	ssl. 和 sasl. 配置**：用于配置 SSL 和 SASL 的相关参数，具体可以参考 Kafka 官方文档。
 高级配置
 •	interceptor.classes：拦截器类的列表，用于拦截 Consumer 的消息处理。
 •	metrics.sample.window.ms：度量统计样本的窗口大小，默认为 30000 毫秒。
 •	metrics.num.samples：度量统计样本的数量，默认为 2。
 •	metric.reporters：用于收集和报告度量数据的类。
 */
public class Config {
    public String bootstrap_servers; // Kafka broker 的地址列表，格式为 host1:port1,host2:port2,...
    public String group_id; // Consumer 所属的消费组 ID
    public String topic;
    public final Class<?> key_deserializer = org.apache.kafka.common.serialization.StringDeserializer.class;
    public Class<?> value_deserializer = JsonDeserializer.class;
    public OptionalConfig optional;
}

class OptionalConfig {
    public String auto_offset_reset = "earliest"; // earliest/latest/none
}
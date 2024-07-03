package kafka.client.consumer;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public abstract class BaseKafkaConcurrentConsumer<T> extends BaseKafkaBatchConsumer<T>{

    private ExecutorService executorService;
    public BaseKafkaConcurrentConsumer(Config config, int threadCount) {
        super(config);
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    @Override
    public final void receive(List<T> messages) {
        final List<? extends Future<?>> futures = messages.stream().map(message -> executorService.submit(() -> receive(message))).collect(Collectors.toList());
        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public abstract void receive(T message);
}

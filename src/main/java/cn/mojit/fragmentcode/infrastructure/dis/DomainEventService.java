package cn.mojit.fragmentcode.infrastructure.dis;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

/**
 * @author Maobo
 * @date 2023年06月18日 01:18
 */
@Component
public class DomainEventService {
    private final Disruptor<DomainEvent> disruptor;
    private final DomainEventPublisher publisher;
    private final DomainEventSubscriber subscriber;

    public DomainEventService() {
        // 定义RingBuffer的大小
        int bufferSize = 1024;
        disruptor = new Disruptor<>(DomainEvent::new, bufferSize, Executors.defaultThreadFactory());
        subscriber = new DomainEventSubscriber();
        disruptor.handleEventsWith(subscriber);
        disruptor.start();
        RingBuffer<DomainEvent> ringBuffer = disruptor.getRingBuffer();
        publisher = new DomainEventPublisher(ringBuffer);
    }

    public void publishEvent(String eventData) {
        publisher.publishEvent(eventData);
    }
    public void publishEvent(DomainEvent domainEvent){
        publisher.publishEvent(domainEvent);
    }
}

package cn.mojit.fragmentcode.infrastructure.dis;

import com.lmax.disruptor.RingBuffer;

/**
 * @author Maobo
 * @date 2023年06月18日 01:17
 */
public class DomainEventPublisher {
    private final RingBuffer<DomainEvent> ringBuffer;

    public DomainEventPublisher(RingBuffer<DomainEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void publishEvent(String eventData) {
        long sequence = ringBuffer.next();
        try {
            DomainEvent event = ringBuffer.get(sequence);
            event.setEventData(eventData);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
    public void publishEvent(DomainEvent eventData) {
        long sequence = ringBuffer.next();
        try {
            DomainEvent event = ringBuffer.get(sequence);
            event.setEventData(eventData.getEventData());
            event.setId(event.getId());
            event.setType(event.getType());
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}

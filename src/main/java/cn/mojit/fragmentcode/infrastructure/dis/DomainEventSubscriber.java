package cn.mojit.fragmentcode.infrastructure.dis;

import cn.hutool.json.JSONUtil;
import com.lmax.disruptor.EventHandler;

/**
 * @author Maobo
 * @date 2023年06月18日 01:17
 */
public class DomainEventSubscriber implements EventHandler<DomainEvent> {
    @Override
    public void onEvent(DomainEvent event, long sequence, boolean endOfBatch) {
        // 处理接收到的领域事件
        String eventData = event.getEventData();
        // 进行相应的处理逻辑
        System.out.println("消费到消息"+ JSONUtil.toJsonStr(event));

    }
}

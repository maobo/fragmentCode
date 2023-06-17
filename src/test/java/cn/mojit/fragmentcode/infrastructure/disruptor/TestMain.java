package cn.mojit.fragmentcode.infrastructure.disruptor;

import cn.mojit.fragmentcode.infrastructure.dis.DomainEvent;
import cn.mojit.fragmentcode.infrastructure.dis.DomainEventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Maobo
 * @date 2023年06月18日 00:58
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMain {

    @Resource
    private DomainEventService eventService;

    @Test
    public void tests(){
        eventService.publishEvent("Event 1");
        eventService.publishEvent("Event 2");
        eventService.publishEvent("Event 3");
        eventService.publishEvent("Event 4");
        DomainEvent de = new DomainEvent();
        de.setId("123");
        de.setType("email");
        de.setEventData("{这里是一段json数据}");
        eventService.publishEvent(de);

    }
}
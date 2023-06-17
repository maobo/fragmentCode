package cn.mojit.fragmentcode.infrastructure.dis;

import lombok.Data;

/**
 * @author Maobo
 * @date 2023年06月18日 01:16
 */
@Data
public class DomainEvent {
    // 事件数据
    private String id;
    private String type;
    private String eventData;


}

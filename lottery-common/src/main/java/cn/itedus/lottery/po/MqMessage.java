package cn.itedus.lottery.po;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xz
 * @since 2023-07-19
 */

@Data
public class MqMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String messageId;

    private String content;

    private String classType;

    private String toExchange;

    private String routingKey;

//    0-新建,1-已发送,2-错误抵达,3-已抵达
    private Integer messageState;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer failCount;

}

package cn.itedus.lottery.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author：xz
 * @date: 2023/7/2
 */
@Configuration
@Slf4j
public class MyRabbitConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 对象转json
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    @PostConstruct
    public void initRabbitTemplate(){
        /**
         * 1、只要消息抵达Broker就ack=true
         * correlationData：当前消息的唯一关联数据(这个是消息的唯一id)
         * ack：消息是否成功收到
         * cause：失败的原因
         */
        //设置确认回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            /**
             * TODO: 修改数据库状态
             *1、做好消息确认机制(publisher,consumer[手动ack])
             *2、每一个发送的消息都在数据库做好记录。定期将失败的消息再次发送一追
             */
            log.error("服务器收到了消息：correlationData({}),ack({}),cause({})",correlationData,ack,cause);
        });

        /**
         * 只要消息没有投递给指定的队列，就触发这个失败回调
         * message：投递失败的消息详细信息
         * replyCode：回复的状态码
         * replyText：回复的文本内容
         * exchange：当时这个消息发给哪个交换机
         * routingKey：当时这个消息用哪个路邮键
         */
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            // TODO: 由于交换机投递消息失败，我们可以在这里做一些处理，将数据库当前消息的状态置为错误
            log.error("消息{}，被交换机{}退回，退回原因：{}，路由key：{}",new String(message.getBody()),exchange,replyText,routingKey);
        });
    }

}

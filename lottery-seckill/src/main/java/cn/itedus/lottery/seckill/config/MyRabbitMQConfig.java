package cn.itedus.lottery.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @description:
 * @author：xz
 * @date: 2023/6/30
 */
@Configuration
public class MyRabbitMQConfig {

    /* 容器中的Queue、Exchange、Binding 会自动创建（在RabbitMQ）不存在的情况下 */

    @Bean
    public Exchange lotteryEventExchange() {
        /*
         *   String name,
         *   boolean durable,
         *   boolean autoDelete,
         *   Map<String, Object> arguments
         * */
        return new TopicExchange("seckill-event-exchange", true, false);
    }


    /**
     * 死信队列
     * @return
     */
    @Bean
    public Queue lotterySucceedDelayQueue() {
        /*
            Queue(String name,  队列名字
            boolean durable,  是否持久化
            boolean exclusive,  是否排他
            boolean autoDelete, 是否自动删除
            Map<String, Object> arguments) 属性
         */

        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "seckill-event-exchange");  // 死信路由
        arguments.put("x-dead-letter-routing-key", "seckill.process");  // routing-key
        arguments.put("x-message-ttl", 10000); // 消息过期时间 10秒
        Queue queue = new Queue("seckill.succeed.delay.queue", true, false, false, arguments);
        return queue;
    }


    /**
     * 普通队列
     * @return
     */
    @Bean
    public Queue seckillSucceedQueue() {

        Queue queue = new Queue("seckill.succeed.queue", true, false, false);

        return queue;
    }

    /**
     * 普通队列
     * @return
     */
    @Bean
    public Queue seckillProcessQueue() {

        Queue queue = new Queue("seckill.process.queue", true, false, false);

        return queue;
    }


    @Bean
    public Binding lotterySucceedDelayBinding() {
        /*
         * String destination, 目的地（队列名或者交换机名字）
         * DestinationType destinationType, 目的地类型（Queue、Exhcange）
         * String exchange,
         * String routingKey,
         * Map<String, Object> arguments
         * */
        return new Binding("seckill.succeed.queue",
                Binding.DestinationType.QUEUE,
                "seckill-event-exchange",
                "seckill.succeed",
                null);
    }

    @Bean
    public Binding seckillDelayBinding() {
        /*
         * String destination, 目的地（队列名或者交换机名字）
         * DestinationType destinationType, 目的地类型（Queue、Exhcange）
         * String exchange,
         * String routingKey,
         * Map<String, Object> arguments
         * */
        return new Binding("seckill.succeed.delay.queue",
                Binding.DestinationType.QUEUE,
                "seckill-event-exchange",
                "seckill.succeed.delay",
                null);
    }

    @Bean
    public Binding lotteryProcessBinding() {
        /*
         * String destination, 目的地（队列名或者交换机名字）
         * DestinationType destinationType, 目的地类型（Queue、Exhcange）
         * String exchange,
         * String routingKey,
         * Map<String, Object> arguments
         * */
        return new Binding("seckill.process.queue",
                Binding.DestinationType.QUEUE,
                "seckill-event-exchange",
                "seckill.process",
                null);
    }



}

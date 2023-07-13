package cn.itedus.lottery.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @description:
 * @author：xz
 * @date: 2023/7/2
 */
@Slf4j
@RabbitListener(queues = "lottery.process.queue")
@Service
public class LotteryProcessListener {



    //@RabbitHandler
    //public void handleStockLockedRelease(StockLockedTo to, Message message, Channel channel) throws IOException {
    //
    //    try {
    //        // 查看当前订单的状态
    //
    //            // 如果是已经提交了信息，执行发货操作
    //
    //            // 如果用户没有领取，就恢复库存
    //
    //        // 手动删除消息
    //        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    //    } catch (Exception e) {
    //        // 解锁失败 将消息重新放回队列，让别人消费
    //        channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
    //    }
    //}
}

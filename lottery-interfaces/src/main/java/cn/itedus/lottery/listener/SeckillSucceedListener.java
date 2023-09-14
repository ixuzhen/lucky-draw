package cn.itedus.lottery.listener;
import cn.itedus.lottery.rpc.ILotteryActivityBooth;
import cn.itedus.lottery.rpc.req.DrawReq;
import cn.itedus.lottery.rpc.res.DrawRes;
import cn.itedus.lottery.to.SeckillUser;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @description:
 * @author：xz
 * @date: 2023/7/2
 */
@Slf4j

//@Service
public class SeckillSucceedListener {

    @Resource
    private ILotteryActivityBooth lotteryActivityBooth;

    @RabbitListener(queues = "seckill.succeed.queue")
//    @RabbitHandler
    public void handleSeckillSucceedRelease(SeckillUser to, Message message, Channel channel) throws IOException {

        try {
            DrawReq drawReq = new DrawReq();
            drawReq.setuId(to.getUserId());
            drawReq.setActivityId(Long.valueOf(to.getActivityId()));
            DrawRes drawRes = lotteryActivityBooth.doDraw(drawReq);
            log.info("请求参数：{}", JSON.toJSONString(drawReq));
            log.info("测试结果：{}", JSON.toJSONString(drawRes));
            // 手动删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            // 解锁失败 将消息重新放回队列，让别人消费
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }
}

package cn.itedus.lottery.listener;

import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.domain.strategy.repository.IStrategyRepository;
import cn.itedus.lottery.infrastructure.dao.IMqMessageDao;
import cn.itedus.lottery.infrastructure.dao.IStrategyDetailDao;
import cn.itedus.lottery.infrastructure.dao.IUserStrategyExportDao;
import cn.itedus.lottery.po.MqMessage;
import cn.itedus.lottery.po.UserStrategyExport;
import cn.itedus.lottery.rpc.ILotteryActivityBooth;
import cn.itedus.lottery.rpc.req.DrawReq;
import cn.itedus.lottery.rpc.res.DrawRes;
import cn.itedus.lottery.to.SeckillUser;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @description:
 * @author：xz
 * @date: 2023/7/2
 */
@Slf4j

@Service
public class LotteryProcessListener {


    @Autowired
    private IUserStrategyExportDao userStrategyExportDao;

    @Autowired
    private IStrategyRepository strategyRepository;

    @Autowired
    private IMqMessageDao mqMessageDao;
    @RabbitListener(queues = "lottery.process.queue")
//    @RabbitHandler
    public void handleStockLockedRelease(UserStrategyExport to, Message message, Channel channel) throws IOException {
        MqMessage mqMessage = new MqMessage();
        mqMessage.setMessageId(to.getMqId());
        mqMessage.setMessageState(Constants.messageState.ARRIVE.getCode());
        try {
            // 查看当前订单的状态
            UserStrategyExport userStrategyExport = userStrategyExportDao.queryUserStrategyExportById(to);
            if (userStrategyExport == null) {
                // 如果订单不存在，手动删除消息
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                return;
            }

            // 如果是已经领取奖品，就直接把消息删除，不用解锁库存；如果已经超时了，也不用解锁库存
            if (userStrategyExport.getClaimState().equals(Constants.ClaimState.CLAIMED.getCode()) ) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
                mqMessageDao.updateState(mqMessage);
                return;
            }
            // 如果用户没有领取，就恢复库存
            strategyRepository.releaseStock(userStrategyExport.getStrategyId(), userStrategyExport.getAwardId());
            // 更新订单状态为超时未领取
            userStrategyExport.setClaimState(Constants.ClaimState.TIMEOUT.getCode());
            userStrategyExportDao.updateClaimState(userStrategyExport);
            log.info("库存解锁成功：{}", userStrategyExport);
            mqMessageDao.updateState(mqMessage);
            // 手动删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            log.error("解锁库存时发生错误：");
            log.error("", e);
            mqMessage.setMessageState(Constants.messageState.PROCESS_ERROR.getCode());
            mqMessageDao.updateState(mqMessage);
            // 解锁失败 将消息重新放回队列，让别人消费
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }


}

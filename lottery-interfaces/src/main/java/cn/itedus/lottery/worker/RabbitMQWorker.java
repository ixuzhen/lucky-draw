package cn.itedus.lottery.worker;

import cn.bugstack.middleware.db.router.config.DataSourceAutoConfig;
import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.infrastructure.repository.MqMessageRepository;
import cn.itedus.lottery.po.UserStrategyExport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class RabbitMQWorker {

    @Value("${lottery.worker.rabbitMQ.databases}")
    private String database;

    @Resource
    private IDBRouterStrategy dbRouter;

    @Resource
    private MqMessageRepository mqMessageRepository;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 定时检查mq状态数据库，来判断是否需要重新发送mq消息
     */
    @Scheduled(fixedRate = 10000000) // 1000等于1s
    public void lotteryOrderMQStateWorker () {
        if (null == database || "".equals(database)) {
            return;
        }
        String[] databaseNums = database.split(",");
        for (String databaseNum : databaseNums) {
            int num = Integer.parseInt(databaseNum);
            // 判断是否超过数据库数量
            if (num > dbRouter.dbCount()) {
                continue;
            }
            List<UserStrategyExport> userStrategyExports = mqMessageRepository.queryFailMessages(num);

            for (UserStrategyExport userStrategyExport : userStrategyExports) {
                log.error("重新发送mq消息，消息内容：{}", userStrategyExport);
                mqMessageRepository.updateState(userStrategyExport, Constants.messageState.NEW.getCode());
                rabbitTemplate.
                        convertAndSend(
                        "lottery-event-exchange",
                        "lottery.succeed.delay",
                        userStrategyExport,
                        new CorrelationData(userStrategyExport.getMqId())
                );
            }

        }
    }

}

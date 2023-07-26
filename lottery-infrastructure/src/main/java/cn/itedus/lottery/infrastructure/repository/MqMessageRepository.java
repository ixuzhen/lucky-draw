package cn.itedus.lottery.infrastructure.repository;


import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import cn.hutool.json.JSONUtil;
import cn.itedus.lottery.infrastructure.dao.IMqMessageDao;
import cn.itedus.lottery.po.MqMessage;
import cn.itedus.lottery.po.UserStrategyExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class MqMessageRepository {

    @Resource
    private IMqMessageDao mqMessageDao;

    @Resource
    private IDBRouterStrategy dbRouter;

    public List<UserStrategyExport> queryFailMessages(int dbNum) {
        try {
            dbRouter.setDBKey(dbNum);
            List<MqMessage> mqMessages = mqMessageDao.queryFailMessages();
            List<UserStrategyExport> res = new ArrayList<>();
            for (MqMessage mqMessage : mqMessages) {
                UserStrategyExport userStrategyExport = JSONUtil.toBean(mqMessage.getContent(), UserStrategyExport.class);
                res.add(userStrategyExport);
            }
            return res;
        } finally {
            dbRouter.clear();
        }
    }

    public void updateState(UserStrategyExport userStrategyExport, int state) {
        MqMessage mqMessage = new MqMessage();
        mqMessage.setMessageId(userStrategyExport.getMqId());
        mqMessage.setMessageState(state);
        mqMessageDao.updateState(mqMessage);
    }


}

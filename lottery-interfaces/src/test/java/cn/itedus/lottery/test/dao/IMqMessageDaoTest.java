package cn.itedus.lottery.test.dao;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.itedus.lottery.common.Constants;
import cn.itedus.lottery.infrastructure.dao.IMqMessageDao;
import cn.itedus.lottery.po.MqMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IMqMessageDaoTest {

    @Autowired
    private IMqMessageDao mqMessageDao;

    @Test
    public void test_insert() {
        MqMessage mqMessage = new MqMessage();
        mqMessage.setMessageId(IdUtil.simpleUUID());
        mqMessage.setContent("123455453");
        mqMessage.setUId("xiaofuge");
        mqMessage.setMessageState(Constants.messageState.NEW.getCode());
        mqMessageDao.insert(mqMessage);
    }

    @Test
    public void test_updateState() {
        MqMessage mqMessage = new MqMessage();

        mqMessage.setMessageId("1af6f5dd-9466-42c7-b3c5-601dae1a");
        mqMessage.setMessageState(1);
        mqMessage.setUId("xiaofuge");
        mqMessageDao.updateState(mqMessage);
    }
}

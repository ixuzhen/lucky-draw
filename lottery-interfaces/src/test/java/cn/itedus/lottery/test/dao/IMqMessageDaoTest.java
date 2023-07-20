package cn.itedus.lottery.test.dao;

import cn.hutool.core.lang.UUID;
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
        String uuid = UUID.fastUUID().toString();
        mqMessage.setMessageId(uuid);
        mqMessage.setContent("123455453");
        mqMessageDao.insert(mqMessage);
    }

    @Test
    public void test_updateState() {
        MqMessage mqMessage = new MqMessage();
        mqMessage.setMessageId("123");
        mqMessage.setMessageState(1);
        mqMessageDao.updateState(mqMessage);
    }
}

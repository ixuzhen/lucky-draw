package cn.itedus.lottery.test.dao;

import cn.itedus.lottery.infrastructure.dao.IMqMessageDao;
import cn.itedus.lottery.infrastructure.repository.MqMessageRepository;
import cn.itedus.lottery.po.MqMessage;
import cn.itedus.lottery.po.UserStrategyExport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MqMessageDaoTest {

    @Autowired
    private MqMessageRepository mqMessageRepository;

    @Test
    public void test01() {
        List<UserStrategyExport> userStrategyExports = mqMessageRepository.queryFailMessages(2);
        // 循环打印
        for (UserStrategyExport userStrategyExport : userStrategyExports) {
            System.out.println(userStrategyExport);
        }
    }

}

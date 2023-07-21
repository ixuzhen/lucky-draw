package cn.itedus.lottery.test.common;


import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class UUIDTest {


    @Test
    public void test_uuid() {
        System.out.println("UUID.randomUUID().toString() = " + IdUtil.simpleUUID());
        log.info("UUID.randomUUID().toString() = " + IdUtil.simpleUUID().length());
    }

}

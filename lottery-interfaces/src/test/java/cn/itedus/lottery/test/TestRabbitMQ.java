package cn.itedus.lottery.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;

/**
 * @description:
 * @author：xz
 * @date: 2023/7/2
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRabbitMQ {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Data
    @AllArgsConstructor
    class Dog{
        private static final long serialVersionUID = 1L;
        public String name;
        public Integer age;

    }

    @Test
    public void testSend(){
        Dog dog = new Dog("zhangsan",14);
        rabbitTemplate.convertAndSend(
                "lottery-event-exchange",
                "lottery.succeed.delay",
                dog
        );
    }

}

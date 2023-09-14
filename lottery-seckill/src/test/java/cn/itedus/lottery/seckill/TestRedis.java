package cn.itedus.lottery.seckill;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
public class TestRedis {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;

    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("seckill.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);
    }

    @Test
    public void testLua() {
        // 1.执行lua脚本
        Long result = stringRedisTemplate.execute(
                SECKILL_SCRIPT,
                Collections.emptyList(),
                "100001", "xiaofuge1"
        );
        log.error("result:{}", result);
    }
    @Test
    public void testRedis() {
        stringRedisTemplate.opsForValue().set("seckill:stock:100001", "100");
        stringRedisTemplate.expire("seckill:stock:100001", 10000, TimeUnit.SECONDS);
        stringRedisTemplate.opsForSet().add("seckill:uid:100001","");
        stringRedisTemplate.expire("seckill:stock:100001", 10000, TimeUnit.SECONDS);
    }





}

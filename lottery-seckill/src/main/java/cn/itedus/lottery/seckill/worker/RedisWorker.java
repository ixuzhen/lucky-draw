package cn.itedus.lottery.seckill.worker;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisWorker {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

//    TODO: 增加redis数据
//    @Scheduled(fixedRate = 10000000) // 1000等于1s
    public void addRedisData() {
        stringRedisTemplate.opsForValue().set("seckill:stock:100001", "100");
        stringRedisTemplate.expire("seckill:stock:100001", 100, TimeUnit.SECONDS);
        stringRedisTemplate.opsForSet().isMember("seckill:user:100001","");
        stringRedisTemplate.expire("seckill:stock:100001", 100, TimeUnit.SECONDS);
    }

}

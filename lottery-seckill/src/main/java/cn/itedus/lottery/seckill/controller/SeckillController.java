package cn.itedus.lottery.seckill.controller;


import cn.itedus.lottery.common.R;
import cn.itedus.lottery.common.Result;
import cn.itedus.lottery.seckill.service.LotteryFeignService;
import cn.itedus.lottery.seckill.to.DrawRes;
import cn.itedus.lottery.to.SeckillUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class SeckillController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;

    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("seckill.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);
    }
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private LotteryFeignService lotteryFeignService;

    @PostMapping("/seckill")
    public R<DrawRes> seckill() {
        String activityId = "100001";
        String userId = "xiaofuge1";
        // 1.执行lua脚本
        Long result = stringRedisTemplate.execute(
                SECKILL_SCRIPT,
                Collections.emptyList(),
                activityId, userId
        );
        int r = result.intValue();
        if (r != 0 ) {
            return R.failed("秒杀失败");
        }
        // 2.秒杀成功，发送消息
//        SeckillUser seckillUser = new SeckillUser(activityId, userId);
//        rabbitTemplate.convertAndSend(
//                "seckill-event-exchange",
//                "seckill.succeed",
//                seckillUser
//        );
        R<DrawRes> res = lotteryFeignService.lottery(userId, activityId);
        return res;
    }

    @GetMapping("/addData")
    public Result testRedis() {
        stringRedisTemplate.opsForValue().set("seckill:stock:100001", "100");
        stringRedisTemplate.expire("seckill:stock:100001", 10000, TimeUnit.SECONDS);
        stringRedisTemplate.delete("seckill:user:100001");
        return Result.buildSuccessResult();
    }

}

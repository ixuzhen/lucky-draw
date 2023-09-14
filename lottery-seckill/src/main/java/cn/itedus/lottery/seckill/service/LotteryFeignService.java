package cn.itedus.lottery.seckill.service;


import cn.itedus.lottery.common.R;
import cn.itedus.lottery.seckill.to.DrawRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "lottery")
public interface LotteryFeignService {
    @GetMapping("/api/lottery/{userId}/{activityId}")
    R<DrawRes> lottery(@PathVariable("userId") String userId,@PathVariable("activityId") String activityId);
}
